package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import model.CategoryDao;
import model.CategoryDto;
import model.MemberDto;
import model.TodoDao;
import model.TodoDto;

public class TodoListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ 로그인 체크
        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        // 2️⃣ 날짜 처리
        String day = request.getParameter("day");
        if (day == null || day.equals("")) {
            day = LocalDate.now().toString();
        }

        String month = day.substring(0, 7);

        // 3️⃣ DAO 준비
        TodoDao todoDao = new TodoDao();
        CategoryDao categoryDao = new CategoryDao();

        // 4️⃣ 데이터 조회
        ArrayList<TodoDto> todoList =
                todoDao.getTodoListByDay(user.getId(), day);

        Map<String, Integer> heatmap =
                todoDao.getMonthlyHeatmap(user.getId(), month);

        ArrayList<CategoryDto> categoryList =
                categoryDao.getListByMember(user.getId());

        // 5️⃣ JSP로 전달
        request.setAttribute("day", day);
        request.setAttribute("todoList", todoList);
        request.setAttribute("heatmap", heatmap);
        request.setAttribute("categoryList", categoryList);

        request.getRequestDispatcher("/todo/list.jsp")
               .forward(request, response);
    }
}
