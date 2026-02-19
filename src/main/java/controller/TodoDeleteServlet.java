package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.MemberDto;
import model.TodoDao;

public class TodoDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        int todoId = Integer.parseInt(request.getParameter("todoId"));
        String day = request.getParameter("day");

        TodoDao dao = new TodoDao();
        dao.deleteTodo(todoId, user.getId());

        if (day == null || day.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/todo/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/todo/list?day=" + day);
        }
    }
}

