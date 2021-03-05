package com.zijian.webshare.link;

import com.zijian.webshare.comment.CommentService;
import com.zijian.webshare.exception.ResourceEmptyException;
import com.zijian.webshare.user.User;
import com.zijian.webshare.util.LikeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {
    private static final Logger log = LoggerFactory.getLogger(LinkController.class);

    private final LinkService linkService;
    private final CommentService commentService;
    private final HttpSession httpSession;
    private final HashMap<String, User> map;
    private final LikeUtil likeUtil;

    @Autowired
    public LinkController(LinkService linkService, CommentService commentService,
                          HttpSession httpSession, HashMap<String, User> map,
                          LikeUtil likeUtil) {
        this.linkService = linkService;
        this.commentService = commentService;
        this.httpSession = httpSession;
        this.map = map;
        this.likeUtil = likeUtil;
    }

    @GetMapping("/")
    public Page<Link> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return linkService.findAll(pageable);
    }

    @GetMapping("/hot")
    public List<Link> findHot(@PageableDefault(size = 20) Pageable pageable) {
        List<Link> res = new ArrayList<>();
        for (Long linkId : likeUtil.linkLikeSort(pageable.getOffset(), pageable.getPageSize())) {
            res.add(linkService.findById(linkId).get());
        }
        return res;
    }

    @PostMapping("/")
    public ResponseEntity<Link> addLink(@RequestBody LinkVO linkVO) {
        User user = map.get(httpSession.getAttribute("user").toString());
        Link link = linkService.save(new Link(linkVO.getTitle(), linkVO.getUri(), System.currentTimeMillis(), user, null));
        if (linkVO.getFirstComment() != null) {
            commentService.save(linkVO.getFirstComment(), user, link);
        }
        return new ResponseEntity<>(link, HttpStatus.OK);
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<Link> findById(@PathVariable Long linkId) {
        Optional<Link> optional = linkService.findById(linkId);
        if (optional.isPresent()) {
            Link link = optional.get();
            if (httpSession.getAttribute("user") != null) {
                String username = httpSession.getAttribute("user").toString();
                if (map.containsKey(username) && likeUtil.userLiked(linkId, map.get(username).getId())) {
                    link.setUserLiked(true);
                }
            }
            return new ResponseEntity<>(link, HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("link.empty");
        }
    }

    @PostMapping("/like/{linkId}")
    public ResponseEntity<String> likeLink(@PathVariable Long linkId) {
        User user = map.get(httpSession.getAttribute("user").toString());
        Optional<Link> optional = linkService.findById(linkId);
        if (optional.isPresent()) {
            likeUtil.addLike(linkId, user.getId());
            return new ResponseEntity<>("link.like.success", HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("link.empty");
        }
    }

    @PostMapping("/unlike/{linkId}")
    public ResponseEntity<String> unlikeLink(@PathVariable Long linkId) {
        User user = map.get(httpSession.getAttribute("user").toString());
        Optional<Link> optional = linkService.findById(linkId);
        if (optional.isPresent()) {
            likeUtil.cancelLike(linkId, user.getId());
            return new ResponseEntity<>("link.unlike.success", HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("link.empty");
        }
    }
}
