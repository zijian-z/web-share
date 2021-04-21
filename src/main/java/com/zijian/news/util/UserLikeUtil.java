package com.zijian.news.util;

import com.zijian.news.mapper.UserLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class UserLikeUtil {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserLikeMapper userLikeMapper;

    public static final String LINK_TOTAL_LIKE_COUNT = "link:total:like:count";
    public static final String LINK_USER_CHANGED = "link:user:changed";
    public static final String LIKE_STATUS = "like:status:";

    @Autowired
    public UserLikeUtil(RedisTemplate<String, String> redisTemplate, UserLikeMapper userLikeMapper) {
        this.redisTemplate = redisTemplate;
        this.userLikeMapper = userLikeMapper;
    }

    public static String linkWithUser(Long linkId, Long userId) {
        return LIKE_STATUS + linkId + ":" + userId;
    }

    public boolean checkUserLiked(Long linkId, Long userId) {
        String key = linkWithUser(linkId, userId);
        //更新热点key的过期时间
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            //key失效后查数据库
            Integer status = userLikeMapper.selectLikeByLinkAndUser(linkId, userId);
            if (status == null) {
                value = "0";
            } else {
                value = status.toString();
            }
            redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
        }
        return value.equals("1");
    }

    public void cancelLike(Long linkId, Long userId) {
        //用户没有点赞过，不能取消点赞
        if (!checkUserLiked(linkId, userId)) return;

        String key = linkWithUser(linkId, userId);
        redisTemplate.opsForValue().set(key, "0");
        redisTemplate.opsForSet().add(LINK_USER_CHANGED, key);
        redisTemplate.opsForZSet().incrementScore(LINK_TOTAL_LIKE_COUNT, String.valueOf(linkId), -1);
    }

    public void addLike(Long linkId, Long userId) {
        //用户已经点赞过
        if (checkUserLiked(linkId, userId)) return;

        String key = linkWithUser(linkId, userId);
        redisTemplate.opsForValue().set(key, "1");
        redisTemplate.opsForSet().add(LINK_USER_CHANGED, key);
        redisTemplate.opsForZSet().incrementScore(LINK_TOTAL_LIKE_COUNT, linkId.toString(), 1);
    }

    /**
     * 根据offset和count返回最热的linkId
     * @param offset
     * @param count
     * @return
     */
    public ArrayList<Long> linkLikeSort(long offset, long count) {
        Set<String> set = redisTemplate.opsForZSet().reverseRangeByScore(LINK_TOTAL_LIKE_COUNT, 0, Long.MAX_VALUE, offset, count);
        ArrayList<Long> list = new ArrayList<>();
        // value 和 score
        for (String value : set) {
            list.add(Long.valueOf(value));
        }
        return list;
    }
}
