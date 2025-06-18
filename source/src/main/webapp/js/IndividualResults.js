'use strict';

document.addEventListener('DOMContentLoaded', () => {
  const weights = {
    gpa_jp: 1, gpa_ss: 1, gpa_ma: 1, gpa_sc: 1, gpa_en: 1,
    gpa_mu: 2, gpa_ar: 2, gpa_pe: 2, gpa_te: 2
  };

  const inputs = Object.keys(weights).map(id => document.getElementById(id));
  const naishinInput = document.getElementById('naishin');

  function calculateNaishin() {
    let total = 0;
    for (const id of Object.keys(weights)) {
      const val = parseInt(document.getElementById(id).value, 10);
      if (!isNaN(val) && val >= 1 && val <= 5) {
        total += val * weights[id];
      }
    }
    naishinInput.value = total;
  }

  inputs.forEach(input => {
    input.addEventListener('input', calculateNaishin);
  });
});

// ========== ふりがな：ひらがなのみ許可 ==========
document.addEventListener('DOMContentLoaded', () => {
  const kanaInput = document.querySelector('.kana-only');
  if (kanaInput) {
    kanaInput.addEventListener('input', () => {
      kanaInput.value = kanaInput.value.replace(/[^\u3041-\u3096ー\s]/g, '');
    });
  }
});

const examRegisterBtn = document.getElementById('examRegisterBtn');
if (examRegisterBtn) {
  examRegisterBtn.addEventListener('click', (e) => {
    const rows = document.querySelectorAll('tbody tr');
    let isValid = true;

    rows.forEach(row => {
      const inputs = row.querySelectorAll('input, select');
      inputs.forEach(input => {
        const value = input.value.trim();
        if (!value) {
          isValid = false;
        }
      });
    });

    if (!isValid) {
      alert('※模試結果のすべての項目（模試名、日付、教科、点数、偏差値、平均）を入力してください。');
      e.preventDefault(); // 登録キャンセル
    }
  });
}

// すべての編集ボタンに警告を出す
const editButtons = document.querySelectorAll('.edit-btn');
editButtons.forEach(btn => {
  btn.addEventListener('click', () => {
    alert('※この項目を編集できます。変更後は忘れずに登録してください。');
  });
});

