// ファイル名: IndividualResults.java
package dto;

import java.util.Date;
import java.util.List;

public class IndividualResults {
	private int id;
	private String name;
	private String furigana;
	private int schoolId;
	private String schoolName;
	private Date birthday;
	private String gender;
	private String aspirationSchool1Name;
	private String aspirationSchool2Name;
	private String aspirationSchool3Name;
	private String personalityName;
	private List<Gpa> gpaList;
	private List<ExamScore> examResults;
	private Date createdAt;
	private Date updatedAt;

	// 学習計画
	private String understanding;
	private String textSelection;
	private String schedule;
	private String homeworkPages;
	private String lastContent;
	private String nextContent;
	private String homework;
	private String note;

	// 各教科のGPA
	private Integer gpaJp;
	private Integer gpaSs;
	private Integer gpaMa;
	private Integer gpaSc;
	private Integer gpaEn;
	private Integer gpaMu;
	private Integer gpaAr;
	private Integer gpaPe;
	private Integer gpaTe;

	public IndividualResults() {
	}

	// ======== Getter / Setter ========
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

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAspirationSchool1Name() {
		return aspirationSchool1Name;
	}

	public void setAspirationSchool1Name(String name) {
		this.aspirationSchool1Name = name;
	}

	public String getAspirationSchool2Name() {
		return aspirationSchool2Name;
	}

	public void setAspirationSchool2Name(String name) {
		this.aspirationSchool2Name = name;
	}

	public String getAspirationSchool3Name() {
		return aspirationSchool3Name;
	}

	public void setAspirationSchool3Name(String name) {
		this.aspirationSchool3Name = name;
	}

	public String getPersonalityName() {
		return personalityName;
	}

	public void setPersonalityName(String name) {
		this.personalityName = name;
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

	public String getUnderstanding() {
		return understanding;
	}

	public void setUnderstanding(String understanding) {
		this.understanding = understanding;
	}

	public String getTextSelection() {
		return textSelection;
	}

	public void setTextSelection(String textSelection) {
		this.textSelection = textSelection;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getHomeworkPages() {
		return homeworkPages;
	}

	public void setHomeworkPages(String homeworkPages) {
		this.homeworkPages = homeworkPages;
	}

	public String getLastContent() {
		return lastContent;
	}

	public void setLastContent(String lastContent) {
		this.lastContent = lastContent;
	}

	public String getNextContent() {
		return nextContent;
	}

	public void setNextContent(String nextContent) {
		this.nextContent = nextContent;
	}

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Gpa> getGpaList() {
		return gpaList;
	}

	public void setGpaList(List<Gpa> gpaList) {
		this.gpaList = gpaList;
	}

	public List<ExamScore> getExamResults() {
		return examResults;
	}

	public void setExamResults(List<ExamScore> examResults) {
		this.examResults = examResults;
	}

	public Integer getGpaJp() {
		return gpaJp;
	}

	public void setGpaJp(Integer gpaJp) {
		this.gpaJp = gpaJp;
	}

	public Integer getGpaSs() {
		return gpaSs;
	}

	public void setGpaSs(Integer gpaSs) {
		this.gpaSs = gpaSs;
	}

	public Integer getGpaMa() {
		return gpaMa;
	}

	public void setGpaMa(Integer gpaMa) {
		this.gpaMa = gpaMa;
	}

	public Integer getGpaSc() {
		return gpaSc;
	}

	public void setGpaSc(Integer gpaSc) {
		this.gpaSc = gpaSc;
	}

	public Integer getGpaEn() {
		return gpaEn;
	}

	public void setGpaEn(Integer gpaEn) {
		this.gpaEn = gpaEn;
	}

	public Integer getGpaMu() {
		return gpaMu;
	}

	public void setGpaMu(Integer gpaMu) {
		this.gpaMu = gpaMu;
	}

	public Integer getGpaAr() {
		return gpaAr;
	}

	public void setGpaAr(Integer gpaAr) {
		this.gpaAr = gpaAr;
	}

	public Integer getGpaPe() {
		return gpaPe;
	}

	public void setGpaPe(Integer gpaPe) {
		this.gpaPe = gpaPe;
	}

	public Integer getGpaTe() {
		return gpaTe;
	}

	public void setGpaTe(Integer gpaTe) {
		this.gpaTe = gpaTe;
	}

	// ========== 内部クラス: Gpa ==========
	public static class Gpa {
		private int id;
		private int studentId;
		private int subjectId;
		private int gpa;
		private String subjectName;
		private Date createdAt;
		private Date updatedAt;

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

		public int getGpa() {
			return gpa;
		}

		public void setGpa(int gpa) {
			this.gpa = gpa;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
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
	}

	// ========== 内部クラス: ExamScore ==========
	public static class ExamScore {
		private int id;
		private int examId;
		private int studentId;
		private int subjectId;
		private int score;
		private double averageScore;
		private double deviationValue;
		private String examName;
		private String subjectName;
		private Date examDate;
		private Date createdAt;
		private Date updatedAt;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getExamId() {
			return examId;
		}

		public void setExamId(int examId) {
			this.examId = examId;
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

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public double getAverageScore() {
			return averageScore;
		}

		public void setAverageScore(double averageScore) {
			this.averageScore = averageScore;
		}

		public double getDeviationValue() {
			return deviationValue;
		}

		public void setDeviationValue(double deviationValue) {
			this.deviationValue = deviationValue;
		}

		public String getExamName() {
			return examName;
		}

		public void setExamName(String examName) {
			this.examName = examName;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}

		public Date getExamDate() {
			return examDate;
		}

		public void setExamDate(Date examDate) {
			this.examDate = examDate;
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
	}
}