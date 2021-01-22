package com.zijian.webshare.profile;

import com.zijian.webshare.exception.ResourceEmptyException;
import com.zijian.webshare.user.User;
import com.zijian.webshare.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final UserService userService;
    private final HttpSession httpSession;
    private final HashMap<String, User> map;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService,
                             HttpSession httpSession, HashMap<String, User> map) {
        this.profileService = profileService;
        this.userService = userService;
        this.httpSession = httpSession;
        this.map = map;
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileDTO> findByUser(@PathVariable String username) {
        ProfileDTO profileDTO;
        //get请求不能保证有session
        if (httpSession != null && httpSession.getAttribute("user").equals(username)) {
            profileDTO = getSelfProfileDTO(username);
        } else {
            profileDTO = getOtherProfileDTO(username);
        }
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<ProfileDTO> updateByUser(@PathVariable String username, @RequestBody ProfileVO profileVO) {
        User user = map.get(username);
        Optional<Profile> optional = profileService.findByUser(user);
        if (optional.isPresent()) {
            Profile profile = optional.get();
            profile.setBio(profileVO.getBio());
            profile.setEmail(profileVO.getEmail());
            profileService.save(profile);
            return new ResponseEntity<>(new ProfileDTO(profile.getEmail(), profile.getBio()), HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("user.empty");
        }
    }

    private ProfileDTO getSelfProfileDTO(String username) {
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Profile> optional = profileService.findByUser(user);
            Profile profile = optional.orElseGet(() -> profileService.save(new Profile(user, "", "")));
            return new ProfileDTO(profile.getEmail(), profile.getBio());
        } else {
            throw new ResourceEmptyException("user.empty");
        }
    }

    private ProfileDTO getOtherProfileDTO(String username) {
        Optional<User> optional = userService.findByUsername(username);
        if (optional.isPresent()) {
            Optional<Profile> profileOptional = profileService.findByUser(optional.get());
            if (profileOptional.isPresent()) {
                return new ProfileDTO(profileOptional.get().getBio());
            } else {
                throw new ResourceEmptyException("profile.empty");
            }
        } else {
            throw new ResourceEmptyException("user.empty");
        }
    }
}
