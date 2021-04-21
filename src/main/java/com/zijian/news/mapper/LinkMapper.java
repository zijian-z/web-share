package com.zijian.news.mapper;

import com.zijian.news.dao.Link;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LinkMapper {
    @Insert("insert into link (user_id, title, url, create_time) " +
            "values (#{userId}, #{title}, #{url}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Link link);

    @Select("select id, user_id, title, url, create_time from link where id = #{id}")
    @Results ({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createTime", column = "create_time")
    })
    Link findById(@Param("id") Long linkId);

    @Select("select id, user_id, title, url, create_time from link where user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createTime", column = "create_time")
    })
    List<Link> findByUserId(@Param("userId") Long userId);

    @Select("select id, user_id, title, url, create_time from link order by create_time desc limit 50")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createTime", column = "create_time")
    })
    List<Link> findTop50();
}
