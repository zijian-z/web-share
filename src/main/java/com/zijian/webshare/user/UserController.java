package com.zijian.webshare.user;

import com.zijian.webshare.exception.ResourceEmptyException;
import com.zijian.webshare.mail.MailProducer;
import com.zijian.webshare.util.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final HttpSession httpSession;
    private final HashMap<String, User> map;
    private final MailProducer mailProducer;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public UserController(UserService userService, HttpSession httpSession,
                          HashMap<String, User> map, MailProducer mailProducer,
                          RedisTemplate<String, String> redisTemplate) {
        this.userService = userService;
        this.httpSession = httpSession;
        this.map = map;
        this.mailProducer = mailProducer;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> optional;
        if (username.contains("@")) {
            optional = userService.findByEmail(username);
        }else{
            optional = userService.findByUsername(username);
        }

        if (optional.isPresent()) {
            User user = optional.get();
            if (userService.checkPassword(password, user.getPassword())) {
                httpSession.setAttribute("user", user.getUsername());
                map.put(user.getUsername(), user);
                return new ResponseEntity<>("user.login.success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("user.login.failed", HttpStatus.OK);
            }
        } else {
            throw new ResourceEmptyException("user.empty");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        if (httpSession != null && httpSession.getAttribute("user") != null) {
            return new ResponseEntity<>(httpSession.getAttribute("user").toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody UserVO userVO) {
        String storedCode = redisTemplate.opsForValue().get(userVO.getEmail());
        if (storedCode == null || !storedCode.equals(userVO.getCode())) {
            return new ResponseEntity<>("code.error", HttpStatus.OK);
        }
        try {
            userService.register(userVO.getUsername(), userVO.getEmail(), userVO.getPassword());
            return new ResponseEntity<>("user.register.success", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("user.exists", HttpStatus.OK);
        }
    }

    @GetMapping("/registerMail")
    public ResponseEntity<String> registerMail(@RequestParam String email) {
        if (email == null || !email.contains("@")) {
            return new ResponseEntity<>("code.error", HttpStatus.OK);
        }
        String code = VerificationCodeUtil.generate();
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
        mailProducer.produce(email, "欢迎注册", code);
        return new ResponseEntity<>("code.success", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        map.remove(httpSession.getAttribute("user").toString());
        httpSession.invalidate();
        return new ResponseEntity<>("logout.success", HttpStatus.OK);
    }
}
