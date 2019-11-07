package VO;

import java.io.Serializable;
import java.util.Date;

public class HospitalVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int hos_num;
	private String hos_name;
	private String hos_addr;
	private String hos_tel;
	private Date hos_start_time;
	private Date hos_end_time;
	
	public int getHos_num() {
		return hos_num;
	}
	public void setHos_num(int hos_num) {
		this.hos_num = hos_num;
	}
	public String getHos_name() {
		return hos_name;
	}
	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}
	public String getHos_addr() {
		return hos_addr;
	}
	public void setHos_addr(String hos_addr) {
		this.hos_addr = hos_addr;
	}
	public String getHos_tel() {
		return hos_tel;
	}
	public void setHos_tel(String hos_tel) {
		this.hos_tel = hos_tel;
	}
	public Date getHos_start_time() {
		return hos_start_time;
	}
	public void setHos_start_time(Date hos_start_time) {
		this.hos_start_time = hos_start_time;
	}
	public Date getHos_end_time() {
		return hos_end_time;
	}
	public void setHos_end_time(Date hos_end_time) {
		this.hos_end_time = hos_end_time;
	}
}
