package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * LogoutServlet
 * ------------------------------------------------------
 * 로그아웃 처리
 *  - 세션 무효화
 *  - keepLogin으로 늘려놓은 JSESSIONID 쿠키도 삭제(원하면)
 *
 * 아이디 저장(rememberId)는 "로그인 화면 체크박스"에서 관리하므로 여기서 안 지움
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) 세션 제거
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        // 2) (선택) JSESSIONID 쿠키 삭제 - keepLogin 해제 효과
        Cookie jsid = new Cookie("JSESSIONID", "");
        jsid.setPath(request.getContextPath());
        jsid.setMaxAge(0);
        response.addCookie(jsid);
        
        // 로그아웃 완료 메시지와 함께 로그인 페이지로 이동
        response.sendRedirect(
            request.getContextPath() + "/member/login.jsp?msg=logout"
        );
    }
}
