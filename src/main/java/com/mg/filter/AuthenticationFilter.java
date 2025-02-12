package com.mg.filter;

import com.mg.model.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = { "/back-office/*", "/front-office/reservations/*" })
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestUri = httpRequest.getRequestURI();
        boolean isBackOffice = requestUri.contains("/back-office/");

        if (session != null && session.getAttribute("user") != null) {
            Utilisateur user = (Utilisateur) session.getAttribute("user");

            if (isBackOffice && !"admin".equals(user.getRole())) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/front-office/vols/list");
                return;
            }

            chain.doFilter(request, response);
        } else {
            if (isBackOffice) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/back-office/login.jsp");
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/front-office/login.jsp");
            }
        }
    }

    @Override
    public void destroy() {
    }
}