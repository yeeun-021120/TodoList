package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDao;

/*
 * JoinCheckServlet
 * ------------------------------------------------------
 * 회원가입 화면(join.jsp)에서 "아이디 중복확인" 버튼 클릭 시 호출되는 서블릿(AJAX)
 *
 * 요청:  GET  /join/check?username=xxx
 * 응답:
 *   - "OK"    : 사용 가능
 *   - "DUP"   : 이미 존재(중복)
 *   - "EMPTY" : username이 비어있음
 */
public class JoinCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) 파라미터 받기
        String username = request.getParameter("username");

        // 2) 응답 타입 설정 (AJAX 텍스트 응답)
        response.setContentType("text/plain; charset=UTF-8");

        // 3) 빈 값이면 EMPTY 반환
        if (username == null || username.trim().equals("")) {
            response.getWriter().write("EMPTY");
            return;
        }

        username = username.trim();

        // 4) DB 중복 체크
        MemberDao dao = new MemberDao();
        boolean exists = dao.existsUsername(username); // ✅ MemberDao에 존재해야 함(위에서 추가함)

        // 5) 결과 반환
        if (exists) response.getWriter().write("DUP");
        else response.getWriter().write("OK");
    }
}
