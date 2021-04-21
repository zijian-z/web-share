package com.zijian.news.mapper;

import com.zijian.news.dao.TotalLike;
import com.zijian.news.dao.UserLike;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserLikeMapper {
    @Select("select is_like from user_like where link_id = #{linkId} and user_id = #{userId}")
    Integer selectLikeByLinkAndUser(@Param("linkId") Long linkId, @Param("userId") Long userId);

    @Select("select link_id, user_id from user_like where is_like = 1")
    List<UserLike> selectLiked();

    @Select("select link_id, like_count from link_total_like")
    List<TotalLike> selectTotalLike();

    @Insert("insert into link_total_like (link_id, like_count) values (#{linkId}, #{likeCount})" +
            "on duplicate key update like_count = #{likeCount}")
    void insertTotalLike(TotalLike totalLike);

    @Insert("insert into user_like (link_id, user_id, is_like) values (#{linkId}, #{userId}, #{liked})" +
            "on duplicate key update is_like = #{liked}")
    void insertUserLike(UserLike userLike);
}
