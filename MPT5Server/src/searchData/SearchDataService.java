package searchData;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Map;

import VO.MedicineVO;
import VO.PatientVO;
import VO.VisitclinicVO;
import searchData.SearchDataDao;

public class SearchDataService extends UnicastRemoteObject implements ISearchDataService{
	private SearchDataDao sdDao;
	
	public SearchDataService() throws RemoteException{
		sdDao = SearchDataDao.getInstance();
	}

	@Override
	public List<PatientVO> getAllPa(int doctor_num) throws RemoteException{
		return sdDao.getAllPa(doctor_num);
	}

	@Override
	public List<MedicineVO> getAllMedi() throws RemoteException{
		return sdDao.getAllMedi();
	}

	@Override
	public Date getPaRecentVSTClinicDate(String pa_id)throws RemoteException {
		return sdDao.getPaRecentVSTClinicDate(pa_id);
	}

	@Override
	public List<PatientVO> searchPa(String pa_name)throws RemoteException {
		return sdDao.searchPa(pa_name);
	}

	@Override
	public List<MedicineVO> searchMedi(String medi_name)throws RemoteException {
		return sdDao.searchMedi(medi_name);
	}

	@Override
	public List<VisitclinicVO> getPaVSTClinicDate(Map map) throws RemoteException {
		return sdDao.getPaVSTClinicDate(map);
	}
	
	
}
