package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.MemberDao;
import model.MemberDto;

/*
 * MemberEditServlet
 * -----------------------
 * 회원정보 수정 페이지
 *  - GET  : 수정 페이지 이동
 *  - POST : 비밀번호 변경 처리
 *
 * msg 파라미터 규칙
 *  - changed : 비밀번호 변경 성공
 *  - wrongPw : 현재 비밀번호 불일치
 */
public class MemberEditServlet extends HttpServlet {

    // ✅ 수정 페이지 열기
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        // 로그인 안 했으면 로그인 페이지로
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        // 회원 정보 전달
        request.setAttribute("user", user);
        request.getRequestDispatcher("/member/memberEdit.jsp")
               .forward(request, response);
    }

    // ✅ 비밀번호 변경 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String currentPw = request.getParameter("currentPw");
        String newPw = request.getParameter("newPw");

        MemberDao dao = new MemberDao();

        // ❌ 현재 비밀번호가 틀린 경우
        if (!dao.checkPassword(user.getId(), currentPw)) {
            response.sendRedirect(
                request.getContextPath() + "/member/edit?msg=wrongPw"
            );
            return;
        }

        // ⭕ 현재 비밀번호가 맞는 경우 → 비밀번호 변경
        dao.updatePassword(user.getId(), newPw);

        response.sendRedirect(
            request.getContextPath() + "/member/edit?msg=changed"
        );
    }
}
