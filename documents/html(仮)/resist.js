document.addEventListener("DOMContentLoaded", function () {
  const birthdayInput = document.getElementById("birthday");
  if (birthdayInput) {
    const today = new Date().toISOString().split('T')[0];
    birthdayInput.setAttribute("max", today);
  } else {
    console.warn("birthday input not found");
  }
});
// ふりがながひらがなか確認
function isHiragana(text) {
  return /^[ぁ-んー　\s]+$/.test(text);
}
  document.addEventListener("DOMContentLoaded", function () {
    const subjectSelects = document.querySelectorAll("select[name^='subject']");

    function updateOptions() {
      const selectedSubjects = new Set();

      subjectSelects.forEach(select => {
        if (select.value) {
          selectedSubjects.add(select.value);
        }
      });

      subjectSelects.forEach(select => {
        const currentValue = select.value;
        Array.from(select.options).forEach(option => {
          if (option.value === "" || option.value === currentValue) {
            option.disabled = false;
          } else {
            option.disabled = selectedSubjects.has(option.value);
          }
        });
      });
    }

    subjectSelects.forEach(select => {
      select.addEventListener("change", updateOptions);
    });

    updateOptions(); // 初期化
  });


