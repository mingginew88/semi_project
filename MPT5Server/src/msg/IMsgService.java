package msg;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.DoctorVO;
import VO.MessageVO;

public interface IMsgService extends Remote{
	/**
	 * 해당 의사에게 온 모든 메시지를 불러오는 메서드
	 * @return
	 */
	List<MessageVO> getAllMsg(int doctor_num) throws RemoteException;
	
	/**
	 * 모든 의사 리스트 불러오는 메서드
	 * @return
	 */
	List<DoctorVO> getAllDoctor() throws RemoteException; 
	
	/**
	 * 아이디에 해당하는 의사 불러오는 메서드
	 * @param doctor_num
	 * @return doctor
	 */
	DoctorVO searchDoctor(int doctor_num) throws RemoteException;
	
	/**
	 * 아이디에 해당하는 의사 이름만 불러오는 메서드...
	 * @param doctor_num
	 * @return
	 */
	String searchDoctorName(int doctor_num) throws RemoteException;
	
	/**
	 * 메시지 DB에 추가하는 메서드
	 * @param msgVo
	 * @return 성공시 1 아니면 0
	 */
	int insertMsg(MessageVO msgVo) throws RemoteException;
	
	/**
	 * 보낸 메시지 리스트 불러오는 메서드
	 * @param doctor_num
	 * @return
	 */
	List<MessageVO> getAllReMsg(int doctor_num) throws RemoteException;
}
