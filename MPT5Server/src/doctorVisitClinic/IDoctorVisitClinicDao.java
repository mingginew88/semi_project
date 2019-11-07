package doctorVisitClinic;

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

public interface IDoctorVisitClinicDao {
	/**
	 * 로그인한 의사의 당일 예약리스트 불러오는 메서드
	 * @param doctor_num
	 * @return 예약 리스트
	 * @
	 */
	public List<AppointmentVO> getAllAppoint(int doctor_num) ;
	
	/**
	 * 환자 Id에 해당하는 환자 VO 불러오는 메서드
	 * @param pa_id
	 * @return 환자 VO
	 * @
	 */
	public PatientVO getPaInfo(String pa_id) ;
	
	/**
	 * 환자 Id에 해당하는 검사VO 불러오는 메서드
	 * @param pa_id
	 * @return 검사VO
	 * @
	 */
	public List<ExaminationVO> getExInfo(String pa_id) ;
	
	/**
	 * 의약품코드/의약품명으로 검색했을때 해당하는 의약품VO리스트 불러오는 메서드
	 * @param data
	 * @return 의약품VO
	 * @
	 */
	public List<MedicineVO> getMedi(Map<String, String> data) ;
	
	/**
	 * 환자 Id에 해당하는 질병 VO불러오는 메서드
	 * @param pa_id
	 * @return 질병VO
	 * @
	 */
	public List<DiseaseVO> getDis(String pa_id) ;
	
	/**
	 * VisitClinic insert
	 * @param vc
	 * @return
	 */
	public int insertVisitClinic(VisitclinicVO vc) ;
	
	/**
	 * Disease insert
	 * @param dis
	 * @return
	 */
	public int insertDisease(DiseaseVO dis) ;
	
	/**
	 * Prescription insert
	 * @param presc
	 * @return
	 */
	public int insertPrescription(PrescriptionVO presc) ;
	
	/**
	 * Visitclinic num 제일 큰값
	 * @return
	 * @
	 */
	public int maxVCNum() ;
	
	/**
	 * Disease num 제일 큰값
	 * @return
	 * @
	 */
	public int maxDisNum() ;
	
	/**
	 * Examination insert
	 * @return
	 * @
	 */
	public int insertExam(ExaminationVO exam) ;
	
	/**
	 * 예약리스트의 환자가 진단을 했는지 검사하는 메서드
	 * @param appt_num
	 * @return 진단기록이 있으면 1 없으면 0
	 * @
	 */
	public boolean searchApptVC(int appt_num) ;
	
	/**
	 * 예약리스트에 있지만 이미 진단 내용이 있을 경우 진단 내용을 가져오는 메서드
	 * @param appt_num
	 * @return
	 * @
	 */
	public VisitclinicVO getPaVC(int appt_num) ;
	
	/**
	 * 예약리스트에 있지만 이미 진단 내용이 있을 경우 해당 진단서의 처방 내용을 가져오는 메서드
	 * @param clinic_num
	 * @return
	 * @
	 */
	public PrescriptionVO getPaPresc(int clinic_num) ;
	
	/**
	 * 예약리스트에 있지만 이미 진단 내용이 있을 경우 해당 진단 관련 질병 내용을 가져오는 메서드
	 * @param clinic_num
	 * @return
	 * @
	 */
	public DiseaseVO getPaDisease(int clinic_num) ;
	
	/**
	 * 예약리스트에 있지만 진단 내용이 있을 경우 해당 진단 관련 검사 내용을 가져오는 메서드
	 * @param clinic_num
	 * @return
	 * @throws RemoteException
	 */
	public ExaminationVO getPaExam(int clinic_num);
	
	/**
	 * 진단 후에 또 저장했을때 VisitClinic update하는 메서드..
	 * @param search
	 * @return
	 * @
	 */
	public int updateVC(VisitclinicVO vc) ;
	
	/**
	 * 진단 후에 또 저장했을때 Prescription update하는 메서드
	 * @param search
	 * @return
	 * @
	 */
	public int updatePresc(PrescriptionVO presc) ;
	
	/**
	 * 진단 후에 또 저장했을때 Disease update하는 메서드
	 * @param search
	 * @return
	 * @
	 */
	public int updateDisease(DiseaseVO dis) ;
	
	/**
	 * 진단 후에 또 저장했을때 Examination update하는 메서드
	 * @param search
	 * @return
	 * @
	 */
	public int updateExam(ExaminationVO exam) ;


}
