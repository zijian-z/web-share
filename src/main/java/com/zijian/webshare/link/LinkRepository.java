package com.zijian.webshare.link;

import com.zijian.webshare.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface LinkRepository extends PagingAndSortingRepository<Link, Long> {
    Page<Link> findAll(Pageable pageable);
    Page<Link> findAllByUser(User user, Pageable pageable);
    Optional<Link> findById(Long linkId);
}

