package VO;

import java.io.Serializable;
import java.util.Date;

public class DoctorVO implements Serializable {
	private static final long serialVersionUID = 8336416363709537754L;
	private int doctor_num;
	private String doctor_name;
	private String doctor_pw;
	private String doctor_gen;
	private int doctor_reg1;
	private int doctor_reg2;
	private String doctor_addr;
	private String doctor_email;
	private int dept_num;
	private Date rmtclinic_pos_time;
	private Date vstclinic_pos_time;
	private Date work_start_time;
	private Date work_end_time;
	
	public int getDoctor_num() {
		return doctor_num;
	}
	public void setDoctor_num(int doctor_num) {
		this.doctor_num = doctor_num;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getDoctor_pw() {
		return doctor_pw;
	}
	public void setDoctor_pw(String doctor_pw) {
		this.doctor_pw = doctor_pw;
	}
	public String getDoctor_gen() {
		return doctor_gen;
	}
	public void setDoctor_gen(String doctor_gen) {
		this.doctor_gen = doctor_gen;
	}
	public int getDoctor_reg1() {
		return doctor_reg1;
	}
	public void setDoctor_reg1(int doctor_reg1) {
		this.doctor_reg1 = doctor_reg1;
	}
	public int getDoctor_reg2() {
		return doctor_reg2;
	}
	public void setDoctor_reg2(int doctor_reg2) {
		this.doctor_reg2 = doctor_reg2;
	}
	public String getDoctor_addr() {
		return doctor_addr;
	}
	public void setDoctor_addr(String doctor_addr) {
		this.doctor_addr = doctor_addr;
	}
	public String getDoctor_email() {
		return doctor_email;
	}
	public void setDoctor_email(String doctor_email) {
		this.doctor_email = doctor_email;
	}
	public int getDept_num() {
		return dept_num;
	}
	public void setDept_num(int dept_num) {
		this.dept_num = dept_num;
	}
	public Date getRmtclinic_pos_time() {
		return rmtclinic_pos_time;
	}
	public void setRmtclinic_pos_time(Date rmtclinic_pos_time) {
		this.rmtclinic_pos_time = rmtclinic_pos_time;
	}
	public Date getVstclinic_pos_time() {
		return vstclinic_pos_time;
	}
	public void setVstclinic_pos_time(Date vstclinic_pos_time) {
		this.vstclinic_pos_time = vstclinic_pos_time;
	}
	public Date getWork_start_time() {
		return work_start_time;
	}
	public void setWork_start_time(Date work_start_time) {
		this.work_start_time = work_start_time;
	}
	public Date getWork_end_time() {
		return work_end_time;
	}
	public void setWork_end_time(Date work_end_time) {
		this.work_end_time = work_end_time;
	}
	
	
	
}
