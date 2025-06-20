 document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('searchForm');
  const subject = document.getElementById('subject');
  const personality = document.getElementById('personality');
  const msg = document.getElementById('warningMsg');
  const result = document.getElementById('searchResult');

  function validateForm() {
    msg.textContent = '';
    if (!subject.value) {
      msg.textContent = '教科を選んでください。';
      return false;
    }
    if (!personality.value) {
      msg.textContent = '性格を選んでください。';
      return false;
    }
    return true;
  }

  // リセット時の処理
  form.addEventListener('reset', function () {
    setTimeout(() => {
      subject.value = '';
      personality.value = '';
      msg.textContent = '';
      result.innerHTML = '';
    }, 0);
  });

  // グローバルに公開したい場合
  window.validateForm = validateForm;
});
