package VO;

import java.io.Serializable;

public class DiseaseVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int dis_num;
	private String dis_name;
	private String dis_symp;
	private String pa_id;
	private int clinic_num;
	
	public int getDis_num() {
		return dis_num;
	}
	public void setDis_num(int dis_num) {
		this.dis_num = dis_num;
	}
	public String getDis_name() {
		return dis_name;
	}
	public void setDis_name(String dis_name) {
		this.dis_name = dis_name;
	}
	public String getDis_symp() {
		return dis_symp;
	}
	public void setDis_symp(String dis_symp) {
		this.dis_symp = dis_symp;
	}
	public String getPa_id() {
		return pa_id;
	}
	public void setPa_id(String pa_id) {
		this.pa_id = pa_id;
	}
	public int getClinic_num() {
		return clinic_num;
	}
	public void setClinic_num(int clinic_num) {
		this.clinic_num = clinic_num;
	}
	
}
