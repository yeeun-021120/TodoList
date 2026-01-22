package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDto;
import model.TodoDao;

/*
 * TodoUpdateServlet
 * -------------------------------------------------
 * ✔ 할 일 체크 / 체크 해제 처리
 * ✔ 체크박스 클릭 시 자동으로 호출됨
 * ✔ 완료 여부(status)를 DB에 반영
 *
 * 사용 위치:
 *  - list.jsp 에서 체크박스 클릭 시
 *
 * 처리 흐름:
 *  1. 로그인 확인
 *  2. todoId / status 값 받기
 *  3. DB 업데이트
 *  4. 다시 선택한 날짜로 이동
 */
public class TodoUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ======================================
        // 1. 로그인 체크
        // ======================================
        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            // 로그인 안 되어 있으면 로그인 페이지로
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        // ======================================
        // 2. 파라미터 받기
        // ======================================
        int todoId = Integer.parseInt(request.getParameter("todoId"));

        // 체크되면 status=1, 체크 해제되면 null → 0
        int status = 0;
        if (request.getParameter("status") != null) {
            status = 1;
        }

        // 날짜 유지용 (다시 그 날짜로 돌아가기 위해)
        String day = request.getParameter("day");

        // ======================================
        // 3. DB 업데이트
        // ======================================
        TodoDao dao = new TodoDao();
        dao.updateTodoStatus(todoId, status);

        // ======================================
        // 4. 다시 list 화면으로 이동
        // ======================================
        response.sendRedirect(
            request.getContextPath() + "/todo/list?day=" + day
        );
    }
}
