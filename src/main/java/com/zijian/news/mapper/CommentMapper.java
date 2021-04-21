package com.zijian.news.mapper;

import com.zijian.news.dao.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    @Insert("insert into comment (user_id, link_id, content, create_time) " +
            "values (#{userId}, #{linkId}, #{content}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Comment comment);

    @Select("select user_id, link_id, content, create_time from comment where link_id = #{linkId}")
    List<Comment> findByLinkId(@Param("linkId") Long linkId);
}
