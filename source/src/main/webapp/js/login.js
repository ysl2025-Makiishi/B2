/* login.js */
document.addEventListener('DOMContentLoaded', () => {
  // 要素取得
  const formObj         = document.getElementById('login_form');
  const errorMessageObj = document.getElementById('error_message');
  const pwInput         = document.getElementById('user_pw');
  const togglePwBtn     = document.getElementById('toggle_pw');

  // フォーム送信時バリデーション
  formObj.addEventListener('submit', (event) => {
    const userId = document.getElementById('user_id').value.trim();
    const userPw = pwInput.value.trim();

    // ①空欄チェック
    if (!userId || !userPw) {
      showError('※IDとパスワードを入力してください！', event);
      return;
    }

    // ②ユーザー ID 長さチェック
    if (userId.length < 5 || userId.length > 20) {
      showError('※ユーザーIDは5〜20文字で入力してください！', event);
      return;
    }

    // ③パスワード条件チェック
    const pwLengthOk = userPw.length >= 8 && userPw.length <= 20;
    const hasUpper   = /[A-Z]/.test(userPw);
    const hasLower   = /[a-z]/.test(userPw);
    const hasNumber  = /[0-9]/.test(userPw);

    if (!(pwLengthOk && hasUpper && hasLower && hasNumber)) {
      showError('※パスワードは8〜20文字で、大文字・小文字・数字をすべて含めてください！', event);
      return;
    }

    // 通過したらエラー文言クリア
    errorMessageObj.textContent = '';
  });

  // リセットボタン押下時
  formObj.addEventListener('reset', () => {
    errorMessageObj.textContent = '';
  });

  // パスワード表示／非表示トグル
  togglePwBtn.addEventListener('click', () => {
    const isHidden = pwInput.type === 'password';
    pwInput.type   = isHidden ? 'text' : 'password';
    togglePwBtn.textContent = isHidden ? '表示中' : '👁';
  });

  // エラーハンドリング用共通関数
  function showError(message, event) {
    errorMessageObj.textContent = message;
    event.preventDefault();
  }
});
