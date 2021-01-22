package com.zijian.webshare.profile;

import com.zijian.webshare.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
