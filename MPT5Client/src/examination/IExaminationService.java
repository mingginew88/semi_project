package examination;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.ExaminationVO;


public interface IExaminationService extends Remote{

	public List<ExaminationVO> searchExaminationAll(String pa_id) throws RemoteException;
	
}
