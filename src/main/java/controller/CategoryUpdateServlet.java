package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.CategoryDao;

/**
 * 카테고리 수정 처리 서블릿
 * /category/update
 */
public class CategoryUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 값 받기
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String color = request.getParameter("color");

        // 2. DB 수정
        CategoryDao dao = new CategoryDao();
        dao.update(id, name, color);

        // 3. 다시 관리 페이지로
        response.sendRedirect(request.getContextPath() + "/category/manage");
    }
}
