// StudentList.js
console.log(students); // ← JSPで定義された配列がここで使える

document.getElementById("studentCount").textContent = students.length;
// 以下はソートや描画処理だけを書いてOK（すでにstudentsは定義済み）


  const rowsPerPage = 18;
  let currentPage = 1;
  let sortedKey = null;
  let sortDirection = 'asc';
  const studentCountElem = document.getElementById("studentCount");
  const studentGrid = document.getElementById("studentGrid");
  const paginationDiv = document.getElementById("pagination");
  const sortButtons = document.querySelectorAll(".sort-btn");
  studentCountElem.textContent = students.length;
  function getDisplayStudents() {
    let list = [...students];
    if (sortedKey) {
      list.sort((a, b) => {
        let valA = a[sortedKey];
        let valB = b[sortedKey];
        return sortDirection === 'asc'
          ? valA.localeCompare(valB, 'ja')
          : valB.localeCompare(valA, 'ja');
      });
    }
    const start = (currentPage - 1) * rowsPerPage;
    return list.slice(start, start + rowsPerPage);
  }
  function renderGrid() {
    const displayStudents = getDisplayStudents();
    studentGrid.innerHTML = "";
    for (const student of displayStudents) {
      const card = document.createElement("div");
      card.className = "student-card";
      // 性別を「M → 男」「F → 女」に変換
		let genderDisplay = student.gender === "M" ? "男性" : student.gender === "F" ? "女性" : student.gender;
		
		card.innerHTML = `
		  <div class="student-row"><span class="label">氏名</span><span class="value name">${student.name}</span></div>
		  <div class="student-row"><span class="label">学校名</span><span class="value">${student.school}</span></div>
		  <div class="student-row"><span class="label">性別</span><span class="value">${genderDisplay}</span></div>
		`;


	card.addEventListener("click", () => {
	  console.log("clicked student id:", student.id);
	  location.href = `${contextPath}/IndividualResultsServlet?studentId=${student.id}`;
	});


      
      studentGrid.appendChild(card);
    }
    renderPagination();
  }
  
  function renderPagination() {
  const totalPages = Math.ceil(students.length / rowsPerPage);
  paginationDiv.innerHTML = "";

  // ▼ 1. ラッパー div を作成して中央寄せ
  const wrapper = document.createElement("div");
  wrapper.style.display = "flex";
  wrapper.style.justifyContent = "center";
  wrapper.style.alignItems = "center";
  wrapper.style.gap = "10px";

  // ▼ 2. 「前へ」ボタン
  const prevBtn = document.createElement("button");
  prevBtn.textContent = "前へ";
  prevBtn.disabled = currentPage === 1;
  prevBtn.addEventListener("click", () => {
    if (currentPage > 1) {
      currentPage--;
      renderGrid();
    }
  });
  wrapper.appendChild(prevBtn);

  // ▼ 3. 「1 / 2」 のようなページ情報表示
  const pageInfo = document.createElement("span");
  pageInfo.className = "page-info";
  pageInfo.textContent = `${currentPage} / ${totalPages}`;
  wrapper.appendChild(pageInfo);

  // ▼ 4. 「次へ」ボタン
  const nextBtn = document.createElement("button");
  nextBtn.textContent = "次へ";
  nextBtn.disabled = currentPage === totalPages;
  nextBtn.addEventListener("click", () => {
    if (currentPage < totalPages) {
      currentPage++;
      renderGrid();
    }
  });
  wrapper.appendChild(nextBtn);

  // ▼ 5. ラッパーを pagination に追加
  paginationDiv.appendChild(wrapper);
}

  window.addEventListener('scroll', () => {
    document.getElementById("backToTop").style.display =
      window.scrollY > 100 ? "block" : "none";
  });
  renderGrid();
  
 

  
  document.getElementById("sortSelect").addEventListener("change", (e) => {
  const value = e.target.value;

  if (value === "default") {
    sortedKey = null;
  } else if (value === "reverse") {
    students.reverse();
    sortedKey = null;
  } else if (value === "name-asc") {
    sortedKey = "name";
    sortDirection = "asc";
  } else if (value === "name-desc") {
    sortedKey = "name";
    sortDirection = "desc";
  }

  currentPage = 1;
  renderGrid();
});


// 並び替えボタンのクリックでプルダウン表示／非表示
document.getElementById("toggleSortBtn").addEventListener("click", () => {
  const sortOptions = document.getElementById("sortOptions");
  sortOptions.style.display = (sortOptions.style.display === "none") ? "block" : "none";
});

