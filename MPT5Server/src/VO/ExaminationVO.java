package VO;

import java.io.Serializable;
import java.util.Date;

public class ExaminationVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private String pa_id;
	private Date exam_rec_date;
	private String exam_cont;
	private int fever;
	private int red_blood_cell;
	private int white_blood_cell;
	private int platelet;
	private int exam_bad1;
	private int exam_bad2;
	private int exam_bad3;
	private int clinic_num;
	private int doctor_num;
	private int exam_num;
	
	public int getExam_num() {
		return exam_num;
	}
	public void setExam_num(int exam_num) {
		this.exam_num = exam_num;
	}
	public int getClinic_num() {
		return clinic_num;
	}
	public void setClinic_num(int clinic_num) {
		this.clinic_num = clinic_num;
	}
	public int getExam_bad1() {
		return exam_bad1;
	}
	public void setExam_bad1(int exam_bad1) {
		this.exam_bad1 = exam_bad1;
	}
	public int getExam_bad2() {
		return exam_bad2;
	}
	public void setExam_bad2(int exam_bad2) {
		this.exam_bad2 = exam_bad2;
	}
	public int getExam_bad3() {
		return exam_bad3;
	}
	public void setExam_bad3(int exam_bad3) {
		this.exam_bad3 = exam_bad3;
	}
	
	public int getDoctor_num() {
		return doctor_num;
	}
	public void setDoctor_num(int doctor_num) {
		this.doctor_num = doctor_num;
	}
	public String getPa_id() {
		return pa_id;
	}
	public void setPa_id(String pa_id) {
		this.pa_id = pa_id;
	}
	public Date getExam_rec_date() {
		return exam_rec_date;
	}
	public void setExam_rec_date(Date exam_rec_date) {
		this.exam_rec_date = exam_rec_date;
	}
	public String getExam_cont() {
		return exam_cont;
	}
	public void setExam_cont(String exam_cont) {
		this.exam_cont = exam_cont;
	}
	public int getFever() {
		return fever;
	}
	public void setFever(int fever) {
		this.fever = fever;
	}
	public int getRed_blood_cell() {
		return red_blood_cell;
	}
	public void setRed_blood_cell(int red_blood_cell) {
		this.red_blood_cell = red_blood_cell;
	}
	public int getWhite_blood_cell() {
		return white_blood_cell;
	}
	public void setWhite_blood_cell(int white_blood_cell) {
		this.white_blood_cell = white_blood_cell;
	}
	public int getPlatelet() {
		return platelet;
	}
	public void setPlatelet(int platelet) {
		this.platelet = platelet;
	}
}
