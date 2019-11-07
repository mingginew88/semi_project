package admin.memList;

import java.util.List;
import java.util.Map;

import VO.DoctorVO;
import VO.PatientVO;

public interface IMemListDao {
	
	/**
	 * 환자를 추가하는 메서드
	 * @param paVO
	 * @return 
	 */
	public int insertPatient(PatientVO paVO);
	
	/**
	 * 의사를 추가하는 메서드
	 * @param docVO
	 * @return
	 */
	public int insertDoctor(DoctorVO docVO);
	
	/**
	 * 환자를 삭제하는 메서드
	 * @param paId
	 * @return 
	 */
	public int deleteMember(String paId);
	
	/**
	 * 환자 정보를 수정하는 메서드
	 * @param paVO
	 * @return
	 */
	public int updatePatient(PatientVO paVO);
	
	/**
	 * 의사정보를 수정하는 메서드
	 * @param docVO
	 * @return
	 */
	public int updateDoctor(DoctorVO docVO);
	
	/**
	 * 환자 아이디 중복을 확인하기 위한 메서드
	 * @param paId
	 * @return
	 */
	public int getCountPatient(String paId);
	
	/**
	 * 전체 환자 리스트를 불러오는 메서드
	 * @return
	 */
	public List<PatientVO> getAllPatient();
	
	/**
	 * 전체 의사 리스트를 불러오는 메서드
	 * @return
	 */
	public List<DoctorVO> getAllDoctor();
	
	/**
	 * 환자를 검색하는 메서드
	 * @param searchMap
	 * @return
	 */
	public List<PatientVO> getSearchPatient(Map<String, String> searchMap);
	
	/**
	 * 의사를 검색하는 메서드
	 * @param searchMap
	 * @return
	 */
	public List<DoctorVO> getSearchDoctor(Map<String, String> searchMap);
}
