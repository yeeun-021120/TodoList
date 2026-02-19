package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import model.MemberDao;
import model.MemberDto;
import model.TodoDao;
import model.CategoryDao;

/*
 * MemberDeleteServlet
 * -----------------------
 * 회원 탈퇴 처리
 *  - 로그인 사용자만 가능
 *  - Todo → Category → Member 순서로 삭제 (FK 방지)
 *  - 세션 종료
 *  - 로그인 페이지로 이동 + 탈퇴 완료 메시지
 */
public class MemberDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        // 로그인 안 한 상태면 로그인 페이지로
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        int memberId = user.getId();

        // 🔥 삭제 순서 중요 (FK 제약 때문에)
        new TodoDao().deleteByMember(memberId);
        new CategoryDao().deleteByMember(memberId);
        new MemberDao().deleteMember(memberId);

        // 세션 종료 (완전 로그아웃)
        session.invalidate();

        // 탈퇴 완료 메시지와 함께 로그인 페이지로
        response.sendRedirect(
            request.getContextPath() + "/member/login.jsp?msg=deleted"
        );
    }
}
