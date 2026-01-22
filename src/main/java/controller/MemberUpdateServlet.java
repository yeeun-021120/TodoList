package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.MemberDao;
import model.MemberDto;

public class MemberUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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
            request.setAttribute("error", "비밀번호가 틀립니다.");
            request.getRequestDispatcher("/member/memberEdit.jsp")
                   .forward(request, response);
            return;
        }

        // 비밀번호 변경
        dao.updatePassword(user.getId(), newPw);

        response.sendRedirect(request.getContextPath() + "/mypage");
    }
}
