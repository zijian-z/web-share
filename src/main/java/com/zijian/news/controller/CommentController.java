package com.zijian.news.controller;

import com.zijian.news.dao.*;
import com.zijian.news.mapper.CommentMapper;
import com.zijian.news.mapper.LinkMapper;
import com.zijian.news.mapper.NotifyMapper;
import com.zijian.news.mapper.UserMapper;
import com.zijian.news.query.CommentQuery;
import com.zijian.news.exception.ResourceEmptyException;
import com.zijian.news.util.CommentUtil;
import com.zijian.news.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final LinkMapper linkMapper;
    private final HttpSession httpSession;
    private final NotifyMapper notifyMapper;

    @Autowired
    public CommentController(UserMapper userMapper, CommentMapper commentMapper,
                             LinkMapper linkMapper, HttpSession httpSession,
                             NotifyMapper notifyMapper) {
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.linkMapper = linkMapper;
        this.httpSession = httpSession;
        this.notifyMapper = notifyMapper;
    }

    @PostMapping("/")
    public ResponseEntity<CommentVO> addComment(@RequestBody CommentQuery query) {
        Link link = linkMapper.findById(query.getLinkId());
        if (link == null) {
            throw new ResourceEmptyException("link.empty");
        }

        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        Comment comment = new Comment(userId, query.getLinkId(), query.getContent(), System.currentTimeMillis());
        commentMapper.insert(comment);

        // 保存评论通知
        Notify notify = new Notify(userId, link.getUserId(), query.getLinkId(), comment.getId(), comment.getCreateTime(), NotifyType.COMMENT, 1);
        notifyMapper.insert(notify);
        // 保存@通知
        if (query.getContent().contains("@")) {
            notify.setNotifyType(NotifyType.AT);
            for (String username : CommentUtil.findAtUsers(query.getContent())) {
                User user = userMapper.findByUsername(username);
                if (user != null) {
                    notify.setAtUserId(user.getId());
                    notifyMapper.insert(notify);
                }
            }
        }

        return new ResponseEntity<>(
                new CommentVO(
                        comment,
                        userMapper.findUsernameByUserId(comment.getUserId())
                ), HttpStatus.OK
        );
    }
}
