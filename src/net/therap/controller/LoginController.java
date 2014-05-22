package net.therap.controller;

import net.therap.domain.User;
import net.therap.service.LoginService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shakhawat.hossain
 * Date: 5/19/14
 * Time: 11:46 AM
 */
@WebServlet ("/login")
public class LoginController extends HttpServlet {
    private final String LOGIN_PAGE = "/WEB-INF/jsp/login.jsp";
    private final String HOME_REQUEST = "/home";
    private final String UPDATE_MEAL_PLAN_REQUEST = "update-meal-plan";
    private LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String admin = req.getParameter("admin");

        User user = new User(userId, password);
        if (admin != null) {
            user.setAdmin(true);
        } else {
            user.setAdmin(false);
        }

        boolean isValidUser = loginService.isValidUser(user);
        if (isValidUser) {
            HttpSession session = req.getSession();
            session.setAttribute("authenticatedUser", user);

            if (user.isAdmin()) {
                resp.sendRedirect(UPDATE_MEAL_PLAN_REQUEST);
            } else {
                resp.sendRedirect(HOME_REQUEST);
            }
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PAGE);
            req.setAttribute("message", "wrong userid or password");
            requestDispatcher.forward(req, resp);
        }

    }
}
