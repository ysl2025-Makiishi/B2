// SubjectResult.js - 修正版（グラフ表示問題を解決）

// グローバル変数の初期化
window.examChartInstance = null;

// ページ読み込み時にコンテキストデータを取得
document.addEventListener('DOMContentLoaded', function() {
    const contextData = document.getElementById('contextData');
    if (contextData) {
        window.subjectResultConfig = {
            studentId: contextData.getAttribute('data-student-id'),
            subjectId: contextData.getAttribute('data-subject-id'),
            servletUrl: contextData.getAttribute('data-servlet-url')
        };
    }
});

/**
 * 理解度を更新する
 */
function updateUnderstanding() {
    const understanding = document.getElementById('understanding').value;
    
    const form = document.createElement('form');
    form.method = 'post';
    form.action = window.subjectResultConfig.servletUrl;
    
    const fields = {
        action: 'updateUnderstanding',
        understanding: understanding,
        studentId: window.subjectResultConfig.studentId,
        subjectId: window.subjectResultConfig.subjectId
    };
    
    for (const [key, value] of Object.entries(fields)) {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = key;
        input.value = value;
        form.appendChild(input);
    }
    
    document.body.appendChild(form);
    form.submit();
}

/**
 * 模試結果を更新する
 * @param {number} examId - 模試ID
 */
function updateExam(examId) {
    const examName = document.getElementById('examName_' + examId).value;
    const examDate = document.getElementById('examDate_' + examId).value;
    const score = document.getElementById('score_' + examId).value;
    const deviationValue = document.getElementById('dev_' + examId).value;
    const averageScore = document.getElementById('avg_' + examId).value;
    
    // バリデーション
    if (!validateExamData(examName, examDate, score, deviationValue, averageScore)) {
        return;
    }
    
    const form = document.createElement('form');
    form.method = 'post';
    form.action = window.subjectResultConfig.servletUrl;
    
    const fields = {
        action: 'updateExam',
        examId: examId,
        examName: examName,
        examDate: examDate,
        score: score,
        deviationValue: deviationValue,
        averageScore: averageScore,
        studentId: window.subjectResultConfig.studentId,
        subjectId: window.subjectResultConfig.subjectId
    };
    
    for (const [key, value] of Object.entries(fields)) {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = key;
        input.value = value;
        form.appendChild(input);
    }
    
    document.body.appendChild(form);
    form.submit();
}

/**
 * 模試結果を削除する
 * @param {number} examId - 模試ID
 */
function deleteExam(examId) {
    if (confirm('この模試結果を削除しますか？')) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = window.subjectResultConfig.servletUrl;
        
        const fields = {
            action: 'deleteExam',
            examId: examId,
            studentId: window.subjectResultConfig.studentId,
            subjectId: window.subjectResultConfig.subjectId
        };
        
        for (const [key, value] of Object.entries(fields)) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = value;
            form.appendChild(input);
        }
        
        document.body.appendChild(form);
        form.submit();
    }
}

/**
 * 模試データのバリデーション
 * @param {string} examName - 模試名
 * @param {string} examDate - 実施日
 * @param {string} score - 点数
 * @param {string} deviationValue - 偏差値
 * @param {string} averageScore - 平均点
 * @returns {boolean} バリデーション結果
 */
function validateExamData(examName, examDate, score, deviationValue, averageScore) {
    if (!examName.trim()) {
        alert('模試名を入力してください。');
        return false;
    }
    
    if (!examDate) {
        alert('実施日を入力してください。');
        return false;
    }
    
    const scoreNum = parseFloat(score);
    if (isNaN(scoreNum) || scoreNum < 0) {
        alert('点数は0以上の数値で入力してください。');
        return false;
    }
    
    const devNum = parseFloat(deviationValue);
    if (isNaN(devNum) || devNum < 0) {
        alert('偏差値は0以上の数値で入力してください。');
        return false;
    }
    
    const avgNum = parseFloat(averageScore);
    if (isNaN(avgNum) || avgNum < 0) {
        alert('平均点は0以上の数値で入力してください。');
        return false;
    }
    
    return true;
}

// ========== 模試グラフ機能（修正版） ==========

class ExamChart {
    constructor() {
        this.chart = null;
        this.examData = [];
        this.chartContainer = null;
        this.examNameSelect = null;
        this.currentSubject = null;
    }

