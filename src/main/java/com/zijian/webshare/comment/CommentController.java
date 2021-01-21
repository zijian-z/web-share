package com.zijian.webshare.comment;

import com.zijian.webshare.exception.ResourceEmptyException;
import com.zijian.webshare.link.Link;
import com.zijian.webshare.link.LinkService;
import com.zijian.webshare.notify.NotifyService;
import com.zijian.webshare.notify.NotifyType;
import com.zijian.webshare.user.User;
import com.zijian.webshare.user.UserService;
import com.zijian.webshare.util.CommentUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final HashMap<String, User> map;
    private final NotifyService notifyService;

    @Autowired
    public CommentController(CommentService commentService, LinkService linkService,
                             HttpSession httpSession, HashMap<String, User> map,
                             NotifyService notifyService) {
        this.commentService = commentService;
        this.linkService = linkService;
        this.httpSession = httpSession;
        this.map = map;
        this.notifyService = notifyService;
    }

    @PostMapping("/")
    public ResponseEntity<Comment> addComment(@RequestBody CommentVO commentVO) {
        Optional<Link> optional = linkService.findById(commentVO.getLinkId());
        if (optional.isPresent()) {
            User user = map.get(httpSession.getAttribute("user").toString());
            Comment savedComment = commentService.save(
                    commentVO.getContent(), user, optional.get());

            // 保存评论通知
            notifyService.save(user, savedComment.getLink().getUser(), savedComment.getLink(), savedComment);
            // 保存@通知
            if (savedComment.getContent().contains("@")) {
                notifyService.save(user, CommentUtil.findAtUsers(savedComment.getContent()), savedComment.getLink(), savedComment);
            }

            return new ResponseEntity<>(savedComment, HttpStatus.OK);
        } else {
            throw new ResourceEmptyException("link.empty");
        }
    }
}
