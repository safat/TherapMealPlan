package net.therap.filter;


import net.therap.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: shakhawat.hossain
 * Date: 5/19/14
 * Time: 12:03 PM
 */
@WebFilter ("/*")
public class AuthenticationFilter implements Filter {
    private final String LOGIN_REQUEST = "/login";
    private final String HOME_REQUEST = "/home";
    private final String UPDATE_MEAL_PLAN_REQUEST = "/update-meal-plan";
    private final String RESOURCE_REQUEST = "/resources";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestedUri = request.getRequestURI();

        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);

        if(!isSessionActive(session) && (requestedUri.contains(RESOURCE_REQUEST))){
           filterChain.doFilter(request, response);
        } else if (!isSessionActive(session) && (!requestedUri.contains(LOGIN_REQUEST))) {
            response.sendRedirect(LOGIN_REQUEST);
        } else if (isSessionActive(session) && requestedUri.contains(LOGIN_REQUEST)) {
            response.sendRedirect(HOME_REQUEST);
        } else if (!isAdminSession(session) && requestedUri.contains(UPDATE_MEAL_PLAN_REQUEST)) {
            response.sendRedirect(LOGIN_REQUEST);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isSessionActive(HttpSession session) {
        if ((session != null && session.getAttribute("authenticatedUser") != null)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isAdminSession(HttpSession session) {
        if (isSessionActive(session)) {
            User user = (User) session.getAttribute("authenticatedUser");
            return user.isAdmin();
        }

        return false;
    }

    @Override
    public void destroy() {

    }


}
