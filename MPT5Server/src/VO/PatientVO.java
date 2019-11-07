package VO;

import java.io.Serializable;

public class PatientVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private String pa_id;
	private String pa_name;
	private String pa_gen;
	private String pa_pw;
	private int pa_reg1;
	private int pa_reg2;
	private String pa_addr;
	private String pa_email;
	private String pa_tel;
	
	public String getPa_id() {
		return pa_id;
	}
	public void setPa_id(String pa_id) {
		this.pa_id = pa_id;
	}
	public String getPa_name() {
		return pa_name;
	}
	public void setPa_name(String pa_name) {
		this.pa_name = pa_name;
	}
	public String getPa_gen() {
		return pa_gen;
	}
	public void setPa_gen(String pa_gen) {
		this.pa_gen = pa_gen;
	}
	public String getPa_pw() {
		return pa_pw;
	}
	public void setPa_pw(String pa_pw) {
		this.pa_pw = pa_pw;
	}
	public int getPa_reg1() {
		return pa_reg1;
	}
	public void setPa_reg1(int pa_reg1) {
		this.pa_reg1 = pa_reg1;
	}
	public int getPa_reg2() {
		return pa_reg2;
	}
	public void setPa_reg2(int pa_reg2) {
		this.pa_reg2 = pa_reg2;
	}
	public String getPa_addr() {
		return pa_addr;
	}
	public void setPa_addr(String pa_addr) {
		this.pa_addr = pa_addr;
	}
	public String getPa_email() {
		return pa_email;
	}
	public void setPa_email(String pa_email) {
		this.pa_email = pa_email;
	}
	public String getPa_tel() {
		return pa_tel;
	}
	public void setPa_tel(String pa_tel) {
		this.pa_tel = pa_tel;
	}
}
