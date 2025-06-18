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
