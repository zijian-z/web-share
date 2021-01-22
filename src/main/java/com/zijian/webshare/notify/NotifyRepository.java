package com.zijian.webshare.notify;

import com.zijian.webshare.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotifyRepository extends PagingAndSortingRepository<Notify, Long> {
    Page<Notify> findByConsumeUser(User consumeUser, Pageable pageable);
}
