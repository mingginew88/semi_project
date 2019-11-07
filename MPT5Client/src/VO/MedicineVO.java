package VO;

import java.io.Serializable;

public class MedicineVO implements Serializable {
	private static final long serialVersionUID = 8336416363709537754L;
	private int medi_code;
	private String medi_name;
	private String medi_category;
	private String medi_pms;
	private String medi_dose;
	
	public int getMedi_code() {
		return medi_code;
	}
	public void setMedi_code(int medi_code) {
		this.medi_code = medi_code;
	}
	public String getMedi_name() {
		return medi_name;
	}
	public void setMedi_name(String medi_name) {
		this.medi_name = medi_name;
	}
	public String getMedi_category() {
		return medi_category;
	}
	public void setMedi_category(String medi_category) {
		this.medi_category = medi_category;
	}
	public String getMedi_pms() {
		return medi_pms;
	}
	public void setMedi_pms(String medi_pms) {
		this.medi_pms = medi_pms;
	}
	public String getMedi_dose() {
		return medi_dose;
	}
	public void setMedi_dose(String medi_dose) {
		this.medi_dose = medi_dose;
	}
}
