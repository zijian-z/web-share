package com.zijian.news.mapper;

import com.zijian.news.dao.Notify;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NotifyMapper {
    @Insert("insert into notify (user_id, at_user_id, link_id, comment_id, create_time, notify_type, is_unread) " +
            "values (#{userId}, #{atUserId}, #{linkId}, #{commentId}, #{createTime}, #{notifyType}, #{unread})")
    void insert(Notify notify);

    @Update("update notify set is_unread = 0 where id = #{id}")
    void readNotify(@Param("id") Long notifyId);

    /**
     * 根据at的用户id查找通知，注意构造函数要一致
     * @param atUserId
     * @return
     */
    @Results({
            @Result(property = "unread", column = "is_unread")
    })
    @Select("select id, user_id, link_id, notify_type, is_unread from notify where at_user_id = #{atUserId} and user_id != at_user_id")
    List<Notify> findByAtUserId(@Param("atUserId") Long atUserId);
}
