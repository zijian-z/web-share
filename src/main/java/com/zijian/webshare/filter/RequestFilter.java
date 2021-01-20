package com.zijian.webshare.filter;

import com.zijian.webshare.user.User;
import com.zijian.webshare.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Component
public class RequestFilter extends HttpFilter {
    private final HttpSession httpSession;
    private final HashMap<String, User> map;
    private final UserService userService;

    @Autowired
    public RequestFilter(HttpSession httpSession, HashMap<String, User> map,
                         UserService userService) {
        this.httpSession = httpSession;
        this.map = map;
        this.userService = userService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET") || request.getRequestURI().contains("users/register")) {
            chain.doFilter(request, response);
        } else {
            if (httpSession != null && httpSession.getAttribute("user") != null) {
                String username = httpSession.getAttribute("user").toString();
                if (!map.containsKey(username)) {
                    Optional<User> optional = userService.findByUsername(username);
                    optional.ifPresent(user -> map.put(username, user));
                }
                chain.doFilter(request, response);
            } else {
                response.setStatus(403);
            }
        }
    }
}
