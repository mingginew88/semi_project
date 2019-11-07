package searchData;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import VO.MedicineVO;
import VO.PatientVO;
import VO.VisitclinicVO;

public interface ISearchDataService extends Remote{
	/**
	 * 환자 정보 다 가져오기
	 * @return 환자 리스트
	 */
	public List<PatientVO> getAllPa(int doctor_num) throws RemoteException;
	
	/**
	 * 의약품 정보 다 가져오기
	 * @return 의약품 리스트
	 */
	public List<MedicineVO> getAllMedi() throws RemoteException;
	
	/**
	 * 환자의 제일 최근 진료일 가져오기
	 * @param pa_id
	 * @return 날짜
	 */
	public Date getPaRecentVSTClinicDate(String pa_id) throws RemoteException;
	
	/**
	 * 검색한 환자명에 해당하는 환자 리스트 가져오기
	 * @param pa_name
	 * @return
	 */
	public List<PatientVO> searchPa(String pa_name) throws RemoteException;
	
	/**
	 * 검색한 의약품명에 해당하는 의약품 리스트 가져오기
	 * @param medi_name
	 * @return
	 */
	public List<MedicineVO> searchMedi(String medi_name) throws RemoteException;
	
	/**
	 * 의사가 방문진료한 환자의 진료 VO
	 * @param map
	 * @return VSTClinic_date
	 * @throws RemoteException
	 */
	public List<VisitclinicVO> getPaVSTClinicDate(Map map) throws RemoteException;
	
	
	
	
	
}
