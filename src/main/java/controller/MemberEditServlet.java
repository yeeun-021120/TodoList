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

        String currentPw = request.getParameter("currentPw");
        String newPw = request.getParameter("newPw");

        MemberDao dao = new MemberDao();

        // 현재 비밀번호 확인
        if (!dao.checkPassword(user.getId(), currentPw)) {
            request.setAttribute("error", "현재 비밀번호가 틀렸습니다.");
            request.getRequestDispatcher("/member/memberEdit.jsp")
                   .forward(request, response);
            return;
        }

        // 비밀번호 변경
        dao.updatePassword(user.getId(), newPw);

        response.sendRedirect(request.getContextPath() + "/mypage");
    }
}
