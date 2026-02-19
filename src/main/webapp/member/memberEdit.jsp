<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.MemberDto" %>

<%
MemberDto user = (MemberDto) request.getAttribute("user");
	if(user == null){
        response.sendRedirect(request.getContextPath() + "/member/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
</head>
<body>

<h2>🔐 회원정보 수정</h2>

<p>아이디: <b><%=user.getUsername()%></b></p>

<hr>

<!-- 비밀번호 변경 -->


<form action="<%=request.getContextPath()%>/member/edit" method="post">

    <p>
        현재 비밀번호<br>
        <input type="password" name="currentPw" required>
    </p>

    <p>
        새 비밀번호<br>
        <input type="password" name="newPw" required>
    </p>

    <button>비밀번호 변경</button>
</form>

<% if(request.getAttribute("error") != null){ %>
    <p style="color:red;"><%=request.getAttribute("error")%></p>
<% } %>

<hr>

<%
    /*
     * 비밀번호 변경 메시지 처리
     * -----------------------------
     * changed : 변경 성공
     * wrongPw : 현재 비밀번호 불일치
     */
    String msg = request.getParameter("msg");

    if ("changed".equals(msg)) {
%>
    <script>
        alert("비밀번호가 변경되었습니다.");
    </script>
<%
    } else if ("wrongPw".equals(msg)) {
%>
    <script>
        alert("현재 비밀번호가 일치하지 않습니다.");
    </script>
<%
    }
%>




<a href="<%=request.getContextPath()%>/mypage">← 마이페이지</a>

</body>
</html>
