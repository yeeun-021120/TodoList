package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.MemberDto;
import model.TodoDao;

public class TodoAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        String content = request.getParameter("content");
        String day = request.getParameter("day");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        TodoDao dao = new TodoDao();
        dao.insertTodo(user.getId(), content, day, categoryId);

        response.sendRedirect(request.getContextPath() + "/todo/list?day=" + day);
    }
}
