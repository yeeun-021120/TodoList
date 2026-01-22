package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.MemberDao;
import model.TodoDao;
import model.CategoryDao;
import model.MemberDto;

/**
 * 회원 탈퇴 처리 서블릿
 * ------------------------------------
 * ✔ 로그인 확인
 * ✔ Todo 삭제
 * ✔ Category 삭제
 * ✔ Member 삭제
 * ✔ 세션 종료
 */
public class MemberDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 세션 확인
        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            // 로그인 안 된 상태면 로그인 페이지로
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        int memberId = user.getId();

        // ✅ 1. Todo 삭제
        TodoDao todoDao = new TodoDao();
        todoDao.deleteByMember(memberId);

        // ✅ 2. Category 삭제
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.deleteByMember(memberId);

        // ✅ 3. Member 삭제
        MemberDao memberDao = new MemberDao();
        memberDao.deleteMember(memberId);

        // ✅ 4. 세션 제거
        session.invalidate();

        // ✅ 5. 로그인 화면으로 이동
        response.sendRedirect(request.getContextPath() + "/member/login.jsp");
    }
}
