package com.zijian.webshare.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class LinkService {
    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    /**
     * 添加一条链接
     * @param link
     * @return
     */
    public Link save(Link link) {
        return linkRepository.save(link);
    }

    /**
     * 查找所有的链接
     * @param pageable
     * @return
     */
    public Page<Link> findAll(Pageable pageable) {
        return linkRepository.findAll(pageable);
    }
    /**
     * 由用户id查找所有上传的链接
     * @param userId
     * @return
     */
    public Page<Link> findAllByUserId(Long userId, Pageable pageable) {
        return linkRepository.findAllByUserId(userId, pageable);
    }

    /**
     * 根据linkId查找链接
     * @param linkId
     * @return
     */
    public Optional<Link> findById(Long linkId) {
        return linkRepository.findById(linkId);
    }
}
