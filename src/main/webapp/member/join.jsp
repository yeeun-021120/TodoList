<%@ page contentType="text/html; charset=UTF-8" %>

<%
/*
 * join.jsp
 * ---------------------------------------------------------
 * 기능
 *  1) 회원가입 폼
 *  2) 아이디 중복확인 버튼 (AJAX -> /join/check)
 *  3) JoinServlet에서 중복이면 error 메시지로 돌아옴
 */

String error = (String) request.getAttribute("error");
%>+

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>JOIN</title>
<style>
body { font-family: Arial; background:#f5f5f5; }
.box { width:420px; margin:120px auto; background:#fff; padding:25px; border-radius:10px; }
input { width:100%; padding:10px; margin:8px 0; }
button { padding:10px; background:#4caf50; border:none; color:#fff; border-radius:6px; }
.err { color:red; font-size:13px; }
.ok { color:green; font-size:13px; }
.row { display:flex; gap:8px; align-items:center; }
.row input { flex:1; }
</style>
</head>
<body>

<div class="box">
    <h2>🧾 회원가입</h2>

    <% if (error != null) { %>
        <div class="err"><%= error %></div>
    <% } %>

    <!-- (중요) 아이디 중복확인 결과 표시 -->
    <div id="checkMsg" class="err" style="min-height:18px;"></div>

    <form action="<%=request.getContextPath()%>/join" method="post" onsubmit="return validateJoin();">
        <!-- 아이디 + 중복확인 버튼 -->
        <div class="row">
            <input type="text" id="username" name="username" placeholder="아이디" required>
            <button type="button" onclick="checkId()">중복확인</button>
        </div>

        <input type="password" id="password" name="password" placeholder="비밀번호" required>

        <button type="submit" style="width:100%;">가입하기</button>
    </form>

    <br>
    <a href="<%=request.getContextPath()%>/member/login.jsp">← 로그인으로</a>
</div>

<script>
/*
 * 중복확인 상태를 기억하는 변수
 * - 중복확인을 통과한 아이디인지 확인하기 위해 사용
 */
let checkedOk = false;
let checkedValue = "";

/*
 * 아이디 중복확인 (AJAX)
 * GET /join/check?username=xxx
 * 응답: OK / DUP
 */
function checkId() {
    const user = document.getElementById("username").value.trim();
    const msg = document.getElementById("checkMsg");

    checkedOk = false;
    checkedValue = "";

    if (!user) {
        msg.className = "err";
        msg.textContent = "아이디를 입력하세요.";
        return;
    }

    fetch("<%=request.getContextPath()%>/join/check?username=" + encodeURIComponent(user))
        .then(r => r.text())
        .then(t => {
            if (t === "OK") {
                msg.className = "ok";
                msg.textContent = "✅ 사용 가능한 아이디입니다.";
                checkedOk = true;
                checkedValue = user;
            } else {
                msg.className = "err";
                msg.textContent = "❌ 이미 사용중인 아이디입니다.";
            }
        })
        .catch(() => {
            msg.className = "err";
            msg.textContent = "서버 오류가 발생했습니다.";
        });
}

/*
 * 가입하기 버튼 눌렀을 때
 * - 중복확인 OK를 안 했으면 막기
 * - 중복확인한 아이디와 현재 입력 아이디가 다르면 막기
 */
function validateJoin() {
    const user = document.getElementById("username").value.trim();
    const msg = document.getElementById("checkMsg");

    if (!checkedOk) {
        msg.className = "err";
        msg.textContent = "중복확인을 먼저 해주세요.";
        return false;
    }

    if (user !== checkedValue) {
        msg.className = "err";
        msg.textContent = "아이디를 변경했습니다. 다시 중복확인 해주세요.";
        return false;
    }

    return true;
}
</script>

</body>
</html>
