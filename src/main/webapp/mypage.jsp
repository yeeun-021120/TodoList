<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.MemberDto" %>

<%
MemberDto user = (MemberDto) session.getAttribute("loginUser");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/member/login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<style>
body { font-family: Arial; background:#f5f5f5; }
.box {
    width: 400px;
    margin: 60px auto;
    background: #fff;
    padding: 30px;
    border-radius: 10px;
}
a {
    display:block;
    margin:12px 0;
    padding:12px;
    text-align:center;
    background:#4caf50;
    color:white;
    text-decoration:none;
    border-radius:6px;
}
.danger { background:#e53935; }
</style>
</head>
<body>

<div class="box">
    <h2>👤 마이페이지</h2>
    <p>아이디: <b><%= user.getUsername() %></b></p>

    <a href="<%=request.getContextPath()%>/member/edit">회원정보 수정</a>
    <a href="<%=request.getContextPath()%>/category/manage">
    📁 카테고리 관리
</a>
    
    <a href="<%=request.getContextPath()%>/todo/list">할 일 목록</a>

    <hr>

    <form action="<%=request.getContextPath()%>/member/delete"
      method="post"
      onsubmit="return confirm('정말 탈퇴하시겠습니까?\n모든 데이터가 삭제되며 복구할 수 없습니다.');">

    <button type="submit" class="danger"
        style="
            width:100%;
            margin:12px 0;
            padding:12px;
            border:none;
            border-radius:6px;
            background:#e53935;
            color:white;
            cursor:pointer;
        ">
        회원 탈퇴
    </button>
</form>

</div>

</body>
</html>
