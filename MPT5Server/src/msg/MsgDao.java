package msg;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.DoctorVO;
import VO.MessageVO;

public class MsgDao implements IMsgDao{
	private static MsgDao msgDao;
	private SqlMapClient smc;
	
	private MsgDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MsgDao getInstance() {
		return msgDao==null? msgDao=new MsgDao():msgDao;
	}

	@Override
	public List<MessageVO> getAllMsg(int doctor_num) {
		List<MessageVO> msgList = null;
		
		try {
			msgList = smc.queryForList("msg.getAllMsg", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return msgList;
	}

	@Override
	public List<DoctorVO> getAllDoctor() {
		List<DoctorVO> doctorList = null;
		
		try {
			doctorList = smc.queryForList("msg.getAllDoctor");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return doctorList;
	}

	@Override
	public DoctorVO searchDoctor(int doctor_num) {
		DoctorVO doctor = null;
		try {
			doctor = (DoctorVO) smc.queryForObject("msg.searchDoctor", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctor;
	}

	@Override
	public String searchDoctorName(int doctor_num) {
		String doctorName = "";
		try {
			doctorName = (String) smc.queryForObject("msg.searchDoctorName",doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctorName;
	}

	@Override
	public int insertMsg(MessageVO msgVo) {
		int cnt = 0;
		try {
			if(smc.insert("msg.insertMsg",msgVo)==null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<MessageVO> getAllReMsg(int doctor_num) {
		List<MessageVO> msgList = null;
		
		try {
			msgList = smc.queryForList("msg.getAllReMsg", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return msgList;
	}
	
	
}
