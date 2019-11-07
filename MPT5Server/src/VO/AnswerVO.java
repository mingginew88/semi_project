package VO;

import java.io.Serializable;
import java.util.Date;

public class AnswerVO implements Serializable {
	private static final long serialVersionUID = 8336416363709537754L;
	private int answer_num;
	private String answer_title;
	private Date answer_date;
	private String answer_cont;
	private int board_num;
	private int doctor_num;
	
	public int getAnswer_num() {
		return answer_num;
	}
	public void setAnswer_num(int answer_num) {
		this.answer_num = answer_num;
	}
	public String getAnswer_title() {
		return answer_title;
	}
	public void setAnswer_title(String answer_title) {
		this.answer_title = answer_title;
	}
	public Date getAnswer_date() {
		return answer_date;
	}
	public void setAnswer_date(Date answer_date) {
		this.answer_date = answer_date;
	}
	public String getAnswer_cont() {
		return answer_cont;
	}
	public void setAnswer_cont(String answer_cont) {
		this.answer_cont = answer_cont;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public int getDoctor_num() {
		return doctor_num;
	}
	public void setDoctor_num(int doctor_num) {
		this.doctor_num = doctor_num;
	}
	
	
}
