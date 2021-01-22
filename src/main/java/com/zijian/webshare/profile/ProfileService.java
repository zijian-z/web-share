package com.zijian.webshare.profile;

import com.zijian.webshare.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * 保存或更新profile
     * @param profile
     * @return
     */
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    /**
     * 查找某个用户的profile
     * @param user
     * @return
     */
    public Optional<Profile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }
}
