package com.zijian.news.controller;

import com.zijian.news.dao.Profile;
import com.zijian.news.dao.User;
import com.zijian.news.mapper.ProfileMapper;
import com.zijian.news.mapper.UserMapper;
import com.zijian.news.query.ProfileQuery;
import com.zijian.news.vo.ProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileMapper profileMapper;
    private final UserMapper userMapper;
    private final HttpSession httpSession;

    @Autowired
    public ProfileController(ProfileMapper profileMapper, UserMapper userMapper,
                             HttpSession httpSession) {
        this.profileMapper = profileMapper;
        this.userMapper = userMapper;
        this.httpSession = httpSession;
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileVO> findByUser(@PathVariable String username) {
        User user = userMapper.findByUsername(username);

        if (httpSession != null && httpSession.getAttribute("userId") != null) {
            Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
            if (user.getId().equals(userId)) {
                Profile profile = profileMapper.findByUserId(userId);
                return new ResponseEntity<>(new ProfileVO(profile, user.getEmail()), HttpStatus.OK);
            }
        }

        Profile profile = profileMapper.findByUserId(user.getId());
        ProfileVO profileVO = new ProfileVO();
        profileVO.setSelf(false);
        profileVO.setBio(profile.getBio());
        return new ResponseEntity<>(profileVO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ProfileVO> updateProfile(@RequestBody ProfileQuery query) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        Profile profile = new Profile(userId, query.getSex(), query.getBio());
        profileMapper.update(profile);
        userMapper.updateEmail(query.getEmail(), userId);
        return new ResponseEntity<>(new ProfileVO(profile, query.getEmail()), HttpStatus.OK);
    }
}
