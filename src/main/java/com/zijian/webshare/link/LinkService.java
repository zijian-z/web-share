package com.zijian.webshare.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public Link addLink(Link link) {
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
}
