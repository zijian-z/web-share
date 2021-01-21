package com.zijian.webshare.comment;

import com.zijian.webshare.link.Link;
import com.zijian.webshare.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 创建一条评论
     * @param content 评论内容
     * @param createUser 评论者
     * @param link 评论的链接
     * @return
     */
    public Comment save(String content, User createUser, Link link) {
        return commentRepository.save(new Comment(content, createUser, link));
    }
}
