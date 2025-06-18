function calculateHomework() {
    const nextDateInput = document.getElementById("nextDate").value;
    const totalPages = parseInt(document.getElementById("totalPages").value);

    if (!nextDateInput || isNaN(totalPages) || totalPages <= 0) {
        alert("すべての項目を正しく入力してください。");
        return;
    }

    const today = new Date();
    const nextDate = new Date(nextDateInput);

    const diffTime = nextDate - today;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays <= 0) {
        document.getElementById("result").textContent = "次に塾に行く日が今日または過去の日付になっています。";
        document.getElementById("registerBtn").style.display = "none";
        return;
    }

    const pagesPerDay = Math.ceil(totalPages / diffDays);
    document.getElementById("result").textContent = `1日${pagesPerDay}ページの宿題をやろう！`;

    //  「登録」ボタンを表示
    document.getElementById("registerBtn").style.display = "inline-block";
}

function resetForm() {
    document.getElementById("nextDate").value = "";
    document.getElementById("totalPages").value = "";
    document.getElementById("result").textContent = "";
    document.getElementById("registerBtn").style.display = "none"; 
}