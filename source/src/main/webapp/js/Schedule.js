// リセットボタンの処理
document.getElementById("schedule_form").addEventListener("reset", function() {
    document.getElementById("result").style.display = "none"; // 結果を非表示
    document.getElementById("register_container").style.display = "none";
    document.getElementById("error_message").textContent = "";
});

document.getElementById("schedule_form").addEventListener("submit", function(e) {
    e.preventDefault(); // フォーム送信を止める

    const date = document.querySelector('input[name="target_date"]').value;
    const frequency = document.querySelector('select[name="frequency"]').value;
    const understanding = parseInt(document.querySelector('select[name="level_of_understanding"]').value);
    const page = parseInt(document.querySelector('input[name="page"]').value);
    const errorMessage = document.getElementById("error_message");

    // 初期化
    errorMessage.textContent = "";
    document.getElementById("result").style.display = "none";
    document.getElementById("register_container").style.display = "none";

    let errors = [];

    if (!date) errors.push("目標日");
    if (!frequency) errors.push("塾に行く頻度");
    if (!understanding) errors.push("生徒の理解度");
    if (!page || page <= 0) errors.push("進めるテキストページ数");

    if (errors.length > 0) {
        if (errors.length === 1) {
            errorMessage.textContent = `${errors[0]}を選択してください`;
        } else {
            errorMessage.textContent = "選択していない項目が複数あります";
        }
        return;
    }

    // 日数の計算
    const today = new Date();
    const targetDate = new Date(date);
    const diffTime = targetDate.getTime() - today.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); // 日数（1日単位に切り上げ）

    if (diffDays <= 0) {
        errorMessage.textContent = "目標日は今日以降の日付を選んでください";
        return;
    }

    // 理解度係数の計算
    let factor = 1.0;
    if (understanding === 6) factor = 1.1;
    else if (understanding === 7) factor = 1.2;
    else if (understanding === 8) factor = 1.3;
    else if (understanding === 9) factor = 1.4;
    else if (understanding === 10) factor = 1.5;

    // ページ数計算
    const dailyPage = page / diffDays;
    const resultPage = Math.ceil(dailyPage * factor); // 小数切り上げ

    // 結果表示
    document.getElementById("result").textContent = `結果： ${resultPage}ページ進めよう！`;
    document.getElementById("result").style.display = "block";
    document.getElementById("register_container").style.display = "block";
    document.getElementById("calculated_page").value = resultPage;

});


window.addEventListener('DOMContentLoaded', function() {
    const today = new Date();

    // 日付を「YYYY-MM-DD」形式に変換
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // 月は0スタート
    const day = String(today.getDate()).padStart(2, '0');

    const todayString = `${year}-${month}-${day}`;

    // target_date に最小日付を設定（過去の日は選べなくなる）
    document.getElementById('target_date').min = todayString;
});
