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
      //console.log("📤 フォーム送信されました");
    });
  }
});


/**
 * レーダーチャート機能
 * 最新の模試結果をレーダーチャートで表示
 */

let radarChart = null;

/**
 * レーダーチャートを初期化
 * @param {Object} examData - 模試データ
 */
function initializeRadarChart(examData) {
    // Chart.jsの読み込み確認
    if (typeof Chart === 'undefined') {
        //console.error('Chart.js is not loaded');
        showChartError('チャートライブラリの読み込みに失敗しました。');
        return;
    }

    // データが存在するかチェック
    if (!examData || !examData.subjects || examData.subjects.length === 0) {
        showNoDataMessage();
        return;
    }

    try {
        createRadarChart(examData);
        updateChartInfo(examData);
        updateStatistics(examData);
    } catch (error) {
        //console.error('Chart initialization failed:', error);
        showChartError('チャートの初期化に失敗しました。');
    }
}

/**
 * レーダーチャートを作成
 * @param {Object} examData - 模試データ
 */
function createRadarChart(examData) {
    const ctx = document.getElementById('radarChart');
    if (!ctx) {
        //console.error('Canvas element not found');
        return;
    }

    // 既存のチャートを破棄
    if (radarChart) {
        radarChart.destroy();
    }

    radarChart = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: examData.subjects,
            datasets: [{
                label: '自分の点数',
                data: examData.myScores,
                borderColor: '#007bff',
                backgroundColor: 'rgba(0, 123, 255, 0.2)',
                borderWidth: 3,
                pointBackgroundColor: '#007bff',
                pointBorderColor: '#ffffff',
                pointBorderWidth: 2,
                pointRadius: 6,
                pointHoverRadius: 8
            }, {
                label: '平均点',
                data: examData.averageScores,
                borderColor: '#dc3545',
                backgroundColor: 'rgba(220, 53, 69, 0.1)',
                borderWidth: 2,
                pointBackgroundColor: '#dc3545',
                pointBorderColor: '#ffffff',
                pointBorderWidth: 2,
                pointRadius: 4,
                pointHoverRadius: 6,
                borderDash: [5, 5]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                title: {
                    display: true,
                    text: '教科別成績比較',
                    font: {
                        size: 16,
                        weight: 'bold'
                    },
                    color: '#333',
                    padding: {
                        bottom: 20
                    }
                },
                legend: {
                    position: 'bottom',
                    labels: {
                        padding: 20,
                        font: {
                            size: 13
                        },
                        usePointStyle: true,
                        pointStyle: 'circle'
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.dataset.label + ': ' + context.parsed.r + '点';
                        }
                    },
                    backgroundColor: 'rgba(0, 0, 0, 0.8)',
                    titleColor: '#ffffff',
                    bodyColor: '#ffffff',
                    borderColor: '#007bff',
                    borderWidth: 1
                }
            },
            scales: {
                r: {
                    beginAtZero: true,
                    max: 100,
                    min: 0,
                    ticks: {
                        stepSize: 20,
                        font: {
                            size: 11
                        },
                        color: '#666',
                        backdropColor: 'rgba(255, 255, 255, 0.8)',
                        backdropPadding: 2
                    },
                    grid: {
                        color: 'rgba(0, 0, 0, 0.1)',
                        lineWidth: 1
                    },
                    angleLines: {
                        color: 'rgba(0, 0, 0, 0.1)',
                        lineWidth: 1
                    },
                    pointLabels: {
                        font: {
                            size: 13,
                            weight: 'bold'
                        },
                        color: '#333',
                        padding: 10
                    }
                }
            },
            interaction: {
                intersect: false,
                mode: 'point'
            },
            elements: {
                line: {
                    tension: 0.2
                }
            },
            animation: {
                duration: 1000,
                easing: 'easeInOutQuart'
            }
        }
    });
}

/**
 * チャート情報を更新
 * @param {Object} examData - 模試データ
 */
