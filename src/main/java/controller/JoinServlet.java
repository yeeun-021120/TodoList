package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDao;

/*
 * JoinServlet
 * ------------------------------------------------------
 * join.jsp에서 "가입하기" 제출 시 실행되는 서블릿
 *
 * 흐름:
 *  1) username/password 읽기
 *  2) 빈 값 검사
 *  3) 아이디 중복 검사
 *  4) 가능하면 insert 후 login.jsp로 이동
 */
public class JoinServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 한글 깨짐 방지
        request.setCharacterEncoding("UTF-8");

        // 1) 값 받기
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2) 기본 유효성 검사
        if (username == null || username.trim().equals("") ||
            password == null || password.trim().equals("")) {

            request.setAttribute("error", "아이디/비밀번호를 입력하세요.");
            request.getRequestDispatcher("/member/join.jsp").forward(request, response);
            return;
        }

        username = username.trim();
        password = password.trim();

        // 3) 중복 체크
        MemberDao dao = new MemberDao();
        if (dao.existsUsername(username)) { // ✅ MemberDao에 존재해야 함(위에서 추가함)
            request.setAttribute("error", "이미 사용중인 아이디입니다.");
            request.getRequestDispatcher("/member/join.jsp").forward(request, response);
            return;
        }

        // 4) 회원가입 insert
        dao.insertMember(username, password);

     // 회원가입 성공 시 로그인 페이지로 이동하면서
     // msg=joinSuccess 상태값을 전달한다
     response.sendRedirect(
         request.getContextPath() + "/member/login.jsp?msg=joinSuccess"
     );

    }
}
