package VO;

import java.io.Serializable;
import java.util.Date;

public class RemoteclinicVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int rmtclinic_num;
	private String rmtclinic_file_name;
	private Date rmtclinic_date;
	private int appt_num;
	private String pa_id;
	private int doctor_num;
	private Date rmtclinic_start_time;
	private Date rmtclinic_end_time;
	
	public int getRmtclinic_num() {
		return rmtclinic_num;
	}
	public void setRmtclinic_num(int rmtclinic_num) {
		this.rmtclinic_num = rmtclinic_num;
	}
	public String getRmtclinic_file_name() {
		return rmtclinic_file_name;
	}
	public void setRmtclinic_file_name(String rmtclinic_file_name) {
		this.rmtclinic_file_name = rmtclinic_file_name;
	}
	public Date getRmtclinic_date() {
		return rmtclinic_date;
	}
	public void setRmtclinic_date(Date rmtclinic_date) {
		this.rmtclinic_date = rmtclinic_date;
	}
	public int getAppt_num() {
		return appt_num;
	}
	public void setAppt_num(int appt_num) {
		this.appt_num = appt_num;
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
	public Date getRmtclinic_start_time() {
		return rmtclinic_start_time;
	}
	public void setRmtclinic_start_time(Date rmtclinic_start_time) {
		this.rmtclinic_start_time = rmtclinic_start_time;
	}
	public Date getRmtclinic_end_time() {
		return rmtclinic_end_time;
	}
	public void setRmtclinic_end_time(Date rmtclinic_end_time) {
		this.rmtclinic_end_time = rmtclinic_end_time;
	}
	
	
}
