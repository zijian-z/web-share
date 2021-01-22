package com.zijian.webshare;

import com.zijian.webshare.comment.CommentService;
import com.zijian.webshare.exception.GlobalExceptionHandler;
import com.zijian.webshare.link.Link;
import com.zijian.webshare.link.LinkRepository;
import com.zijian.webshare.link.LinkService;
import com.zijian.webshare.user.User;
import com.zijian.webshare.user.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
class WebShareApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(WebShareApplicationTests.class);

    @Autowired
    UserService userService;
    @Autowired
    LinkService linkService;
    @Autowired
    CommentService commentService;
    @Autowired
    StringRedisTemplate template;

    @Test
    void contextLoads() {
    }

    @Test
    void register() {
//        userService.register("zhangsan", "zhangsan");
    }

    @Test
    void addLink() {
//        User user = userService.findByUsername("zhangsan").get();
//        linkService.save(new Link("https://www.baidu.com", System.currentTimeMillis(), user, null));
    }

    @Test
    void addComment() {
//        User user = userService.findByUsername("zhangsan").get();
//        Page<Link> links = linkService.findAllByUserId(user.getId(), PageRequest.of(0, 20));
//        commentService.save("测试评论", user, null, links.getContent().get(0));
    }
}
