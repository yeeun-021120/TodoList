package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.TodoDao;

public class TodoDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int todoId = Integer.parseInt(request.getParameter("todoId"));
        String day = request.getParameter("day");

        TodoDao dao = new TodoDao();
        dao.deleteTodo(todoId);

        response.sendRedirect(request.getContextPath() + "/todo/list?day=" + day);
    }
}
