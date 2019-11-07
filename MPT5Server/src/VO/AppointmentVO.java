package VO;

import java.io.Serializable;
import java.util.Date;

public class AppointmentVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int appt_num;
	private Date appt_date;
	private String pa_id;
	private int doctor_num;
	private String appt_kind;
	private String appt_chid;
	private String appt_portn;
	private String appt_ip;
	
	public String getAppt_chid() {
		return appt_chid;
	}
	public void setAppt_chid(String appt_chid) {
		this.appt_chid = appt_chid;
	}
	public String getAppt_portn() {
		return appt_portn;
	}
	public void setAppt_portn(String appt_portn) {
		this.appt_portn = appt_portn;
	}
	public String getAppt_ip() {
		return appt_ip;
	}
	public void setAppt_ip(String appt_ip) {
		this.appt_ip = appt_ip;
	}
	public int getAppt_num() {
		return appt_num;
	}
	public void setAppt_num(int appt_num) {
		this.appt_num = appt_num;
	}
	public Date getAppt_date() {
		return appt_date;
	}
	public void setAppt_date(Date appt_date) {
		this.appt_date = appt_date;
	}
	public String getPa_id() {
		return pa_id;
	}
	public void setPa_id(String pa_id) {
		this.pa_id = pa_id;
	}
	public int getDoctor_num() {
		return doctor_num;
	}
	public void setDoctor_num(int doctor_num) {
		this.doctor_num = doctor_num;
	}
	public String getAppt_kind() {
		return appt_kind;
	}
	public void setAppt_kind(String appt_kind) {
		this.appt_kind = appt_kind;
	}
}
