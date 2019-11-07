package schedule;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import VO.ScheduleVO;

public class ScheduleService extends UnicastRemoteObject implements IScheduleService {
	private static IScheduleDao scheduleDao;
	
	public ScheduleService() throws RemoteException{
		scheduleDao = ScheduleDao.getInterFace();
	}

	@Override
	public ArrayList<ScheduleVO> getAllSchedule(Integer doctor_num) throws RemoteException{
		return scheduleDao.getAllSchedule(doctor_num);
	}

	@Override
	public int addSchedule(ScheduleVO scheduleVo) throws RemoteException {
		return scheduleDao.addSchedule(scheduleVo);
	}

	@Override
	public int deleteSchedule(int sche_num) throws RemoteException {
		return scheduleDao.deleteSchedule(sche_num);
	}

	@Override
	public int updateSchedule(ScheduleVO scheduleVo) throws RemoteException {
		return scheduleDao.updateSchedule(scheduleVo);
	}

	@Override
	public ArrayList<ScheduleVO> getScheduleFromDate(ScheduleVO scheduleVo) throws RemoteException {
		return scheduleDao.getScheduleFromDate(scheduleVo);
	}

	@Override
	public int deleteAllSchedule(int doctor_num) throws RemoteException {
		return scheduleDao.deleteAllSchedule(doctor_num);
	}
	
	@Override
	public ArrayList<ScheduleVO> getSelectedSchedule(Map<String, List<String>> map) throws RemoteException {
		System.out.println("map => " + map);
		return scheduleDao.getSelectedSchedule(map);
	}
	
	
	
	
}
