package com.zijian.webshare;

import com.zijian.webshare.util.CommentUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;


@SpringBootTest
public class CommentUtilTest {
    private static final Logger log = LoggerFactory.getLogger(CommentUtilTest.class);

    @Test
    public void testFindAtUsers() {
        Set<String> set = CommentUtil.findAtUsers("dfsajkl@zzj fdsalj@orl");
        for (String username : set) {
            log.info(username);
        }
    }
}
