package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.CategoryDao;
import model.CategoryDto;
import model.MemberDto;
import model.MyPageDao;
import model.MyPageDto;

/*
 * MyPageServlet
 * ------------------------------------------
 * 마이페이지를 보여주는 서블릿
 *
 * 하는 일:
 *  1) 로그인 확인
 *  2) 기존 마이페이지 통계 가져오기 (총 할일, 완료수)
 *  3) 카테고리 목록도 함께 가져오기
 *  4) mypage.jsp 로 전달
 */
public class MyPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 로그인 확인
        HttpSession session = request.getSession();
        MemberDto user = (MemberDto) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        int memberId = user.getId();

        // 2. 기존 마이페이지 통계
        MyPageDao myDao = new MyPageDao();
        MyPageDto mypage = myDao.getMyPageInfo(memberId);

        // 3. 카테고리 목록
        CategoryDao catDao = new CategoryDao();
        ArrayList<CategoryDto> categoryList = catDao.getListByMember(memberId);

        // 4. JSP로 전달
        request.setAttribute("mypage", mypage);
        request.setAttribute("categoryList", categoryList);

        request.getRequestDispatcher("/mypage.jsp").forward(request, response);
    }
}
