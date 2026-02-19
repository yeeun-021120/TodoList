package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.CategoryDao;
import model.TodoDao;
import model.MemberDto;

public class CategoryDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        // ⭐ 1. 해당 카테고리의 Todo 먼저 삭제
        TodoDao todoDao = new TodoDao();
        todoDao.deleteByCategory(categoryId, user.getId());

        // ⭐ 2. 카테고리 삭제
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.deleteCategory(categoryId, user.getId());

        response.sendRedirect(request.getContextPath() + "/category/manage");
    }
}
