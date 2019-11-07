package disease;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.DiseaseVO;


public interface IDiseaseService extends Remote{
	
	/**
	 * 
	 * @param pa_id
	 * @return
	 * @throws RemoteException
	 */
	public List<DiseaseVO> searchDiseaseAll(String pa_id) throws RemoteException;
}
