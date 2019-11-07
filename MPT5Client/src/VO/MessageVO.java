package VO;

import java.io.Serializable;
import java.util.Date;

public class MessageVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int msg_num;
	private int msg_sd;
	private int msg_rc;
	private String msg_cont;
	private Date msg_date;
	
	public int getMsg_num() {
		return msg_num;
	}
	public void setMsg_num(int msg_num) {
		this.msg_num = msg_num;
	}
	public int getMsg_sd() {
		return msg_sd;
	}
	public void setMsg_sd(int msg_sd) {
		this.msg_sd = msg_sd;
	}
	public int getMsg_rc() {
		return msg_rc;
	}
	public void setMsg_rc(int msg_rc) {
		this.msg_rc = msg_rc;
	}
	public String getMsg_cont() {
		return msg_cont;
	}
	public void setMsg_cont(String msg_cont) {
		this.msg_cont = msg_cont;
	}
	public Date getMsg_date() {
		return msg_date;
	}
	public void setMsg_date(Date msg_date) {
		this.msg_date = msg_date;
	}
	
	
	
	
}
