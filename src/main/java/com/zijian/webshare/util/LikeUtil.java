package com.zijian.webshare.util;

import com.zijian.webshare.link.Link;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class LikeUtil {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LikeUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final String TOTAL_COUNT_SORT_KEY = "total:count:sort:key";
    private String linkLikeKey(Long linkId) {
        return "link:like:" + linkId;
    }
    private String linkCountKey(Long linkId) { return "count:link:like:" + linkId; }
    private String userLikeKey(Long userId) {
        return "user:like:" + userId;
    }

//    private long likeCount(Long linkId) {
//        Long likeSize = redisTemplate.opsForSet().size(linkLikeKey(linkId));
//        return likeSize == null ? 0 : likeSize;
//    }

    public boolean userLiked(Long linkId, Long userId) {
        //用户是否已经点赞过
        Boolean hasLike = redisTemplate.opsForSet().isMember(userLikeKey(userId), String.valueOf(linkId));
        return hasLike != null && hasLike;
    }

    public void cancelLike(Long linkId, Long userId) {
        //用户没有点赞过，不能取消点赞
        if (!userLiked(linkId, userId)) return;

        redisTemplate.opsForSet().remove(userLikeKey(userId), String.valueOf(linkId));
        redisTemplate.opsForSet().remove(linkLikeKey(linkId), String.valueOf(userId));
        redisTemplate.opsForZSet().incrementScore(TOTAL_COUNT_SORT_KEY, linkCountKey(linkId), -1);
    }

    public void addLike(Long linkId, Long userId) {
        //用户已经点赞过
        if (userLiked(linkId, userId)) return;

        redisTemplate.opsForSet().add(userLikeKey(userId), String.valueOf(linkId));
        redisTemplate.opsForSet().add(linkLikeKey(linkId), String.valueOf(userId));
        redisTemplate.opsForZSet().incrementScore(TOTAL_COUNT_SORT_KEY, linkCountKey(linkId), 1);
    }

    /**
     * 根据offset和count返回最热的linkId
     * @param offset
     * @param count
     * @return
     */
    public ArrayList<Long> linkLikeSort(long offset, long count) {
        Set<String> set = redisTemplate.opsForZSet().reverseRangeByScore(TOTAL_COUNT_SORT_KEY, 0, Long.MAX_VALUE, offset, count);
        ArrayList<Long> list = new ArrayList<>();
        if (set != null) {
            for (String value : set) {
                list.add(Long.valueOf(value.substring(value.lastIndexOf(':') + 1)));
            }
        }
        return list;
    }
}
