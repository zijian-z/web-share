package com.zijian.news;

import com.zijian.news.util.UserLikeUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebShareApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(WebShareApplicationTests.class);

    @Autowired
    private UserLikeUtil userLikeUtil;

    @Test
    void contextLoads() {
    }

    @Test
    void redisTest() {
        userLikeUtil.addLike(1l, 1l);
        userLikeUtil.addLike(1l, 2l);
        userLikeUtil.addLike(2l, 1l);
        userLikeUtil.linkLikeSort(0, 50);
    }
}