    // 初期化
    initialize(examData, currentSubject) {
        console.log('Initializing chart with data:', examData);
        
        this.examData = examData || [];
        this.currentSubject = currentSubject;
        this.chartContainer = document.getElementById('examChart');
        this.examNameSelect = document.getElementById('examNameSelect');

        if (!this.chartContainer) {
            console.error('Chart container (examChart) not found');
            return false;
        }

        if (!this.examNameSelect) {
            console.error('Exam name select element not found');
            return false;
        }

        // Chart.jsの確認
        if (typeof Chart === 'undefined') {
            console.error('Chart.js is not loaded');
            return false;
        }

        this.setupExamNameOptions();
        return this.initializeChart();
    }

    // 模試名の選択肢を設定
    setupExamNameOptions() {
        // 既存のオプションをクリア（初期オプション以外）
        while (this.examNameSelect.children.length > 1) {
            this.examNameSelect.removeChild(this.examNameSelect.lastChild);
        }

        // ユニークな模試名を取得
        const uniqueExamNames = [...new Set(this.examData.map(exam => exam.examName))];
        
        uniqueExamNames.forEach(examName => {
            const option = document.createElement('option');
            option.value = examName;
            option.textContent = examName;
            this.examNameSelect.appendChild(option);
        });
    }

    // 教科に応じた縦軸の最大値を取得
    getScoreAxisMax() {
        // 総合の場合は500点、それ以外は100点
        return (this.currentSubject === '総合') ? 500 : 100;
    }

    // Chart.js初期化（修正版）
    initializeChart() {
        if (!this.chartContainer) return false;

        try {
            const ctx = this.chartContainer.getContext('2d');
            const scoreMax = this.getScoreAxisMax();
            
            // 既存のチャートがあれば破棄
            if (this.chart) {
                this.chart.destroy();
            }
            
            this.chart = new Chart(ctx, {
                type: 'bar', // メインタイプを棒グラフに設定
                data: {
                    labels: [],
                    datasets: []
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    interaction: {
                        mode: 'index',
                        intersect: false,
                    },
                    scales: {
                        x: {
                            display: true,
                            title: {
                                display: true,
                                text: '実施日',
                                font: {
                                    size: 14,
                                    weight: 'bold'
                                }
                            },
                            grid: {
                                color: 'rgba(0, 0, 0, 0.1)'
                            }
                        },
                        y: {
                            type: 'linear',
                            display: true,
                            position: 'left',
                            title: {
                                display: true,
                                text: '点数・平均点',
                                font: {
                                    size: 14,
                                    weight: 'bold'
                                }
                            },
                            min: 0,
                            max: scoreMax,
                            grid: {
                                color: 'rgba(0, 0, 0, 0.1)'
                            }
                        },
                        y1: {
                            type: 'linear',
                            display: true,
                            position: 'right',
                            title: {
                                display: true,
                                text: '偏差値',
                                font: {
                                    size: 14,
                                    weight: 'bold'
                                }
                            },
                            min: 30,
                            max: 80,
                            grid: {
                                drawOnChartArea: false,
                            },
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: '模試結果推移グラフ',
                            font: {
                                size: 16,
                                weight: 'bold'
                            }
                        },
                        legend: {
                            display: true,
                            position: 'top',
                            labels: {
                                usePointStyle: true,
                                padding: 20
                            }
                        },
                        tooltip: {
                            mode: 'index',
                            intersect: false,
                            callbacks: {
                                title: function(context) {
                                    return '実施日: ' + context[0].label;
                                },
                                label: function(context) {
                                    let label = context.dataset.label || '';
                                    if (label) {
                                        label += ': ';
                                    }
                                    label += context.parsed.y;
                                    
                                    // 偏差値以外は点を追加
                                    if (context.dataset.label !== '偏差値') {
                                        label += '点';
                                    }
                                    
                                    return label;
                                }
                            }
                        }
                    }
                }
            });

            console.log('Chart created successfully');
            this.updateChart();
            return true;
            
        } catch (error) {
            console.error('Error creating chart:', error);
            return false;
        }
    }

