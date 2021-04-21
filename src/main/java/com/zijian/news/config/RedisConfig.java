package com.zijian.news.config;

import com.zijian.news.dao.TotalLike;
import com.zijian.news.dao.UserLike;
import com.zijian.news.mapper.UserLikeMapper;
import com.zijian.news.util.UserLikeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisConfig {
    private final Logger logger = LoggerFactory.getLogger(RedisConfig.class.getName());

    private final RedisTemplate<String, String> redisTemplate;
    private final UserLikeMapper userLikeMapper;

    @Autowired
    public RedisConfig(RedisTemplate<String, String> redisTemplate, UserLikeMapper userLikeMapper) {
        this.redisTemplate = redisTemplate;
        this.userLikeMapper = userLikeMapper;
    }

    @PostConstruct
    public void init() {
        logger.info("初始化Redis");
        // 删除原有的key
        redisTemplate.delete(UserLikeUtil.LINK_USER_CHANGED);
        redisTemplate.delete(redisTemplate.keys(UserLikeUtil.LIKE_STATUS + "*"));
        redisTemplate.delete(UserLikeUtil.LINK_TOTAL_LIKE_COUNT);

        List<UserLike> userLikes = userLikeMapper.selectLiked();
        for (UserLike userLike : userLikes) {
            redisTemplate.opsForValue().set(
                    UserLikeUtil.linkWithUser(userLike.getLinkId(), userLike.getUserId()),
                    "1", 30, TimeUnit.MINUTES);
        }
        List<TotalLike> totalLikes = userLikeMapper.selectTotalLike();
        for (TotalLike totalLike : totalLikes) {
            redisTemplate.opsForZSet().add(UserLikeUtil.LINK_TOTAL_LIKE_COUNT, totalLike.getLinkId().toString(), totalLike.getLikeCount());
        }
    }
}
