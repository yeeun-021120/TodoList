package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDao;
import model.MemberDto;

/*
 * LoginServlet
 * ------------------------------------------------------
 * login.jsp에서 로그인 처리
 *
 * 추가 기능
 *  1) 아이디 저장(rememberId)
 *  2) 로그인 유지(keepLogin) - DB 없이 JSESSIONID 쿠키 유효기간을 늘리는 방식
 *
 * 성공 시 이동
 *  - /todo/list 로 이동 (서블릿으로 이동해야 request/session 정상 유지됨)
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1) 파라미터 받기
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 체크박스 (체크되면 "on" 이 들어옴)
        String rememberId = request.getParameter("rememberId");
        String keepLogin = request.getParameter("keepLogin");

        // 2) 로그인 시도
        MemberDao dao = new MemberDao();
        MemberDto user = dao.login(username, password);

        // 3) 실패 처리
        if (user == null) {
            request.setAttribute("error", "ユーザーIDまたはパスワードが正しくありません");
            request.getRequestDispatcher("/member/login.jsp").forward(request, response);
            return;
        }

        // 4) 세션 저장
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        // --------------------------------------------------
        // 5) 아이디 저장 쿠키 처리
        // --------------------------------------------------
        if ("on".equals(rememberId)) {
            Cookie c = new Cookie("rememberId", user.getUsername());
            c.setPath(request.getContextPath());  // 프로젝트 경로에만 쿠키 적용
            c.setMaxAge(60 * 60 * 24 * 30);       // 30일
            response.addCookie(c);
        } else {
            // 체크 안 하면 쿠키 삭제
            Cookie c = new Cookie("rememberId", "");
            c.setPath(request.getContextPath());
            c.setMaxAge(0);
            response.addCookie(c);
        }

        // --------------------------------------------------
        // 6) 로그인 유지(keepLogin)
        //  - JSESSIONID 쿠키 만료시간을 늘려서 브라우저 닫아도 유지되게 함
        //  - (주의) 서버 재시작/세션 만료 정책에 따라 끊길 수 있음
        // --------------------------------------------------
        if ("on".equals(keepLogin)) {
            Cookie jsid = new Cookie("JSESSIONID", session.getId());
            jsid.setPath(request.getContextPath());
            jsid.setMaxAge(60 * 60 * 24 * 7);    // 7일
            response.addCookie(jsid);

            // 서버 세션도 길게
            session.setMaxInactiveInterval(60 * 60 * 24 * 7);
        }

        // 7) 성공 -> 할 일 목록(서블릿)으로 이동
        response.sendRedirect(request.getContextPath() + "/todo/list");
    }
}