    // チャート更新（修正版）
    updateChart() {
        if (!this.chart || !this.examNameSelect) {
            console.error('Chart or select element not available for update');
            return;
        }

        const selectedExam = this.examNameSelect.value;
        
        // データをフィルタリング
        let filteredData = this.examData;
        if (selectedExam && selectedExam !== '') {
            filteredData = this.examData.filter(exam => exam.examName === selectedExam);
        }

        console.log('Filtered data:', filteredData);

        // データが空の場合
        if (filteredData.length === 0) {
            this.chart.data.labels = [];
            this.chart.data.datasets = [];
            this.chart.update();
            console.log('No data to display');
            return;
        }

        // 日付順にソート
        filteredData.sort((a, b) => new Date(a.examDate) - new Date(b.examDate));

        // ラベル（実施日）を設定
        const labels = filteredData.map(exam => exam.examDate);

        // データセットを作成（偏差値を最初に配置して前面に表示）
        const datasets = [
            {
                label: '偏差値',
                data: filteredData.map(exam => parseFloat(exam.deviationValue)),
                borderColor: 'rgb(34, 139, 34)',
                backgroundColor: 'rgba(34, 139, 34, 0.1)',
                borderWidth: 4,
                pointRadius: 5,
                pointHoverRadius: 8,
                pointBackgroundColor: 'rgb(34, 139, 34)',
                pointBorderColor: 'rgb(255, 255, 255)',
                pointBorderWidth: 2,
                yAxisID: 'y1',
                type: 'line',
                tension: 0.3,
                fill: false,
                order: 1 // 描画順序を指定（数字が小さいほど前面）
            },
            {
                label: '私の点数',
                data: filteredData.map(exam => parseFloat(exam.score)),
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgb(54, 162, 235)',
                borderWidth: 2,
                yAxisID: 'y',
                type: 'bar',
                order: 2
            },
            {
                label: '平均点',
                data: filteredData.map(exam => parseFloat(exam.averageScore)),
                backgroundColor: 'rgba(255, 99, 132, 0.6)',
                borderColor: 'rgb(255, 99, 132)',
                borderWidth: 2,
                yAxisID: 'y',
                type: 'bar',
                order: 3
            }
        ];

        // 縦軸の最大値を更新
        const scoreMax = this.getScoreAxisMax();
        this.chart.options.scales.y.max = scoreMax;

        // チャートを更新
        this.chart.data.labels = labels;
        this.chart.data.datasets = datasets;
        this.chart.update();
        
        console.log('Chart updated successfully');
    }

    // チャート破棄
    destroy() {
        if (this.chart) {
            this.chart.destroy();
            this.chart = null;
            console.log('Chart destroyed');
        }
    }

    // データ更新
    updateData(newExamData, currentSubject) {
        this.examData = newExamData || [];
        this.currentSubject = currentSubject;
        this.setupExamNameOptions();
        this.updateChart();
    }
}

// チャート初期化関数（グローバル）
function initializeExamChart(examData, currentSubject) {
    console.log('initializeExamChart called with:', examData, currentSubject);
    
    try {
        // 既存のインスタンスを破棄
        if (window.examChartInstance) {
            window.examChartInstance.destroy();
        }
        
        // 新しいインスタンスを作成
        window.examChartInstance = new ExamChart();
        const success = window.examChartInstance.initialize(examData, currentSubject);
        
        if (success) {
            console.log('Chart initialized successfully');
        } else {
            console.error('Chart initialization failed');
        }
        
        return success;
    } catch (error) {
        console.error('Error initializing chart:', error);
        return false;
    }
}

// チャート更新関数（グローバル、select要素のonchangeから呼ばれる）
function updateChart() {
    try {
        if (window.examChartInstance && window.examChartInstance.chart) {
            window.examChartInstance.updateChart();
        } else {
            console.warn('Chart instance not found for update');
        }
    } catch (error) {
        console.error('Error updating chart:', error);
    }
}

// Chart.jsが読み込まれているかチェック
function checkChartJsLoaded() {
    const loaded = typeof Chart !== 'undefined';
    console.log('Chart.js loaded:', loaded);
    return loaded;
}

// グローバルスコープで関数を確実に利用可能にする
window.initializeExamChart = initializeExamChart;
window.updateChart = updateChart;
window.ExamChart = ExamChart;
window.checkChartJsLoaded = checkChartJsLoaded;