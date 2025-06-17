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
    const understanding = document.querySelector('select[name="level_of_understanding"]').value;
    const page = document.querySelector('input[name="page"]').value;
    const errorMessage = document.getElementById("error_message");

    // 初期化
    errorMessage.textContent = "";
    document.getElementById("result").style.display = "none";
    document.getElementById("register_container").style.display = "none";

    let errors = [];

    if (!date) errors.push("目標日");
    if (!frequency) errors.push("塾に行く頻度");
    if (!understanding) errors.push("生徒の理解度");
    if (!page || Number(page) <= 0) errors.push("進めるテキストページ数");

    if (errors.length > 0) {
        if (errors.length === 1) {
            errorMessage.textContent = `${errors[0]}を選択してください`;
        } else {
            errorMessage.textContent = "選択していない項目が複数あります";
        }
        return; // ここが重要！ エラーがあるときは下に進まない
    }

    // 成功時のみ表示
    document.getElementById("result").style.display = "block";
    document.getElementById("register_container").style.display = "block";
});

