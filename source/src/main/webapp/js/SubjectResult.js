document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.edit-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const section = btn.closest('section');
      const inputs = section.querySelectorAll('input, textarea');
      inputs.forEach(input => {
        input.removeAttribute('readonly');
      });

      // 保存ボタンがなければ追加
      const btnRow = btn.closest('.section-btn-row');
      if (!btnRow.querySelector('.save-btn')) {
        const saveBtn = document.createElement('button');
        saveBtn.type = 'submit';
        saveBtn.textContent = '保存';
        saveBtn.classList.add('save-btn');
        btnRow.appendChild(saveBtn);
      }
    });
  });
});
