package dto;

import java.util.Date;
import java.util.List;

/**
 * 個人結果表示専用 DTO - 生徒基本情報 - 志望校情報 - 学校・性格 - 各教科のGPA（9教科） - 模試結果リスト -
 * スケジュール（テキスト選出）データ
 */
public class IndividualResults {
	// === 基本情報 ===
	private int id;
	private String name;
	private String furigana;
	private String gender;
	private Date birthday;

	// === 学校・性格 ===
	private String schoolName;
	private String personalityName;

	// === 志望校（名前） ===
	private String aspirationSchool1Name;
	private String aspirationSchool2Name;
	private String aspirationSchool3Name;

	// === GPA（9教科）===
	private int gpaJp; // 国語
	private int gpaSs; // 社会
	private int gpaMa; // 数学
	private int gpaSc; // 理科
	private int gpaEn; // 英語
	private int gpaMu; // 音楽
	private int gpaAr; // 美術
	private int gpaPe; // 保健体育
	private int gpaTe; // 技術家庭

	// === 作成日時・更新日時 ===
	private Date createdAt;
	private Date updatedAt;

	// === 模試結果一覧 ===
	private List<ExamScore> examResults;
	private List<Gpa> gpaList;

	// === スケジュール（テキスト選出）関連 ===
	private List<Schedule> schedules;

	// ========== Getter / Setter ==========

	public List<Gpa> getGpaList() {
		return gpaList;
	}

	public void setGpaList(List<Gpa> gpaList) {
		this.gpaList = gpaList;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFurigana() {
		return furigana;
	}

	public void setFurigana(String furigana) {
		this.furigana = furigana;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getPersonalityName() {
		return personalityName;
	}

	public void setPersonalityName(String personalityName) {
		this.personalityName = personalityName;
	}

	public String getAspirationSchool1Name() {
		return aspirationSchool1Name;
	}

	public void setAspirationSchool1Name(String aspirationSchool1Name) {
		this.aspirationSchool1Name = aspirationSchool1Name;
	}

	public String getAspirationSchool2Name() {
		return aspirationSchool2Name;
	}

	public void setAspirationSchool2Name(String aspirationSchool2Name) {
		this.aspirationSchool2Name = aspirationSchool2Name;
	}

	public String getAspirationSchool3Name() {
		return aspirationSchool3Name;
	}

	public void setAspirationSchool3Name(String aspirationSchool3Name) {
		this.aspirationSchool3Name = aspirationSchool3Name;
	}

	public int getGpaJp() {
		return gpaJp;
	}

	public void setGpaJp(int gpaJp) {
		this.gpaJp = gpaJp;
	}

	public int getGpaSs() {
		return gpaSs;
	}

	public void setGpaSs(int gpaSs) {
		this.gpaSs = gpaSs;
	}

	public int getGpaMa() {
		return gpaMa;
	}

	public void setGpaMa(int gpaMa) {
		this.gpaMa = gpaMa;
	}

	public int getGpaSc() {
		return gpaSc;
	}

	public void setGpaSc(int gpaSc) {
		this.gpaSc = gpaSc;
	}

	public int getGpaEn() {
		return gpaEn;
	}

	public void setGpaEn(int gpaEn) {
		this.gpaEn = gpaEn;
	}

	public int getGpaMu() {
		return gpaMu;
	}

	public void setGpaMu(int gpaMu) {
		this.gpaMu = gpaMu;
	}

	public int getGpaAr() {
		return gpaAr;
	}

	public void setGpaAr(int gpaAr) {
		this.gpaAr = gpaAr;
	}

	public int getGpaPe() {
		return gpaPe;
	}

	public void setGpaPe(int gpaPe) {
		this.gpaPe = gpaPe;
	}

	public int getGpaTe() {
		return gpaTe;
	}

	public void setGpaTe(int gpaTe) {
		this.gpaTe = gpaTe;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<ExamScore> getExamResults() {
		return examResults;
	}

	public void setExamResults(List<ExamScore> examResults) {
		this.examResults = examResults;
	}

	// ========================================
	// === スケジュール（テキスト選出）の内部クラス ===
	// ========================================
	public static class Schedule {
		private int id;
		private int studentId;
		private int subjectId;
		private int textId;
		private int pages;
		private String subjectName;
		private String textName;
		private int totalPages;

		// getter/setter
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getStudentId() {
			return studentId;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}

		public int getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(int subjectId) {
			this.subjectId = subjectId;
		}

		public int getTextId() {
			return textId;
		}

		public void setTextId(int textId) {
			this.textId = textId;
		}

		public int getPages() {
			return pages;
		}

		public void setPages(int pages) {
			this.pages = pages;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}

		public String getTextName() {
			return textName;
		}

		public void setTextName(String textName) {
			this.textName = textName;
		}

		public int getTotalPages() {
			return totalPages;
		}

		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}
	}

	// ========================================
	// === 既存の内部クラス ===
	// ========================================
	public static class Gpa {
		private String subjectName;
		private int gpa;

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}

		public int getGpa() {
			return gpa;
		}

		public void setGpa(int gpa) {
			this.gpa = gpa;
		}
	}

	// ==========================
	// === 模試結果の内部クラス ===
	// ==========================
	public static class ExamScore {
		private int id;
		private String examName; // 模試名
		private Date examDate; // 実施日
		private String subjectName; // 教科名
		private int score; // 点数（0～100）
		private double deviationValue; // 偏差値（例: 55.2）
		private double averageScore; // 平均点（例: 63.4）
		// 編集用に追加するフィールド
		private int examId; // exams テーブルのID
		private int examNameId; // exam_names テーブルのID

		// getter/setter（既存のものに追加）
		public int getExamId() {
			return examId;
		}

		public void setExamId(int examId) {
			this.examId = examId;
		}

		public int getExamNameId() {
			return examNameId;
		}

		public void setExamNameId(int examNameId) {
			this.examNameId = examNameId;
		}

		// id のgetter/setterを追加
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getExamName() {
			return examName;
		}

		public void setExamName(String examName) {
			this.examName = examName;
		}

		public Date getExamDate() {
			return examDate;
		}

		public void setExamDate(Date examDate) {
			this.examDate = examDate;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public double getDeviationValue() {
			return deviationValue;
		}

		public void setDeviationValue(double deviationValue) {
			this.deviationValue = deviationValue;
		}

		public double getAverageScore() {
			return averageScore;
		}

		public void setAverageScore(double averageScore) {
			this.averageScore = averageScore;
		}
	}
}