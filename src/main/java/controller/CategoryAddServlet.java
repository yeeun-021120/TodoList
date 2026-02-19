package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.CategoryDao;
import model.MemberDto;

public class CategoryAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        MemberDto user = (MemberDto) request.getSession().getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String color = request.getParameter("color");

        new CategoryDao().insertCategory(user.getId(), name, color);

        response.sendRedirect(request.getContextPath() + "/category/manage");
    }
}