function updateChartInfo(examData) {
    const examNameElement = document.getElementById('chartExamName');
    const examDateElement = document.getElementById('chartExamDate');

    if (examNameElement && examData.examName) {
        examNameElement.textContent = examData.examName;
    }

    if (examDateElement && examData.examDate) {
        examDateElement.textContent = '実施日: ' + examData.examDate;
    }
}

/**
 * 統計情報を更新
 * @param {Object} examData - 模試データ
 */
function updateStatistics(examData) {
    if (!examData.myScores || !examData.averageScores) {
        return;
    }

    const myAvg = calculateAverage(examData.myScores);
    const overallAvg = calculateAverage(examData.averageScores);
    const diff = myAvg - overallAvg;

    updateElement('myAverage', myAvg.toFixed(1));
    updateElement('overallAverage', overallAvg.toFixed(1));
    
    const diffElement = document.getElementById('difference');
    if (diffElement) {
        diffElement.textContent = (diff >= 0 ? '+' : '') + diff.toFixed(1);
        diffElement.style.color = diff >= 0 ? '#28a745' : '#dc3545';
    }
}

/**
 * 平均値を計算
 * @param {Array} scores - 点数の配列
 * @returns {number} 平均値
 */
function calculateAverage(scores) {
    if (!scores || scores.length === 0) {
        return 0;
    }
    return scores.reduce((sum, score) => sum + score, 0) / scores.length;
}

/**
 * 要素のテキストを更新
 * @param {string} elementId - 要素のID
 * @param {string} text - 設定するテキスト
 */
function updateElement(elementId, text) {
    const element = document.getElementById(elementId);
    if (element) {
        element.textContent = text;
    }
}

/**
 * チャートエラーを表示
 * @param {string} message - エラーメッセージ
 */
function showChartError(message) {
    const container = document.querySelector('.chart-canvas-wrapper');
    if (container) {
        container.innerHTML = `
            <div class="chart-error">
                <h4>チャート表示エラー</h4>
                <p>${message}</p>
            </div>
        `;
    }
}

/**
 * データなしメッセージを表示
 */
function showNoDataMessage() {
    const container = document.querySelector('.chart-canvas-wrapper');
    if (container) {
        container.innerHTML = `
            <div class="no-data-message">
                <h4>表示するデータがありません</h4>
                <p>模試結果を登録すると、レーダーチャートが表示されます。</p>
            </div>
        `;
    }

    // 統計情報をクリア
    updateElement('chartExamName', '模試結果なし');
    updateElement('chartExamDate', '実施日: -');
    updateElement('myAverage', '-');
    updateElement('overallAverage', '-');
    updateElement('difference', '-');
}

/**
 * チャートデータを更新（外部から呼び出し用）
 * @param {Object} newData - 新しい模試データ
 */
function updateRadarChart(newData) {
    if (!newData || !newData.subjects || newData.subjects.length === 0) {
        showNoDataMessage();
        return;
    }

    if (radarChart) {
        // 既存チャートのデータを更新
        radarChart.data.labels = newData.subjects;
        radarChart.data.datasets[0].data = newData.myScores;
        radarChart.data.datasets[1].data = newData.averageScores;
        radarChart.update('active');
        
        updateChartInfo(newData);
        updateStatistics(newData);
    } else {
        // チャートが存在しない場合は新規作成
        initializeRadarChart(newData);
    }
}

/**
 * チャートを破棄
 */
function destroyRadarChart() {
    if (radarChart) {
        radarChart.destroy();
        radarChart = null;
    }
}

/**
 * ウィンドウリサイズ時の処理
 */
function handleResize() {
    if (radarChart) {
        radarChart.resize();
    }
}

// ウィンドウリサイズイベントのリスナー登録
window.addEventListener('resize', handleResize);

// ページアンロード時のクリーンアップ
window.addEventListener('beforeunload', function() {
    destroyRadarChart();
});