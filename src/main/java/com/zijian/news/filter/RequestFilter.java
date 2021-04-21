package com.zijian.news.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class RequestFilter extends HttpFilter {
    private final HttpSession httpSession;

    @Autowired
    public RequestFilter(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET") || request.getRequestURI().contains("users/register")) {
            chain.doFilter(request, response);
        } else {
            if (httpSession != null && httpSession.getAttribute("userId") != null) {
                chain.doFilter(request, response);
            } else {
                response.setStatus(403);
            }
        }
    }
}
