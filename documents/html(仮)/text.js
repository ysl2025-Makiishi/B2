function search() {
  const subject = document.getElementById('subject').value;
  const personality = document.getElementById('personality').value;
  const result = document.getElementById('result');
  const registerForm = document.getElementById('registerForm');

  result.style.display = 'block';

  if (!subject && !personality) {
    result.textContent = '結果：情報を入力してください。';
    registerForm.style.display = 'none';
    return;
  }

  if (!subject) {
    result.textContent = '結果：教科を入力してください。';
    registerForm.style.display = 'none';
    return;
  }

  if (!personality) {
    result.textContent = '結果：性格を入力してください。';
    registerForm.style.display = 'none';
    return;
  }

  // 結果を表示
  result.textContent = `結果：${subject} をやろう！（${personality}向け）`;

  // 登録用フォームに値をセットして表示
  document.getElementById('registerSubject').value = subject;
  document.getElementById('registerPersonality').value = personality;
  registerForm.style.display = 'block';
}

// リセット時に非表示に戻す
document.getElementById('textForm').addEventListener('reset', function () {
  const result = document.getElementById('result');
  const registerForm = document.getElementById('registerForm');

  result.textContent = '';
  result.style.display = 'none';
  registerForm.style.display = 'none';
});
