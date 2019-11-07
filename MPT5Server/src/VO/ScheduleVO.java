package VO;

import java.io.Serializable;

public class ScheduleVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int sche_num;
	private String sche_kind;
	private String sche_name;
	private String sche_startdate;
	private String sche_cont;
	private String sche_color;
	private int doctor_num;
	
	public ScheduleVO(String sche_kind, String sche_name, String sche_startdate, String sche_cont) {
		super();
		this.sche_kind = sche_kind;
		this.sche_name = sche_name;
		this.sche_startdate = sche_startdate;
		this.sche_cont = sche_cont;
	}
	
	public ScheduleVO() {
		
	}
	
	public int getSche_num() {
		return sche_num;
	}
	public void setSche_num(int sche_num) {
		this.sche_num = sche_num;
	}
	public String getSche_kind() {
		return sche_kind;
	}
	public void setSche_kind(String sche_kind) {
		this.sche_kind = sche_kind;
	}
	public String getSche_name() {
		return sche_name;
	}
	public void setSche_name(String sche_name) {
		this.sche_name = sche_name;
	}
	public String getSche_startdate() {
		return sche_startdate;
	}
	public void setSche_startdate(String sche_startdate) {
		this.sche_startdate = sche_startdate;
	}
	public String getSche_cont() {
		return sche_cont;
	}
	public void setSche_cont(String sche_cont) {
		this.sche_cont = sche_cont;
	}
	public String getSche_color() {
		return sche_color;
	}
	public void setSche_color(String sche_color) {
		this.sche_color = sche_color;
	}
	public int getDoctor_num() {
		return doctor_num;
	}
	public void setDoctor_num(int doctor_num) {
		this.doctor_num = doctor_num;
	}
	
	
	
	

}
