package VO;

import java.io.Serializable;

public class BoardVO implements Serializable {
	private static final long serialVersionUID = 8336416363709537754L;
	private int board_num;
	private String board_name;
	
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
}
