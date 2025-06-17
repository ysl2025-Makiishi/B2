<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>ホーム | K-manage</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/css/K-style.css' />">
</head>

<body id="home">
    <div class="wrapper">
        <!-- ヘッダー（ここから） -->
        <div style="text-align: center;">
    <h1 id="logo" >
      
        <a href="K-Manage_home.html">
          <img src="<c:url value='/img/K-Manage_logo.png' />" alt="K-Manage">
        </a>
      

    </h1>
    </div>
    </div>
        <ul id="nav">
            <li><a href="home.html">ホーム</a></li>
            <!-- <li><a href="home.html"><img src="images/a.jpg" width="100" height="100" alt="K-Manage"></a></li> -->
            <li><a href="">生徒一覧</a></li>
            <li><a href="">登録</a></li>
            <li><a href="">検索</a></li>
            <!--<li><a href="">性格診断</a></li>
            <li><a href="">テキスト選出</a></li>
            <li><a href="">スケジュール作成</a></li>-->
            <li><a href="">宿題提案</a></li>
            <li><a href="">ログアウト</a></li>
        </ul>
        <!-- ヘッダー（ここまで） -->
        <!-- メイン（ここから） -->
        <h2>性格診断</h2>
        <h2>性格の説明</h2>
        <p>
            ・開放性：新しいことに対する柔軟さや好奇心が強い。想像力が豊か。<br>
            ・勤勉性：計画性や責任感が強く、自己管理能力が高い。<br>
            ・神経症傾向：ストレスや不安に対して敏感。<br>
            ・外向性：能動的で、変化を好む。<br>
        </p>
        
        <!-- アンケート　ラジオボタンで選択 -->
        <h2>アンケート</h2>
        <p>当てはまると思うものを選択してください。（当てはまらない～当てはまる）</p>
        <form id="quiz-form">
            1. 新しいことを学ぶのが好きだ
            <label><input type="radio" name="q1" value="1" required> 1</label>
            <label><input type="radio" name="q1" value="2" required> 2</label>
            <label><input type="radio" name="q1" value="3" required> 3</label>
            <label><input type="radio" name="q1" value="4" required> 4</label>
            <label><input type="radio" name="q1" value="5" required> 5</label>
            <br>

            2. 細かいスケジュールを立てるのが得意だ
            <label><input type="radio" name="q2" value="1" required> 1</label>
            <label><input type="radio" name="q2" value="2" required> 2</label>
            <label><input type="radio" name="q2" value="3" required> 3</label>
            <label><input type="radio" name="q2" value="4" required> 4</label>
            <label><input type="radio" name="q2" value="5" required> 5</label>
            <br>

            3. 心配しすぎて手が止まることがある
            <label><input type="radio" name="q3" value="1" required> 1</label>
            <label><input type="radio" name="q3" value="2" required> 2</label>
            <label><input type="radio" name="q3" value="3" required> 3</label>
            <label><input type="radio" name="q3" value="4" required> 4</label>
            <label><input type="radio" name="q3" value="5" required> 5</label>
            <br>

            4. グループで話すと元気になる
            <label><input type="radio" name="q4" value="1" required> 1</label>
            <label><input type="radio" name="q4" value="2" required> 2</label>
            <label><input type="radio" name="q4" value="3" required> 3</label>
            <label><input type="radio" name="q4" value="4" required> 4</label>
            <label><input type="radio" name="q4" value="5" required> 5</label>
            <br>

            5. じっとしているよりも、何か活動しているほうが好きだ
            <label><input type="radio" name="q5" value="1" required> 1</label>
            <label><input type="radio" name="q5" value="2" required> 2</label>
            <label><input type="radio" name="q5" value="3" required> 3</label>
            <label><input type="radio" name="q5" value="4" required> 4</label>
            <label><input type="radio" name="q5" value="5" required> 5</label>
            <br>

            6. 芸術や創作活動に魅力を感じる
            <label><input type="radio" name="q6" value="1" required> 1</label>
            <label><input type="radio" name="q6" value="2" required> 2</label>
            <label><input type="radio" name="q6" value="3" required> 3</label>
            <label><input type="radio" name="q6" value="4" required> 4</label>
            <label><input type="radio" name="q6" value="5" required> 5</label>
            <br>

            7. 一度決めた計画はきっちり守りたい
            <label><input type="radio" name="q7" value="1" required> 1</label>
            <label><input type="radio" name="q7" value="2" required> 2</label>
            <label><input type="radio" name="q7" value="3" required> 3</label>
            <label><input type="radio" name="q7" value="4" required> 4</label>
            <label><input type="radio" name="q7" value="5" required> 5</label>
            <br>

            8. 小さなミスでも落ち込んでしまう
            <label><input type="radio" name="q8" value="1" required> 1</label>
            <label><input type="radio" name="q8" value="2" required> 2</label>
            <label><input type="radio" name="q8" value="3" required> 3</label>
            <label><input type="radio" name="q8" value="4" required> 4</label>
            <label><input type="radio" name="q8" value="5" required> 5</label>
            <br><br>

            <div class="button-area">
                <button type="submit">診断する</button>
            </div>
            <br>
        </form>

            <!-- 結果を表示 -->
            <div class="result">
                <span style="font-size: 24px; font-weight: bold;">結果：</span>
                <span id="personality-result" style="font-size: 24px; font-weight: bold;"></span>
            </div> 
            <button>登録</button><br><br>
            <button>個人結果ページへ戻る</button>

            <div style="text-align: center;">

                
                
                

        </div>
        <!-- メイン（ここまで） -->
        <!-- フッター（ここから） -->
        <div id="footer">
            <p>&copy;Copyright wataamekun. All rights reserved.</p>
        </div>
        <!-- フッター（ここまで） -->
    </div>
</body>

</html>