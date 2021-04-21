package com.zijian.news.controller;

import com.zijian.news.dao.Comment;
import com.zijian.news.dao.Link;
import com.zijian.news.mapper.CommentMapper;
import com.zijian.news.mapper.LinkMapper;
import com.zijian.news.mapper.UserMapper;
import com.zijian.news.query.LinkQuery;
import com.zijian.news.exception.ResourceEmptyException;
import com.zijian.news.util.UserLikeUtil;
import com.zijian.news.vo.CommentVO;
import com.zijian.news.vo.LinkVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/links")
public class LinkController {
    private static final Logger log = LoggerFactory.getLogger(LinkController.class);

    private final UserMapper userMapper;
    private final LinkMapper linkMapper;
    private final CommentMapper commentMapper;
    private final HttpSession httpSession;
    private final UserLikeUtil userLikeUtil;

    @Autowired
    public LinkController(UserMapper userMapper, LinkMapper linkMapper,
                          CommentMapper commentMapper, HttpSession httpSession,
                          UserLikeUtil userLikeUtil) {
        this.userMapper = userMapper;
        this.linkMapper = linkMapper;
        this.commentMapper = commentMapper;
        this.httpSession = httpSession;
        this.userLikeUtil = userLikeUtil;
    }

    @GetMapping("/")
    public List<Link> findAll() {
        return linkMapper.findTop50();
    }

    @GetMapping("/hot")
    public List<Link> findHot() {
        List<Long> hotLinks = userLikeUtil.linkLikeSort(0, 50);
        List<Link> links = new ArrayList<>(50);
        for (Long id : hotLinks) {
            links.add(linkMapper.findById(id));
        }
        return links;
    }

    @PostMapping("/")
    public ResponseEntity<Link> addLink(@RequestBody LinkQuery linkQuery) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        Long currTime = System.currentTimeMillis();
        Link link = new Link(userId, linkQuery.getTitle(), linkQuery.getUrl(), currTime);
        linkMapper.insert(link);
        log.info("插入link {}", link);

        Comment comment = new Comment(userId, link.getId(), linkQuery.getFirstComment(), currTime);
        commentMapper.insert(comment);
        log.info("推荐理由 {}", comment);

        return new ResponseEntity<>(link, HttpStatus.OK);
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<LinkVO> findById(@PathVariable Long linkId) {
        Link link = linkMapper.findById(linkId);
        if (link == null) {
            throw new ResourceEmptyException("link.empty");
        }

        List<Comment> comments = commentMapper.findByLinkId(linkId);
        List<CommentVO> commentVOS = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            commentVOS.add(new CommentVO(comment, userMapper.findUsernameByUserId(comment.getUserId())));
        }
        LinkVO linkVO = new LinkVO(link, false, commentVOS);
        if (httpSession.getAttribute("userId") != null) {
            Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
            if (userLikeUtil.checkUserLiked(linkId, userId)) {
                linkVO.setLiked(true);
            }
        }
        return new ResponseEntity<>(linkVO, HttpStatus.OK);
    }

    @PostMapping("/like/{linkId}")
    public ResponseEntity<String> likeLink(@PathVariable Long linkId) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        Link link = linkMapper.findById(linkId);
        if (link == null) {
            throw new ResourceEmptyException("link.empty");
        }

        userLikeUtil.addLike(linkId, userId);
        return new ResponseEntity<>("link.like.success", HttpStatus.OK);
    }

    @PostMapping("/unlike/{linkId}")
    public ResponseEntity<String> unlikeLink(@PathVariable Long linkId) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        Link link = linkMapper.findById(linkId);
        if (link == null) {
            throw new ResourceEmptyException("link.empty");
        }

        userLikeUtil.cancelLike(linkId, userId);
        return new ResponseEntity<>("link.unlike.success", HttpStatus.OK);
    }
}
