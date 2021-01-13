package com.zijian.webshare.link;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LinkRepository extends PagingAndSortingRepository<Link, Long> {
    Page<Link> findAll(Pageable pageable);
    Page<Link> findAllByUserId(Long userId, Pageable pageable);
}

