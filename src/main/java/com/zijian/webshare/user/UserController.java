package com.zijian.webshare.user;

import com.zijian.webshare.exception.ResourceEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final HttpSession httpSession;
    private final HashMap<String, User> map;

    @Autowired
    public UserController(UserService userService, HttpSession httpSession, HashMap<String, User> map) {
        this.userService = userService;
        this.httpSession = httpSession;
        this.map = map;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> optional = userService.findByUsername(username);
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
    public ResponseEntity<String> register(@Validated @RequestBody User user) {
        try {
            userService.register(user.getUsername(), user.getPassword());
            return new ResponseEntity<>("user.register.success", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("user.exists", HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        map.remove(httpSession.getAttribute("user").toString());
        httpSession.invalidate();
        return new ResponseEntity<>("logout.success", HttpStatus.OK);
    }
}
