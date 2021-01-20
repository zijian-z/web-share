package com.zijian.webshare.comment;

import com.zijian.webshare.exception.ResourceEmptyException;
import com.zijian.webshare.link.Link;
import com.zijian.webshare.link.LinkService;
import com.zijian.webshare.user.User;
import com.zijian.webshare.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final LinkService linkService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final HashMap<String, User> map;

    @Autowired
    public CommentController(CommentService commentService, LinkService linkService,
                             UserService userService, HttpSession httpSession,
                             HashMap<String, User> map) {
        this.commentService = commentService;
        this.linkService = linkService;
        this.userService = userService;
        this.httpSession = httpSession;
        this.map = map;
    }

    @PostMapping("/")
    public ResponseEntity<Comment> addComment(@RequestBody CommentVO commentVO) {
        Optional<Link> optional = linkService.findById(commentVO.getLinkId());
        if (optional.isPresent()) {
            User user = map.get(httpSession.getAttribute("user").toString());
            Comment savedComment = commentService.save(
                    commentVO.getContent(), user, optional.get());
            return new ResponseEntity<>(savedComment, HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("link.empty");
        }
    }
}
