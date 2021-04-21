package com.zijian.news.schedule;

import com.zijian.news.dao.TotalLike;
import com.zijian.news.dao.UserLike;
import com.zijian.news.mapper.UserLikeMapper;
import com.zijian.news.util.UserLikeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisToDB {
    private final Logger logger = LoggerFactory.getLogger(RedisToDB.class.getName());

    private final RedisTemplate<String, String> redisTemplate;
    private final UserLikeMapper userLikeMapper;

    public RedisToDB(RedisTemplate<String, String> redisTemplate, UserLikeMapper userLikeMapper) {
        this.redisTemplate = redisTemplate;
        this.userLikeMapper = userLikeMapper;
    }

    @Scheduled(fixedRate = 3000)
    public void flush() {
//        logger.info("保存点赞信息到数据库");
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(UserLikeUtil.LINK_TOTAL_LIKE_COUNT, 0, Long.MAX_VALUE);
        for (ZSetOperations.TypedTuple<String> pair : set) {
            userLikeMapper.insertTotalLike(new TotalLike(Long.valueOf(pair.getValue()), Math.round(pair.getScore())));
        }
        Set<String> userLikeChangedKeys = redisTemplate.opsForSet().members(UserLikeUtil.LINK_USER_CHANGED);
        for (String key : userLikeChangedKeys) {
            String[] linkWithUser = key.split(":");
            if (linkWithUser.length != 4) continue;
            userLikeMapper.insertUserLike(new UserLike(
                    Long.valueOf(linkWithUser[2]),
                    Long.valueOf(linkWithUser[3]),
                    Integer.valueOf(redisTemplate.opsForValue().get(key))));
        }
        redisTemplate.delete(UserLikeUtil.LINK_USER_CHANGED);
    }
}
