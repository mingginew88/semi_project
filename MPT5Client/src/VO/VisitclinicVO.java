package VO;

import java.io.Serializable;
import java.util.Date;

public class VisitclinicVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int clinic_num;
	private String vstclinic_cont;
	private String pa_id;
	private int doctor_num;
	private int appt_num;
	private Date vstclinic_Date;
	
	public int getClinic_num() {
		return clinic_num;
	}
	public void setClinic_num(int clinic_num) {
		this.clinic_num = clinic_num;
	}
	public String getVstclinic_cont() {
		return vstclinic_cont;
	}
	public void setVstclinic_cont(String vstclinic_cont) {
		this.vstclinic_cont = vstclinic_cont;
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
	public int getAppt_num() {
		return appt_num;
	}
	public void setAppt_num(int appt_num) {
		this.appt_num = appt_num;
	}
	public Date getVstclinic_Date() {
		return vstclinic_Date;
	}
	public void setVstclinic_Date(Date vstclinic_Date) {
		this.vstclinic_Date = vstclinic_Date;
	}
	
	
}
