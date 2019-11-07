package login;


import java.rmi.Remote;
import java.rmi.RemoteException;

import VO.AdminisratorVO;
import VO.DoctorVO;
import VO.PatientVO;

public interface ILoginService extends Remote {

	/**
	 * 환자의 로그인 확인을 위한 메서드
	 * 
	 * @param patientVo(환자
	 *            아이디와 환자 비밀번호)
	 * @return 1(로그인가능) 또는 0(로그인불가)
	 */
	public int loginChkPatient(PatientVO patientVo) throws RemoteException;

	/**
	 * 환자ID에 맞는 환자 모든 정보 가져오는 메서드
	 * 
	 * @param pa_id
	 * @return patientVo
	 */
	public PatientVO getPatientInfo(String pa_id) throws RemoteException;

	/**
	 * 회원가입 시 아이디 중복 유무 체크하기위한 메서드
	 * 
	 * @param pa_id
	 * @return 1(중복) 또는 0(가능)
	 */
	public int checkPatient(String pa_id) throws RemoteException;

	/**
	 * 환자 회원가입
	 * 
	 * @param patientVo
	 * @return 1(회원가입성공) 또는 0(회원가입실패)
	 */
	public int addPatient(PatientVO patientVo) throws RemoteException;

	/**
	 * 의사의 로그인 확인을 위한 메서드
	 * 
	 * @param doctorVo
	 * @return 1(로그인가능) 또는 0(로그인불가)
	 */
	public int loginChkDoctor(DoctorVO doctorVo) throws RemoteException;

	/**
	 * 관리자의 로그인
	 * 
	 * @param adminVo
	 * @return 1(로그인가능) 또는 0(로그인불가)
	 */
	public int loginChkAdmin(AdminisratorVO adminVo) throws RemoteException;

	/**
	 * 의사번호에 맞는 의사정보 가져오는 메서드
	 * 
	 * @param doctor_num
	 * @return doctorVo
	 */
	public DoctorVO getDoctorInfo(int doctor_num) throws RemoteException;

	/**
	 * 관리자 아이디로 관지라 정보 호출하는 메서드
	 * 
	 * @param admin_id
	 * @return adminVo
	 */
	public AdminisratorVO getAdminInfo(String admin_id) throws RemoteException;

	/**
	 * 아이디 찾기
	 * 
	 * @param patientVo
	 *            (이름, 주민번호앞자리)
	 * @return pa_id
	 */
	public String getFindId(PatientVO patientVo) throws RemoteException;

	/**
	 * 비밀번호 찾기
	 * 
	 * @param patientVo(아이디,
	 *            이름, 주민번호앞자리)
	 * @return pa_pw
	 */
	public String getFindPw(PatientVO patientVo) throws RemoteException;

	/**
	 * ID 찾기 위한 인증
	 * 
	 * @param patientVo
	 * @return 1(인증성공) 0(인증실패)
	 * @throws RemoteException
	 */
	public int checkPatientForId(PatientVO patientVo) throws RemoteException;

	/**
	 * 비밀번호 찾기 위한 인증
	 * 
	 * @param patientVo
	 * @return 1(인증성공) 0(인증실패)
	 * @throws RemoteException
	 */
	public int checkPatientForPw(PatientVO patientVo) throws RemoteException;
	
	


}
