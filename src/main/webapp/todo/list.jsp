<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.time.*, java.util.*" %>
<%@ page import="model.*" %>

<%
/*
 * ================================
 * 📌 Todo List 페이지
 * -------------------------------
 * - 월간 캘린더 출력
 * - 날짜별 할 일 조회
 * - 할 일 추가 / 완료 / 삭제
 * - 카테고리 표시
 * ================================
 */

String day = (String) request.getAttribute("day");
ArrayList<TodoDto> list =
        (ArrayList<TodoDto>) request.getAttribute("todoList");
ArrayList<CategoryDto> categories =
        (ArrayList<CategoryDto>) request.getAttribute("categoryList");
Map<String,Integer> heat =
(Map<String,Integer>)request.getAttribute("heatmap");

if (heat == null) {
heat = new HashMap<>();
}

/* day 값이 없으면 오늘 날짜로 */
if (day == null) {
    day = LocalDate.now().toString();
}

LocalDate selected = LocalDate.parse(day);
YearMonth ym = YearMonth.from(selected);
LocalDate first = ym.atDay(1);
int start = first.getDayOfWeek().getValue() % 7;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Todo List</title>

<style>
td { width:40px;height:40px;text-align:center;border:1px solid #ccc;}
.heat0{background:#eee;}
.heat1{background:#c6e48b;}
.heat2{background:#7bc96f;}
.heat3{background:#239a3b;color:white;}
.heat4{background:#196127;color:white;}
</style>
</head>

<body>

<!-- ===================== -->
<!-- 상단 메뉴 -->
<!-- ===================== -->
<div style="text-align:right; margin-bottom:15px;">
    <a href="<%=request.getContextPath()%>/mypage">👤 마이페이지</a>
    |
    <a href="<%=request.getContextPath()%>/logout">로그아웃</a>
</div>

<!-- ===================== -->
<!-- 달력 -->
<!-- ===================== -->
<h2>
<a href="?day=<%=ym.minusMonths(1).atDay(1)%>">◀</a>
<%=ym.getYear()%> - <%=ym.getMonthValue()%>
<a href="?day=<%=ym.plusMonths(1).atDay(1)%>">▶</a>
</h2>

<table>
<tr>
    <th>Sun</th><th>Mon</th><th>Tue</th>
    <th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th>
</tr>
<tr>

<% for(int i=0;i<start;i++){ %>
    <td></td>
<% } %>

<%
for(int d=1; d<=ym.lengthOfMonth(); d++){
    String ds = String.format("%s-%02d", ym, d);
    int cnt = heat.getOrDefault(ds, 0);
    String cls = "heat" + Math.min(cnt, 4);
%>
    <td class="<%=cls%>">
        <a href="?day=<%=ds%>"><%=d%></a>
    </td>
<%
    if((start + d) % 7 == 0){
%>
</tr><tr>
<%
    }
}
%>
</tr>
</table>

<hr>

<!-- ===================== -->
<!-- 할 일 추가 -->
<!-- ===================== -->
<h3>선택 날짜: <%=day%></h3>

<form action="<%=request.getContextPath()%>/todo/add" method="post">
    <input type="hidden" name="day" value="<%=day%>">

    <input type="text" name="content" placeholder="할 일" required>

    <select name="categoryId" required>
        <option value="">카테고리 선택</option>
        <% for(CategoryDto c : categories){ %>
            <option value="<%=c.getId()%>">
                <%=c.getName()%>
            </option>
        <% } %>
    </select>

    <button>추가</button>
</form>

<hr>

<!-- ===================== -->
<!-- 할 일 목록 -->
<!-- ===================== -->
<% for(TodoDto t : list){ %>

<form action="<%=request.getContextPath()%>/todo/update"
      method="post"
      style="display:inline;">

    <input type="hidden" name="todoId" value="<%=t.getId()%>">
    <input type="hidden" name="day" value="<%=day%>">

    <input type="checkbox"
           name="status"
           value="1"
           onchange="this.form.submit()"
           <%=t.getStatus()==1 ? "checked" : ""%>>

    <span style="color:<%=t.getCategoryColor()%>">●</span>
    <%=t.getContent()%>
    (<%=t.getCategoryName()%>)
</form>

<form action="<%=request.getContextPath()%>/todo/delete"
      method="post"
      style="display:inline;">
    <input type="hidden" name="todoId" value="<%=t.getId()%>">
    <input type="hidden" name="day" value="<%=day%>">
    <button>🗑</button>
</form>

<br>

<% } %>

</body>
</html>
