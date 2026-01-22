package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.CategoryDao;
import model.MemberDto;

/*
 * CategoryDeleteServlet
 * -----------------------------
 * 카테고리 삭제 처리
 */
public class CategoryDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 확인
        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        CategoryDao dao = new CategoryDao();
        dao.deleteCategory(categoryId, user.getId());

        // 다시 카테고리 관리 화면으로
        response.sendRedirect(request.getContextPath() + "/category/manage");
    }
}
