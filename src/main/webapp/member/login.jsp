<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.http.Cookie" %>

<%
/*
 * login.jsp
 * ---------------------------------------------------------
 * 기능
 *  1) 로그인 폼
 *  2) 아이디 저장(rememberId 쿠키를 읽어서 자동 입력)
 *  3) 로그인 유지 체크박스(keepLogin) - LoginServlet에서 처리
 *
 * 이동
 *  - 로그인 성공: /todo/list (LoginServlet에서 redirect)
 *  - 회원가입: /member/join.jsp
 */

// rememberId 쿠키 읽기
String savedId = "";
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (Cookie c : cookies) {
        if ("rememberId".equals(c.getName())) {
            savedId = c.getValue();
            break;
        }
    }
}

// 에러 메시지
String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>LOGIN</title>
<style>
body { font-family: Arial; background:#f5f5f5; }
.box { width:380px; margin:120px auto; background:#fff; padding:25px; border-radius:10px; }
input { width:100%; padding:10px; margin:8px 0; }
button { width:100%; padding:10px; background:#4caf50; border:none; color:#fff; border-radius:6px; }
.small { font-size:13px; }
.err { color:red; font-size:13px; }
.link { margin-top:12px; text-align:center; }
a { text-decoration:none; }
</style>
</head>
<body>

<div class="box">
    <h2>🔐 로그인</h2>

    <% if (error != null) { %>
        <div class="err"><%= error %></div>
    <% } %>

    <!-- 로그인 요청은 /login (web.xml 매핑) -->
    <form action="<%=request.getContextPath()%>/login" method="post">
        <input type="text" name="username" placeholder="아이디" value="<%=savedId%>" required>
        <input type="password" name="password" placeholder="비밀번호" required>

        <div class="small">
            <!-- 아이디 저장 -->
            <label>
                <input type="checkbox" name="rememberId" <%= !"".equals(savedId) ? "checked" : "" %>>
                아이디 저장
            </label>
            <br>
            <!-- 로그인 유지 -->
            <label>
                <input type="checkbox" name="keepLogin">
                로그인 유지(7일)
            </label>
        </div>

        <button type="submit">로그인</button>
    </form>

    <div class="link">
        <a href="<%=request.getContextPath()%>/member/join.jsp">회원가입</a>
    </div>
</div>
<%
    /*
     * login.jsp 메시지 처리 영역
     * ----------------------------------------
     * msg 파라미터 값에 따라 알림 메시지를 출력한다.
     *
     * joinSuccess : 회원가입 완료
     * logout      : 로그아웃 완료
     * deleted     : 회원 탈퇴 완료
     */

    String msg = request.getParameter("msg");

    if ("joinSuccess".equals(msg)) {
%>
        <script>
            alert("회원가입이 완료되었습니다. 로그인해주세요!");
        </script>
<%
    } else if ("logout".equals(msg)) {
%>
        <script>
            alert("로그아웃이 되었습니다.");
        </script>
<%
    } else if ("deleted".equals(msg)) {
%>
        <script>
            alert("회원 탈퇴가 완료되었습니다.");
        </script>
<%
    }
%>


</body>
</html>
