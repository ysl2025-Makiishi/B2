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

function updateUnderstanding() {
            const understanding = document.getElementById('understanding').value;
            
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '<c:url value="/SubjectResultServlet" />';
            
            const fields = {
                action: 'updateUnderstanding',
                understanding: understanding,
                studentId: '${studentId}',
                subjectId: '${subjectId}'
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
        
        function updateExam(examId) {
            const examName = document.getElementById('examName_' + examId).value;
            const examDate = document.getElementById('examDate_' + examId).value;
            const score = document.getElementById('score_' + examId).value;
            const deviationValue = document.getElementById('dev_' + examId).value;
            const averageScore = document.getElementById('avg_' + examId).value;
            
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '<c:url value="/SubjectResultServlet" />';
            
            const fields = {
                action: 'updateExam',
                examId: examId,
                examName: examName,
                examDate: examDate,
                score: score,
                deviationValue: deviationValue,
                averageScore: averageScore,
                studentId: '${studentId}',
                subjectId: '${subjectId}'
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
        
        function deleteExam(examId) {
            if (confirm('この模試結果を削除しますか？')) {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = '<c:url value="/SubjectResultServlet" />';
                
                const fields = {
                    action: 'deleteExam',
                    examId: examId,
                    studentId: '${studentId}',
                    subjectId: '${subjectId}'
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
        
        // SubjectResult.js - JSPから分離されたJavaScript

// ページ読み込み時にコンテキストデータを取得
document.addEventListener('DOMContentLoaded', function() {
    const contextData = document.getElementById('contextData');
    window.subjectResultConfig = {
        studentId: contextData.getAttribute('data-student-id'),
        subjectId: contextData.getAttribute('data-subject-id'),
        servletUrl: contextData.getAttribute('data-servlet-url')
    };
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
    if (isNaN(scoreNum) || scoreNum < 0 || scoreNum > 100) {
        alert('点数は0-100の範囲で入力してください。');
        return false;
    }
    
    const devNum = parseFloat(deviationValue);
    if (isNaN(devNum) || devNum < 0) {
        alert('偏差値は0以上の数値で入力してください。');
        return false;
    }
    
    const avgNum = parseFloat(averageScore);
    if (isNaN(avgNum) || avgNum < 0 || avgNum > 100) {
        alert('平均点は0-100の範囲で入力してください。');
        return false;
    }
    
    return true;
}

/**
 * フォーム送信用のヘルパー関数
 * @param {Object} fields - 送信するフィールドのオブジェクト
 */
function submitForm(fields) {
    const form = document.createElement('form');
    form.method = 'post';
    form.action = window.subjectResultConfig.servletUrl;
    
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