package schedule;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.ScheduleVO;

public class ScheduleDao implements IScheduleDao {
	private static ScheduleDao scheduleDao = new ScheduleDao();
	private SqlMapClient smc;
	
	private ScheduleDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ScheduleDao getInterFace() {
		if(scheduleDao == null) {
			scheduleDao = new ScheduleDao();
		}
		return scheduleDao;
	}


	@Override
	public ArrayList<ScheduleVO> getAllSchedule(Integer doctor_num) throws RemoteException{
		ArrayList<ScheduleVO> scheduleList = null;
		try {
			scheduleList = (ArrayList<ScheduleVO>) smc.queryForList("schedule.getAllSchedule", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scheduleList;
	}

	@Override
	public int addSchedule(ScheduleVO scheduleVo) throws RemoteException {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.insert("schedule.addSchedule", scheduleVo);
			if(obj==null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteSchedule(int sche_num) throws RemoteException {
		int cnt = 0;
		try {
			cnt = smc.delete("schedule.deleteSchedule", sche_num);			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return cnt;
	}

	@Override
	public int updateSchedule(ScheduleVO scheduleVo) throws RemoteException {
		int cnt = 0;
		try {
			cnt = smc.update("schedule.updateSchedule", scheduleVo);				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public ArrayList<ScheduleVO> getScheduleFromDate(ScheduleVO scheduleVo) throws RemoteException {
		ArrayList<ScheduleVO> scheduleList = null;
		try {
			scheduleList = (ArrayList<ScheduleVO>) smc.queryForList("schedule.getScheduleFromDate", scheduleVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scheduleList;
	}

	@Override
	public int deleteAllSchedule(int doctor_num) throws RemoteException {
		int cnt = 0;
		try {
			cnt = smc.delete("schedule.deleteAllSchedule", doctor_num);			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return cnt;
	}
	
	@Override
	public ArrayList<ScheduleVO> getSelectedSchedule(Map<String, List<String>> map) throws RemoteException {
		ArrayList<ScheduleVO> scheduleList = null;
		try {
			scheduleList = (ArrayList<ScheduleVO>) smc.queryForList("schedule.getSelectedSchedule", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scheduleList;
	}
	

	
	
	

}
