package patient;

import java.rmi.RemoteException;
import java.util.List;

import VO.AppointmentVO;
import VO.PatientVO;

public interface IPatientInfoDao {
	
	/**
	 * 환자정보를 변경 하는 메서드
	 * @param patientVo
	 * @return 1(변경 성공) 0(변경실패)
	 */
	public int updatePatientInfo(PatientVO patientVo);
	
	/**
	 * 환자정보 삭제하는 메서드
	 * @param pa_id
	 * @return 1(삭제 성공) 0(삭제 실패)
	 */
	public int deletePatientInfo(String pa_id);
	
	/**
	 * 환자의 이름으로 정보를 검색하는 메서드
	 * @param pa_name
	 * @return
	 * @throws RemoteException
	 */
	public List<PatientVO> serchPatientInfoName(String pa_name);
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<PatientVO> serchPatientInfoAll();
	
	/**
	 * 로그인한 환자님의 예약 정보 가져오기 ^^
	 * @param pa_id
	 * @return
	 * @throws RemoteException
	 */
	public List<AppointmentVO> searchPaAppt(String pa_id);
}
