const rowsPerPage = 18;
let currentPage = 1;

function getDisplayStudents() {
  const start = (currentPage - 1) * rowsPerPage;
  return students.slice(start, start + rowsPerPage);
}

function renderGrid() {
  const displayStudents = getDisplayStudents();
  const studentGrid = document.getElementById("studentGrid");
  studentGrid.innerHTML = "";

  displayStudents.forEach(student => {
    const card = document.createElement("div");
    card.className = "student-card";
    card.innerHTML = `
      <div><strong>氏名:</strong> ${student.name}</div>
      <div><strong>学校名:</strong> ${student.school}</div>
      <div><strong>性別:</strong> ${student.gender}</div>
    `;
    studentGrid.appendChild(card);
  });

  renderPagination();
}

function renderPagination() {
  const paginationDiv = document.getElementById("pagination");
  paginationDiv.innerHTML = "";
  const totalPages = Math.ceil(students.length / rowsPerPage);

  // ページ番号ボタン生成など自由に実装してください
  for (let i = 1; i <= totalPages; i++) {
    const btn = document.createElement("button");
    btn.textContent = i;
    btn.disabled = i === currentPage;
    btn.addEventListener("click", () => {
      currentPage = i;
      renderGrid();
    });
    paginationDiv.appendChild(btn);
  }
}

// 初期表示
renderGrid();
