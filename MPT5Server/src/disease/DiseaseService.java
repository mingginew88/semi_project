package disease;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.DiseaseVO;

public class DiseaseService extends UnicastRemoteObject implements IDiseaseService{

	private IDiseaseDao iExaminationDao;

	public DiseaseService() throws RemoteException{
		iExaminationDao = DiseaseDao.getInterface();
	}
	
	@Override
	public List<DiseaseVO> searchDiseaseAll(String pa_id) throws RemoteException {
		return iExaminationDao.searchDiseaseAll(pa_id);
	}

}
