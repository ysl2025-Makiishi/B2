'use strict';

document.addEventListener('DOMContentLoaded', () => {
  // === GPA重み（主教科1倍、副教科2倍） ===
  const weights = {
    gpa_jp: 1, gpa_ss: 1, gpa_ma: 1, gpa_sc: 1, gpa_en: 1,
    gpa_mu: 2, gpa_ar: 2, gpa_pe: 2, gpa_te: 2
  };

  const naishinInput = document.getElementById('naishin');

  // === 内申点の再計算 ===
  function calculateNaishin() {
    let total = 0;
    for (const id in weights) {
      const input = document.getElementById(id);
      if (!input) continue;
      const val = parseInt(input.value.trim(), 10);
      if (!isNaN(val) && val >= 1 && val <= 5) {
        total += val * weights[id];
      }
    }
    if (naishinInput) naishinInput.value = total;
  }

  // === GPA 入力制限＋内申点再計算 ===
  Object.keys(weights).forEach(id => {
    const input = document.getElementById(id);
    if (input) {
      input.addEventListener('input', () => {
        const v = input.value.trim();
        if (!/^[1-5]$/.test(v)) {
          input.value = '';
        }
        calculateNaishin();
      });
    }
  });

  // 初回表示時の内申点自動計算
  calculateNaishin();

  // === ふりがな入力制限（ひらがなのみ）===
  const kanaInput = document.querySelector('.kana-only');
  if (kanaInput) {
    kanaInput.addEventListener('input', () => {
      kanaInput.value = kanaInput.value.replace(/[^\u3041-\u3096ー\s]/g, '');
    });
  }

  // === 模試入力バリデーション ===
  const examRegisterBtn = document.getElementById('examRegisterBtn');
  if (examRegisterBtn) {
    examRegisterBtn.addEventListener('click', (e) => {
      const rows = document.querySelectorAll('tbody tr');
      let isValid = true;
      rows.forEach(row => {
        const inputs = row.querySelectorAll('input, select');
        inputs.forEach(input => {
          if (!input.value.trim()) isValid = false;
        });
      });
      if (!isValid) {
        alert('※模試結果のすべての項目を入力してください。');
        e.preventDefault();
      }
    });
  }

  // === 編集ボタンの動作（保存ボタンを追加）===
  document.querySelectorAll('.edit-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      alert('※この項目を編集できます。変更後は忘れずに保存してください。');

      const section = btn.closest('section');
      if (!section) return;

      const inputs = section.querySelectorAll('input, select');
      inputs.forEach(input => {
        input.removeAttribute('readonly');
        input.removeAttribute('disabled');
      });

      const btnRow = btn.closest('.section-btn-row');
      if (btnRow && !btnRow.querySelector('.save-btn')) {
        const saveBtn = document.createElement('button');
        saveBtn.type = 'submit';
        saveBtn.textContent = '保存';
        saveBtn.classList.add('save-btn');
        btnRow.appendChild(saveBtn);
      }
    });
  });

  // === フォーム送信ログ（開発用）===
  const form = document.getElementById('studentForm');
  if (form) {
    form.addEventListener('submit', () => {
      console.log("📤 フォーム送信されました");
    });
  }
});
