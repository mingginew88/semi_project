package doctor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.AppointmentVO;
import VO.DoctorVO;
import VO.MessageVO;
import VO.NoticeVO;
import VO.ScheduleVO;

public interface IDoctorMainService extends Remote{
	/**
	 * 현재 시간에 새로 온 메시지 리스트 가져오는 메서드
	 * @param doctor_num
	 * @return List<MessageVO>
	 */
	public List<MessageVO> getMsg(int doctor_num) throws RemoteException;
	
	/**
	 * 해당 의사 스케줄 다 가져오기
	 * @param doctor_num
	 * @return
	 * @throws RemoteException
	 */
	public List<ScheduleVO> getAllSche(int doctor_num) throws RemoteException;
	
	/**
	 * 해당 의사 예약리스트 다 가져오기
	 * @param doctor_num
	 * @return
	 * @throws RemoteException
	 */
	public List<AppointmentVO> getAllAppt(int doctor_num) throws RemoteException;
	
	/**
	 * 공지사항 다 가져오기
	 * @return
	 * @throws RemoteException
	 */
	public List<NoticeVO> getAllNotice() throws RemoteException;
	
	/**
	 * 아이디에 해당하는 환자 이름 가져오기
	 * @param pa_id
	 * @return
	 * @throws RemoteException
	 */
	public String searchPaName(String pa_id) throws RemoteException; 
	
	/**
	 * 의사 정보
	 * @param doctor_num
	 * @return DoctorVO
	 * @throws RemoteException
	 */
	public DoctorVO getDoctorInfo(int doctor_num) throws RemoteException;
	
	/**
	 * 의사정보 (주소와 이메일을 수정가능)
	 * @param doctorVo
	 * @return 1(의사정보 수정성공) 0(의사정보 수정실패)
	 * @throws RemoteException
	 */
	public int updateDoctorInfo(DoctorVO doctorVo) throws RemoteException;
}
