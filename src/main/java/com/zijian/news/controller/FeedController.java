package com.zijian.news.controller;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.zijian.news.dao.Feed;
import com.zijian.news.dao.FeedContent;
import com.zijian.news.exception.RequestException;
import com.zijian.news.exception.ResourceEmptyException;
import com.zijian.news.mapper.FeedMapper;
import com.zijian.news.query.FeedQuery;
import com.zijian.news.vo.FeedContentVO;
import com.zijian.news.vo.FeedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {
    private FeedMapper feedMapper;
    private HttpSession httpSession;

    @Autowired
    public FeedController(FeedMapper feedMapper, HttpSession httpSession) {
        this.feedMapper = feedMapper;
        this.httpSession = httpSession;
    }

    @GetMapping("/")
    public List<FeedVO> getFeeds() {
        if (httpSession.getAttribute("userId") == null) {
            throw new RequestException("not.login");
        }
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        List<FeedVO> feedVOS = feedMapper.select(userId);
        for (FeedVO feedVO : feedVOS) {
            feedVO.setUnreadCount(feedMapper.countUnread(userId, feedVO.getId()));
        }
        return feedVOS;
    }

    @PostMapping("/")
    public FeedVO addFeed(@RequestBody FeedQuery query) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        Long folderId = feedMapper.selectFolderId(userId, query.getFolderName());
        if (folderId == null) {
            throw new ResourceEmptyException("folder.empty");
        }
        try {
            SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(new URL(query.getFeedUrl())));
            Feed feed = new Feed(userId, folderId, query.getFeedUrl(), syndFeed.getTitle());
            feedMapper.insertFeed(feed);
            return new FeedVO(feed.getId(), query.getFolderName(), feed.getFeedUrl(), feed.getFeedName());
        } catch (FeedException | IOException e) {
            e.printStackTrace();
        }
        throw new RequestException("url.error");
    }

    @PostMapping("/folder")
    public ResponseEntity<String> addFolder(@RequestParam String folderName) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        Long folderId = feedMapper.selectFolderId(userId, folderName);
        if (folderId == null) {
            feedMapper.insertFolder(userId, folderName);
        }
        return new ResponseEntity<>(folderName, HttpStatus.OK);
    }

    private void updateContent(Long userId, Long feedId) {
        try {
            Long lastPublishTime = feedMapper.selectLastPublishTime(feedId);
            String feedUrl = feedMapper.selectUrl(feedId);
            if (feedUrl == null) {
                return;
            }
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(feedUrl)));
            for (SyndEntry entry : feed.getEntries()) {
                if (lastPublishTime == null
                        || entry.getPublishedDate().getTime() > lastPublishTime) {
                    FeedContent content = new FeedContent(feedId, entry);
                    feedMapper.insertContent(content);
                    feedMapper.insertUnread(feedId, content.getId(), userId);
                } else {
                    break;
                }
            }
        } catch (FeedException | IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/content/{feedId}")
    public List<FeedContentVO> getFeedContent(@PathVariable Long feedId) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        updateContent(userId, feedId);
        return feedMapper.selectContents(feedId, userId);
    }

    @PostMapping("/read/{contentId}")
    public ResponseEntity<String> readContent(@PathVariable Long contentId) {
        Long userId = Long.valueOf(httpSession.getAttribute("userId").toString());
        feedMapper.readContent(userId, contentId);
        return new ResponseEntity<>("read.success", HttpStatus.OK);
    }
}
