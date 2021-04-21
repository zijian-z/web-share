package com.zijian.news.mapper;

import com.zijian.news.dao.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Select("select username from user where id = #{id}")
    String findUsernameByUserId(@Param("id") Long id);

    @Select("select id, username, email, password from user where username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("select id, username, email, password from user where email = #{email}")
    User findByEmail(@Param("email") String email);

    @Insert("insert into user (username, email, password) values (#{username}, #{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("update user set email = #{email} where id = #{id}")
    void updateEmail(@Param("email") String email, @Param("id") Long userId);
}
