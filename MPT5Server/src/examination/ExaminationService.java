package examination;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.ExaminationVO;


public class ExaminationService extends UnicastRemoteObject implements IExaminationService {
	private IExaminationDao iExaminationDao;

	public ExaminationService() throws RemoteException{
		iExaminationDao = ExaminationDao.getInterface();
	}

	@Override
	public List<ExaminationVO> searchExaminationAll(String pa_id) throws RemoteException {
		return iExaminationDao.searchExaminationAll(pa_id);
	}
}
