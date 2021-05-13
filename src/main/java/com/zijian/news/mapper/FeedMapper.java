package com.zijian.news.mapper;

import java.util.List;
import com.zijian.news.dao.Feed;
import com.zijian.news.dao.FeedContent;
import com.zijian.news.vo.FeedContentVO;
import com.zijian.news.vo.FeedVO;
import org.apache.ibatis.annotations.*;

public interface FeedMapper {
    @Insert("insert into feed_folder (user_id, folder_name) values (#{userId}, #{folderName})")
    void insertFolder(@Param("userId") Long userId, @Param("folderName") String folderName);

    @Select("select id from feed_folder where user_id = #{userId} and folder_name = #{folderName}")
    Long selectFolderId(@Param("userId") Long userId, @Param("folderName") String folderName);

    @Insert("insert into feed (user_id, folder_id, feed_url, feed_name) values" +
            "(#{userId}, #{folderId}, #{feedUrl}, #{feedName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFeed(Feed feed);

    @Select("select f.id, ff.folder_name, f.feed_url, f.feed_name from feed_folder ff left join feed f on ff.id = f.folder_id where ff.user_id = #{userId}")
    List<FeedVO> select(@Param("userId") Long userId);

    @Select("select count(*) from user_read where is_read = 0 and user_id = #{userId} and feed_id = #{feedId}")
    Long countUnread(@Param("userId") Long userId, @Param("feedId") Long feedId);

    @Select("select feed_url from feed where id = #{feedId}")
    String selectUrl(@Param("feedId") Long feedId);

    @Select("select max(publish_time) from feed_content where feed_id = #{feedId}")
    Long selectLastPublishTime(@Param("feedId") Long feedId);

    @Insert("insert into feed_content (feed_id, title, description, publish_time, link) " +
            "values (#{feedId}, #{title}, #{description}, #{publishTime}, #{link})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertContent(FeedContent content);

    @Insert("insert into user_read (feed_id, content_id, user_id) values (#{feedId}, #{contentId}, #{userId})")
    void insertUnread(@Param("feedId") Long feedId, @Param("contentId") Long contentId, @Param("userId") Long userId);

    @Select("select fc.id, fc.title, fc.description, fc.publish_time, fc.link, ur.is_read from feed_content fc inner join user_read ur " +
            "on fc.id = ur.content_id and fc.feed_id = #{feedId} and ur.user_id = #{userId}")
    List<FeedContentVO> selectContents(@Param("feedId") Long feedId, @Param("userId") Long userId);

    @Update("update user_read set is_read = 1 where user_id = #{userId} and content_id = #{contentId}")
    void readContent(@Param("userId") Long userId, @Param("contentId") Long contentId);
}
