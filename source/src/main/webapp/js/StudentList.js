/*const students = [
  { name: "田中 太郎", school: "東京中学校", gender: "男" },
  { name: "鈴木 花子", school: "大阪中学校", gender: "女" },
  { name: "佐藤 次郎", school: "名古屋中学校", gender: "男" },
  { name: "高橋 美咲", school: "福岡中学校", gender: "女" },
  { name: "伊藤 健", school: "札幌中学校", gender: "男" },
  { name: "中村 彩", school: "東京中学校", gender: "女" },
  { name: "小林 一郎", school: "大阪中学校", gender: "男" },
  { name: "山本 裕子", school: "名古屋中学校", gender: "女" },
  { name: "加藤 剛", school: "福岡中学校", gender: "男" },
  { name: "吉田 明美", school: "札幌中学校", gender: "女" },
  { name: "吉田 明美", school: "札幌中学校", gender: "女" },
  { name: "斉藤 直樹", school: "東京中学校", gender: "男" },
  { name: "橋本 美佳", school: "大阪中学校", gender: "女" },
  { name: "石川 勇", school: "名古屋中学校", gender: "男" },
  { name: "村上 玲奈", school: "福岡中学校", gender: "女" },
  { name: "岡田 大輔", school: "札幌中学校", gender: "男" },
  { name: "藤田 真由美", school: "東京中学校", gender: "女" },
  { name: "松本 翔", school: "大阪中学校", gender: "男" },
  { name: "森田 里奈", school: "名古屋中学校", gender: "女" },
  { name: "西村 拓也", school: "福岡中学校", gender: "男" },
  { name: "遠藤 奈緒", school: "札幌中学校", gender: "女" },
  { name: "金子 健太", school: "東京中学校", gender: "男" },
  { name: "中川 美咲", school: "大阪中学校", gender: "女" }
]*/

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
      card.innerHTML = `
		  <div class="student-row"><span class="label">氏名</span><span class="value name">${student.name}</span></div>
		  <div class="student-row"><span class="label">学校名</span><span class="value">${student.school}</span></div>
		  <div class="student-row"><span class="label">性別</span><span class="value">${student.gender}</span></div>
	  `;

	card.addEventListener("click", () => {
	  const encodedName = encodeURIComponent(student.name);
	  window.location.href = `/studentDetail.jsp?name=${encodedName}`;
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

