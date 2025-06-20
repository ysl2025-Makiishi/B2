document.addEventListener('DOMContentLoaded', function () {
  const huriganaInput = document.getElementById('huriganaInput');
  const huriganaError = document.getElementById('huriganaError');

  if (huriganaInput) {
    huriganaInput.addEventListener('input', () => {
      const original = huriganaInput.value;
      const filtered = original.replace(/[^\u3041-\u3096ー\s]/g, '');

      if (original !== filtered) {
        huriganaError.textContent = 'ひらがなで入力してください';
      } else {
        huriganaError.textContent = '';
      }

      huriganaInput.value = filtered;
    });
  }
});
