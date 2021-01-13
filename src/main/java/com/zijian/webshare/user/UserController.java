package com.zijian.webshare.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated @RequestBody User user) {
        Optional<User> optional = userService.findByUsername(user.getUsername());
        if (optional.isPresent()) {
            if (userService.checkPassword(user.getPassword(), optional.get().getPassword())) {
                return new ResponseEntity<>("user.login.success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("user.login.failed", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("user.empty", HttpStatus.OK);
        }
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
}
