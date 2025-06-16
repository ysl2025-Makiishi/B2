<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン | 名刺管理</title>

    <!-- 外部 CSS を読み込み -->
    <link rel="stylesheet" href="css/K-style.css">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="wrapper">
    <!-- ===== ヘッダー ===== -->
    <header class="header">
        <h1 id="logo">
            <a href="login.html">
                <img src="img/K-Manage_logo.png" alt="名刺管理">
            </a>
        </h1>
    </header>

    <!-- ===== ログインフォーム ===== -->
    <h2 class="page-title">ログイン</h2>
    <hr>

    <form id="login_form" method="POST" action="/webapp/LoginServlet">
        <table>
            <tr>
                <td>
                    <label for="user_id" class="form-label">
                        ユーザーID（英数字 5〜20 文字）<br>
                        <input type="text" name="id" id="user_id" autocomplete="username">
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="user_pw" class="form-label">
                        パスワード（大文字・小文字・数字を各 1 文字以上含む 8〜20 文字）<br>
                        <input type="password" name="pw" id="user_pw" autocomplete="current-password">
                        <button type="button" id="toggle_pw" class="toggle-btn" title="パスワード表示切替">👁</button>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="btn-row">
                    <input type="submit" value="ログイン" class="btn-primary">
                    <input type="reset"  value="リセット" class="btn-secondary"><br>
                    <span id="error_message" class="error-message"></span>
                </td>
            </tr>
        </table>
    </form>

    <!-- ===== フッター ===== -->
    <footer id="footer">
        <p>&copy; Shoei Fujii, Bit Corporation. All rights reserved.</p>
    </footer>
</div>

<!-- 外部 JavaScript を読み込み -->
<script src="js/login.js"></script>
</body>
</html>
