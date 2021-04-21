package com.zijian.news.controller;

import com.zijian.news.dao.Profile;
import com.zijian.news.dao.User;
import com.zijian.news.exception.RequestException;
import com.zijian.news.exception.ResourceEmptyException;
import com.zijian.news.mail.MailProducer;
import com.zijian.news.mapper.ProfileMapper;
import com.zijian.news.mapper.UserMapper;
import com.zijian.news.query.RegisterQuery;
import com.zijian.news.util.VerificationCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    private final UserMapper userMapper;
    private final HttpSession httpSession;
    private final MailProducer mailProducer;
    private final PasswordEncoder encoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final ProfileMapper profileMapper;

    @Autowired
    public UserController(UserMapper userMapper, HttpSession httpSession,
                          MailProducer mailProducer, PasswordEncoder encoder,
                          RedisTemplate<String, String> redisTemplate, ProfileMapper profileMapper) {
        this.userMapper = userMapper;
        this.httpSession = httpSession;
        this.mailProducer = mailProducer;
        this.encoder = encoder;
        this.redisTemplate = redisTemplate;
        this.profileMapper = profileMapper;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user;
        if (username.contains("@")) {
            user = userMapper.findByEmail(username);
        }else{
            user = userMapper.findByUsername(username);
        }

        if (user == null) {
            throw new ResourceEmptyException("user.empty");
        }
        if (!encoder.matches(password, user.getPassword())) {
            throw new RequestException("user.login.failed");
        }

        httpSession.setAttribute("userId", user.getId());
        httpSession.setAttribute("username", user.getUsername());
        return new ResponseEntity<>("user.login.success", HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        if (httpSession != null && httpSession.getAttribute("username") != null) {
            return new ResponseEntity<>(httpSession.getAttribute("username").toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterQuery query) {
        logger.info("注册信息 {}", query);
        String registerCode = redisTemplate.opsForValue().get(query.getEmail());
        logger.info("缓存的验证码 {}", registerCode);
        if (registerCode == null || !registerCode.equals(query.getRegisterCode())) {
            throw new ResourceEmptyException("code.error");
        }
        if (userMapper.findByUsername(query.getUsername()) != null
            || userMapper.findByEmail(query.getEmail()) != null) {
            throw new RequestException("user.exists");
        }

        User user = new User(query.getUsername(), query.getEmail(), encoder.encode(query.getPassword()));
        userMapper.insert(user);

        Profile profile = new Profile(user.getId());
        profileMapper.insert(profile);

        return new ResponseEntity<>("user.register.success", HttpStatus.OK);
    }

    @GetMapping("/sendRegisterCode")
    public ResponseEntity<String> sendRegisterCode(@RequestParam String email) {
        if (email == null || !email.contains("@")) {
            throw new RequestException("mail.error");
        }

        if (userMapper.findByEmail(email) != null) {
            throw new RequestException("email.exists");
        }

        String code = VerificationCodeUtil.generate();
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
        mailProducer.produce(email, "欢迎注册", code);
        return new ResponseEntity<>("mail.send.success", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        httpSession.invalidate();
        return new ResponseEntity<>("logout.success", HttpStatus.OK);
    }
}
