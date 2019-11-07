package VO;

import java.io.Serializable;
import java.util.Date;

public class PrescriptionVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int presc_num;
	private String presc_cont;
	private Date presc_date;
	private String pa_id;
	private int doctor_num;
	private int clinic_num;
	private int dis_num;
	private int medi_code;
	
	public int getPresc_num() {
		return presc_num;
	}
	public void setPresc_num(int presc_num) {
		this.presc_num = presc_num;
	}
	public String getPresc_cont() {
		return presc_cont;
	}
	public void setPresc_cont(String presc_cont) {
		this.presc_cont = presc_cont;
	}
	public Date getPresc_date() {
		return presc_date;
	}
	public void setPresc_date(Date presc_date) {
		this.presc_date = presc_date;
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
	public int getClinic_num() {
		return clinic_num;
	}
	public void setClinic_num(int clinic_num) {
		this.clinic_num = clinic_num;
	}
	public int getDis_num() {
		return dis_num;
	}
	public void setDis_num(int dis_num) {
		this.dis_num = dis_num;
	}
	public int getMedi_code() {
		return medi_code;
	}
	public void setMedi_code(int medi_code) {
		this.medi_code = medi_code;
	}
}
