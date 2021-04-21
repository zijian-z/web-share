package com.zijian.news.controller;

import com.zijian.news.dao.Notify;
import com.zijian.news.mapper.NotifyMapper;
import com.zijian.news.mapper.UserMapper;
import com.zijian.news.vo.NotifyVO;
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
@RequestMapping("/notify")
public class NotifyController {
    private final Logger logger = LoggerFactory.getLogger(NotifyController.class.getName());

    private final UserMapper userMapper;
    private final NotifyMapper notifyMapper;
    private final HttpSession httpSession;

    @Autowired
    public NotifyController(UserMapper userMapper, NotifyMapper notifyMapper,
                            HttpSession httpSession) {
        this.userMapper = userMapper;
        this.notifyMapper = notifyMapper;
        this.httpSession = httpSession;
    }

    @PostMapping("/")
    public List<NotifyVO> findAllNotify() {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        List<Notify> notifies = notifyMapper.findByAtUserId(userId);
        List<NotifyVO> notifyVOS = new ArrayList<>(notifies.size());
        for (Notify notify : notifies) {
            notifyVOS.add(new NotifyVO(notify, userMapper.findUsernameByUserId(notify.getUserId())));
        }
        return notifyVOS;
    }

    @PostMapping("/{notifyId}")
    public ResponseEntity<String> readNotify(@PathVariable Long notifyId) {
        notifyMapper.readNotify(notifyId);
        return new ResponseEntity<>("read.notify.success", HttpStatus.OK);
    }
}
