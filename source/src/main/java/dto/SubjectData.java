package dto;

import java.util.List;

public class SubjectData {
	private int studentId;
	private int subjectId;

	// 学習計画データ
	private String understanding; // 理解度
	private String textSelection; // テキスト選出
	private String schedule; // スケジュール
	private String homeworkPages; // 宿題ページ数

	// メモ欄データ
	private String lastContent; // 前回やったこと
	private String nextContent; // 次回やること
	private String homework; // 宿題内容
	private String note; // 備考

	// 教科別模試結果
	private List<IndividualResults.ExamScore> examResults;

	// コンストラクタ
	public SubjectData() {
	}

	// getter/setter
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

	public List<IndividualResults.ExamScore> getExamResults() {
		return examResults;
	}

	public void setExamResults(List<IndividualResults.ExamScore> examResults) {
		this.examResults = examResults;
	}
}