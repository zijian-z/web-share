package com.zijian.webshare.link;

import com.zijian.webshare.comment.CommentService;
import com.zijian.webshare.exception.ResourceEmptyException;
import com.zijian.webshare.user.User;
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
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {
    private static final Logger log = LoggerFactory.getLogger(LinkController.class);

    private final LinkService linkService;
    private final CommentService commentService;
    private final HttpSession httpSession;
    private final HashMap<String, User> map;

    @Autowired
    public LinkController(LinkService linkService, CommentService commentService, HttpSession httpSession, HashMap<String, User> map) {
        this.linkService = linkService;
        this.commentService = commentService;
        this.httpSession = httpSession;
        this.map = map;
    }

    @GetMapping("/")
    public Page<Link> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return linkService.findAll(pageable);
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
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("link.empty");
        }
    }
}
