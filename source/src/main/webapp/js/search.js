document.addEventListener('DOMContentLoaded', function () {
  const furiganaInput = document.getElementById('furiganaInput');
  const furiganaError = document.getElementById('furiganaError');
  let isComposing = false; // IME入力中フラグ

  if (furiganaInput) {
    // IME入力開始
    furiganaInput.addEventListener('compositionstart', () => {
      isComposing = true;
    });

    // IME入力終了（確定）
    furiganaInput.addEventListener('compositionend', () => {
      isComposing = false;
      filterFurigana(); // 確定後にチェック
    });

    // 通常の入力
    furiganaInput.addEventListener('input', () => {
      if (!isComposing) {
        filterFurigana();
      }
    });

    function filterFurigana() {
      const original = furiganaInput.value;
      const filtered = original.replace(/[^\u3041-\u3096ー\s]/g, '');

      if (original !== filtered) {
        furiganaError.textContent = '※ ひらがなのみ入力できます';
        furiganaInput.value = filtered;
      } else {
        furiganaError.textContent = '';
      }
    }
  }
});
