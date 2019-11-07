package VO;

import java.io.Serializable;
import java.util.Date;

public class QuestionVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int que_num;
	private String que_title;
	private Date que_date;
	private String que_cont;
	private int board_num;
	private String pa_id;
	
	public int getQue_num() {
		return que_num;
	}
	public void setQue_num(int que_num) {
		this.que_num = que_num;
	}
	public String getQue_title() {
		return que_title;
	}
	public void setQue_title(String que_title) {
		this.que_title = que_title;
	}
	public Date getQue_date() {
		return que_date;
	}
	public void setQue_date(Date que_date) {
		this.que_date = que_date;
	}
	public String getQue_cont() {
		return que_cont;
	}
	public void setQue_cont(String que_cont) {
		this.que_cont = que_cont;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getPa_id() {
		return pa_id;
	}
	public void setPa_id(String pa_id) {
		this.pa_id = pa_id;
	}
}
