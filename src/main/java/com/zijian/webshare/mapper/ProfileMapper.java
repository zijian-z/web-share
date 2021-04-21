package com.zijian.webshare.mapper;

import com.zijian.webshare.dao.Profile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProfileMapper {
    @Select("select id, user_id, sex, bio from profile where user_id = #{userId}")
    Profile findByUserId(@Param("userId") Long userId);

    @Update("update profile set sex = #{sex}, bio = #{bio} where user_id = #{userId}")
    void update(Profile profile);

    @Insert("insert into profile (user_id, sex, bio) values (#{userId}, #{sex}, #{bio})")
    void insert(Profile profile);
}
