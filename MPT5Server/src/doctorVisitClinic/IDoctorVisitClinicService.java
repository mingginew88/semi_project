package doctorVisitClinic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import VO.AppointmentVO;
import VO.DiseaseVO;
import VO.ExaminationVO;
import VO.MedicineVO;
import VO.PatientVO;
import VO.PrescriptionVO;
import VO.VisitclinicVO;

public interface IDoctorVisitClinicService extends Remote{
	/**
	 * 로그인한 의사의 당일 예약리스트 불러오는 메서드
	 * @param doctor_num
	 * @return 예약 리스트
	 * @throws RemoteException
	 */
	public List<AppointmentVO> getAllAppoint(int doctor_num) throws RemoteException;
	
	/**
	 * 환자 Id에 해당하는 환자 VO 불러오는 메서드
	 * @param pa_id
	 * @return 환자 VO
	 * @throws RemoteException
	 */
	public PatientVO getPaInfo(String pa_id) throws RemoteException;
	
	/**
	 * 환자 Id에 해당하는 검사VO 불러오는 메서드
	 * @param pa_id
	 * @return 검사VO
	 * @throws RemoteException
	 */
	public List<ExaminationVO> getExInfo(String pa_id) throws RemoteException;
	
	/**
	 * 의약품코드/의약품명으로 검색했을때 해당하는 의약품VO리스트 불러오는 메서드
	 * @param data
	 * @return 의약품VO
	 * @throws RemoteException
	 */
	public List<MedicineVO> getMedi(Map<String, String> data) throws RemoteException;
	
	/**
	 * 환자 Id에 해당하는 질병 VO불러오는 메서드
	 * @param pa_id
	 * @return 질병VO
	 * @throws RemoteException
	 */
	public List<DiseaseVO> getDis(String pa_id) throws RemoteException;
	
	/**
	 * VisitClinic insert
	 * @param vc
	 * @return
	 */
	public int insertVisitClinic(VisitclinicVO vc) throws RemoteException;
	
	/**
	 * Disease insert
	 * @param dis
	 * @return
	 */
	public int insertDisease(DiseaseVO dis) throws RemoteException;
	
	/**
	 * Prescription insert
	 * @param presc
	 * @return
	 */
	public int insertPrescription(PrescriptionVO presc) throws RemoteException;
	
	/**
	 * Visitclinic num 제일 큰값
	 * @return
	 * @throws RemoteException
	 */
	public int maxVCNum() throws RemoteException;
	
	/**
	 * Disease num 제일 큰값
	 * @return
	 * @throws RemoteException
	 */
	public int maxDisNum() throws RemoteException;
	
	/**
	 * Examination insert
	 * @return
	 * @throws RemoteException
	 */
	public int insertExam(ExaminationVO exam) throws RemoteException;
	
	/**
	 * 예약리스트의 환자가 진단을 했는지 검사하는 메서드
	 * @param appt_num
	 * @return 진단기록이 있으면 1 없으면 0
	 * @throws RemoteException
	 */
	public boolean searchApptVC(int appt_num) throws RemoteException;
	
	/**
	 * 예약리스트에 있지만 이미 진단 내용이 있을 경우 진단 내용을 가져오는 메서드
	 * @param appt_num
	 * @return
	 * @throws RemoteException
	 */
	public VisitclinicVO getPaVC(int appt_num) throws RemoteException;
	
	/**
	 * 예약리스트에 있지만 이미 진단 내용이 있을 경우 해당 진단서의 처방 내용을 가져오는 메서드
	 * @param clinic_num
	 * @return
	 * @throws RemoteException
	 */
	public PrescriptionVO getPaPresc(int clinic_num) throws RemoteException;
	
	/**
	 * 예약리스트에 있지만 이미 진단 내용이 있을 경우 해당 진단 관련 질병 내용을 가져오는 메서드
	 * @param clinic_num
	 * @return
	 * @throws RemoteException
	 */
	public DiseaseVO getPaDisease(int clinic_num) throws RemoteException;
	
	/**
	 * 예약리스트에 있지만 진단 내용이 있을 경우 해당 진단 관련 검사 내용을 가져오는 메서드
	 * @param clinic_num
	 * @return
	 * @throws RemoteException
	 */
	public ExaminationVO getPaExam(int clinic_num) throws RemoteException;
	
	/**
	 * 진단 후에 또 저장했을때 VisitClinic update하는 메서드..
	 * @param vc
	 * @return
	 * @throws RemoteException
	 */
	public int updateVC(VisitclinicVO vc) throws RemoteException;
	
	/**
	 * 진단 후에 또 저장했을때 Prescription update하는 메서드
	 * @param presc
	 * @return
	 * @throws RemoteException
	 */
	public int updatePresc(PrescriptionVO presc) throws RemoteException;
	
	/**
	 * 진단 후에 또 저장했을때 Disease update하는 메서드
	 * @param dis
	 * @return
	 * @throws RemoteException
	 */
	public int updateDisease(DiseaseVO dis) throws RemoteException;
	
	/**
	 * 진단 후에 또 저장했을때 Examination update하는 메서드
	 * @param exam
	 * @return
	 * @throws RemoteException
	 */
	public int updateExam(ExaminationVO exam) throws RemoteException;

}
