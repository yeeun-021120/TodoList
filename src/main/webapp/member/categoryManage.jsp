<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.CategoryDto" %>

<%
ArrayList<CategoryDto> list =
    (ArrayList<CategoryDto>) request.getAttribute("categories");

// 🔥 null 방어
if (list == null) {
    list = new ArrayList<>();
}
%>

<html>
<head>
    <title>카테고리 관리</title>
</head>
<body>

<h2>📁 카테고리 관리</h2>

<!-- ✅ 카테고리 목록 -->
<% for(CategoryDto c : list) { %>
<form action="<%=request.getContextPath()%>/category/update"
      method="post"
      style="margin-bottom:10px;">

    <input type="hidden" name="id" value="<%=c.getId()%>">

    <!-- 이름 -->
    <input type="text" name="name"
           value="<%=c.getName()%>" required>

    <!-- 색상 -->
    <input type="color" name="color"
           value="<%=c.getColor()%>">

    <button>수정</button>

    <!-- 삭제 -->
    <a href="<%=request.getContextPath()%>/category/delete?id=<%=c.getId()%>">
        삭제
    </a>
</form>
<% } %>

<hr>

<!-- ✅ 새 카테고리 추가 -->
<form action="<%=request.getContextPath()%>/category/add" method="post">
    <input type="text" name="name" placeholder="카테고리 이름" required>
    <input type="color" name="color" required>
    <button>추가</button>
</form>

<br>
<a href="<%=request.getContextPath()%>/mypage">← 마이페이지</a>

</body>
</html>
