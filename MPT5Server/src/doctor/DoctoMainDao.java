package doctor;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AppointmentVO;
import VO.DoctorVO;
import VO.MessageVO;
import VO.NoticeVO;
import VO.ScheduleVO;

public class DoctoMainDao implements IDoctorMainDao{
	private static DoctoMainDao dmDao;
	private SqlMapClient smc;
	
	private DoctoMainDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DoctoMainDao getInstance() {
		return dmDao==null? dmDao=new DoctoMainDao():dmDao;
	}

	@Override
	public List<MessageVO> getMsg(int doctor_num) {
		List<MessageVO> msgList = null;
		try {
			msgList = smc.queryForList("doctorMain.getMsg", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msgList;
	}

	@Override
	public List<ScheduleVO> getAllSche(int doctor_num) {
		List<ScheduleVO> scheList = null;
		try {
			scheList = smc.queryForList("doctorMain.getAllSche", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scheList;
	}

	@Override
	public List<AppointmentVO> getAllAppt(int doctor_num) {
		List<AppointmentVO> apptList = new ArrayList<AppointmentVO>();
		try {
			apptList = smc.queryForList("doctorMain.getAllAppt", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return apptList;
	}

	@Override
	public List<NoticeVO> getAllNotice() {
		List<NoticeVO> noticeList = null;
		try {
			noticeList = smc.queryForList("doctorMain.getAllNotice");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return noticeList;
	}

	@Override
	public String searchPaName(String pa_id) {
		String paName = "";
		try {
			paName = (String) smc.queryForObject("doctorMain.searchPaName", pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paName;
	}

	@Override
	public DoctorVO getDoctorInfo(int doctor_num) throws RemoteException {
		DoctorVO doctorVo = null;
		try {
			doctorVo = (DoctorVO) smc.queryForObject("doctorMain.getDoctorInfo", doctor_num);
		} catch (SQLException e) {
			doctorVo = null;
			e.printStackTrace();
		}
		return doctorVo;
	}

	@Override
	public int updateDoctorInfo(DoctorVO doctorVo) throws RemoteException {
		int cnt = 0;
		try {
			cnt = smc.update("doctorMain.updateDoctorInfo", doctorVo);				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

}
