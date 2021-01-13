package com.zijian.webshare;

import com.zijian.webshare.link.Link;
import com.zijian.webshare.link.LinkRepository;
import com.zijian.webshare.link.LinkService;
import com.zijian.webshare.user.User;
import com.zijian.webshare.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
class WebShareApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    LinkService linkService;

    @Test
    void contextLoads() {
    }

    @Test
    void register() {
        userService.register("zhangsan", "zhangsan");
    }

    @Test
    void login() {
    }

    @Test
    void addLink() {
        User user = userService.findByUsername("zhangsan").get();
        System.out.println(user);
        linkService.addLink(new Link("https://www.baidu.com", System.currentTimeMillis(), user));
        System.out.println(linkService.findAllByUserId(user.getId(), PageRequest.of(1, 20)).getTotalElements());
    }
}
