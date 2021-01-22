package com.zijian.webshare.notify;

import com.zijian.webshare.comment.Comment;
import com.zijian.webshare.link.Link;
import com.zijian.webshare.user.User;
import com.zijian.webshare.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class NotifyService {
    private final NotifyRepository notifyRepository;
    private final UserService userService;

    @Autowired
    public NotifyService(NotifyRepository notifyRepository, UserService userService) {
        this.notifyRepository = notifyRepository;
        this.userService = userService;
    }

    /**
     * 通过notify id查找通知
     * @param id
     * @return
     */
    public Optional<Notify> findById(Long id) {
        return notifyRepository.findById(id);
    }

    /**
     * 查看 被@ 或者 被评论 的通知
     * @param consumeUser
     * @return
     */
    public Page<Notify> findByConsumeUser(User consumeUser, Pageable pageable){
        return notifyRepository.findByConsumeUser(consumeUser, pageable);
    }

    /**
     * 通知是系统默认的动作，不需要返回
     * @param notify
     */
    public void save(Notify notify) {
        notifyRepository.save(notify);
    }

    /**
     * 保存@通知
     * @param createUser 评论人
     * @param users @的所有人
     * @param link 所属的link
     * @param comment 所属的comment
     */
    public void save(User createUser, Set<String> users, Link link, Comment comment) {
        for (String username : users) {
            Optional<User> optional = userService.findByUsername(username);
            if (optional.isPresent()) {
                User user = optional.get();
                save(new Notify(createUser, user, link, comment, NotifyType.AT_YOU, true));
            }
        }
    }

    /**
     * 保存评论通知
     * @param createUser 评论人
     * @param consumeUser link创建者
     * @param link 所属的link
     * @param comment 所属的comment
     */
    public void save(User createUser, User consumeUser, Link link, Comment comment) {
        save(new Notify(createUser, consumeUser, link, comment, NotifyType.COMMENT_YOU, true));
    }

    /**
     * 读取通知
     * @param notify
     */
    public Notify readNotify(Notify notify) {
        notify.setUnread(false);
        return notifyRepository.save(notify);
    }
}
