package VO;

import java.io.Serializable;

public class SurveyVO implements Serializable {
	private static final long serialVersionUID = 8336416363709537754L;
	private int board_num;
	private int survey_num;
	private double grade;
	private String survey_cont;
	
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public int getSurvey_num() {
		return survey_num;
	}
	public void setSurvey_num(int survey_num) {
		this.survey_num = survey_num;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getSurvey_cont() {
		return survey_cont;
	}
	public void setSurvey_cont(String survey_cont) {
		this.survey_cont = survey_cont;
	}
	
	
}
