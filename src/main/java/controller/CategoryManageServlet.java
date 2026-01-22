package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

import model.CategoryDao;
import model.CategoryDto;
import model.MemberDto;

/**
 * 카테고리 관리 화면 출력
 */
public class CategoryManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 로그인 체크
        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        // ✅ 카테고리 조회
        CategoryDao dao = new CategoryDao();
        ArrayList<CategoryDto> list = dao.getListByMember(user.getId());

        // ✅ JSP에서 사용할 수 있도록 전달
        request.setAttribute("categories", list);

        // ✅ JSP 이동
        request.getRequestDispatcher("/member/categoryManage.jsp")
               .forward(request, response);
    }
}
