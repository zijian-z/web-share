package com.zijian.webshare.util;

import java.util.HashSet;
import java.util.Set;

public class CommentUtil {

    public static Set<String> findAtUsers(String content) {
        Set<String> users = new HashSet<>();
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '@') {
                i++;
                StringBuilder username = new StringBuilder();
                while (i < content.length() && content.charAt(i) != ' ') {
                    username.append(content.charAt(i++));
                }
                if (username.length() > 0) {
                    users.add(username.toString());
                }
            }
        }
        return users;
    }
}
