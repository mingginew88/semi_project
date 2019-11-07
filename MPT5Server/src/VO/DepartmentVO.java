package VO;

import java.io.Serializable;

public class DepartmentVO implements Serializable{
	private static final long serialVersionUID = 8336416363709537754L;
	private int dept_num;
	private String dept_name;
	private int hos_num;
	
	public int getDept_num() {
		return dept_num;
	}
	public void setDept_num(int dept_num) {
		this.dept_num = dept_num;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public int getHos_num() {
		return hos_num;
	}
	public void setHos_num(int hos_num) {
		this.hos_num = hos_num;
	}
	
}
