package admin.adminDB;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import VO.DoctorVO;
import VO.MedicineVO;
import VO.MessageVO;
import VO.ScheduleVO;
import VO.VisitclinicVO;

public interface IAdminDBService extends Remote {
	/**
	 * 약 리스트 불러오는 메서드
	 * 
	 * @return List<MedicineVO>
	 * @throws RemoteException
	 */
	public List<MedicineVO> getAllMedi() throws RemoteException;

	/**
	 * 약 검색하는 메서드
	 * 
	 * @return List<MedicineVO>
	 * @throws RemoteException
	 */
	public List<MedicineVO> searchMedi(String mediName) throws RemoteException;

	/**
	 * 약을 추가하는 메서드
	 * 
	 * @param MedicineVO
	 *            medi
	 * @return int
	 * @throws RemoteException
	 */
	public int addMedi(MedicineVO medi) throws RemoteException;

	/**
	 * 약을 삭제하는 메서드
	 * 
	 * @param int
	 *            mediCode
	 * @return int
	 * @throws RemoteException
	 */
	public int deleteMedi(int mediCode) throws RemoteException;

	/**
	 * 방문 상담내역 리스트 불러오는 메서드
	 * 
	 * @return List<VisitclinicVO>
	 * @throws RemoteException
	 */
	public List<VisitclinicVO> getVCList() throws RemoteException;

	/**
	 * 환자의 방문상담내역 검색하는 메서드
	 * 
	 * @param searchMap
	 * @return List<VisitclinicVO>
	 * @throws RemoteException
	 */
	public List<VisitclinicVO> searchVs(Map<String, String> searchMap) throws RemoteException;

	/**
	 * 의사 메시지 리스트 불러오는 메서드
	 * 
	 * @return List<MessageVO>
	 * @throws RemoteException
	 */
	public List<MessageVO> getMsgList() throws RemoteException;

	public String getDoctorName(int doctorNum) throws RemoteException;

	/**
	 * 메세지 보낸이를 검색하는 메서드
	 * 
	 * @param sender
	 * @return List<MessageVO>
	 * @throws RemoteException
	 */
	public List<MessageVO> searchSdMsg(String sender) throws RemoteException;

	/**
	 * 메세지 받은이를 검색하는 메서드
	 * 
	 * @param receiver
	 * @return List<MessageVO>
	 * @throws RemoteException
	 */
	public List<MessageVO> searchRcMsg(String receiver) throws RemoteException;

	/**
	 * 메세지를 삭제하는 메서드
	 * 
	 * @param msgNum
	 * @return
	 * @throws RemoteException
	 */
	public int deleteMsg(int msgNum) throws RemoteException;

	public List<ScheduleVO> getScheList() throws RemoteException;

	public List<ScheduleVO> searchDocSche(String doctor_name) throws RemoteException;

}
