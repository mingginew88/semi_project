package schedule;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import VO.ScheduleVO;

public interface IScheduleDao {
	
	/**
	 * 의사번호에 맞는 모든 일정을 List형태로 가져온다.
	 * @param doctor_num
	 * @return ArrayList<ScheduleVO>
	 * @throws RemoteException
	 */
	public ArrayList<ScheduleVO> getAllSchedule(Integer doctor_num) throws RemoteException;
	
	/**
	 * 일정VO로 하나의 일정을 추가한다.
	 * @param scheduleVo
	 * @return 1(일정추가 성공) 0(일정추가 실패)
	 * @throws RemoteException
	 */
	public int addSchedule(ScheduleVO scheduleVo) throws RemoteException;
	
	/**
	 * 일정고유번호와 일치하는 일정을 삭제한다.
	 * @param sche_num
	 * @return 1(일정삭제 성공) 0(일정삭제 실패)
	 * @throws RemoteException
	 */
	public int deleteSchedule(int sche_num) throws RemoteException;
	
	/**
	 * 일정 수정
	 * @param scheduleVo
	 * @return 1(일정수정 성공) 0(일정수정 실패)
	 * @throws RemoteException
	 */
	public int updateSchedule(ScheduleVO scheduleVo) throws RemoteException;
	
	/**
	 * 날짱에 해당하는 일정 정보들을 List형태로 담는다.
	 * @param scheduleVo
	 * @return ArrayList<ScheduleVO>
	 * @throws RemoteException
	 */
	public ArrayList<ScheduleVO> getScheduleFromDate(ScheduleVO scheduleVo) throws RemoteException;
	
	/**
	 * 의사번호에 일치하는 모든 일정을 삭제한다.
	 * @param doctor_num
	 * @return 1(모든 일정삭제성공) 0(일정삭제 실패)
	 * @throws RemoteException
	 */
	public int deleteAllSchedule(int doctor_num) throws RemoteException;
	
	/**
	 * 일정종류와 해당 날짜에 있는 일정들을 List로 담는다.
	 * @param sche_kind
	 * @return ArrayList<ScheduleVO>
	 * @throws RemoteException
	 */
	public ArrayList<ScheduleVO> getSelectedSchedule(Map<String, List<String>> map) throws RemoteException;
	



}
