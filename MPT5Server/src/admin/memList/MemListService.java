package admin.memList;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import VO.DoctorVO;
import VO.PatientVO;
import admin.memList.IMemListDao;
import admin.memList.MemListDao;

public class MemListService extends UnicastRemoteObject implements IMemListService {
	
	
	private IMemListDao memListDao;
	
	public MemListService() throws RemoteException {
		memListDao = MemListDao.getInstance();
				
	}
	
	
	
	@Override
	public int insertPatient(PatientVO paVO)throws RemoteException {
		return memListDao.insertPatient(paVO);
	}

	@Override
	public int insertDoctor(DoctorVO docVO)throws RemoteException {
		return memListDao.insertDoctor(docVO);
	}

	@Override
	public int deleteMember(String paId)throws RemoteException {
		return memListDao.deleteMember(paId);
	}

	@Override
	public int updatePatient(PatientVO paVO)throws RemoteException {
		return memListDao.updatePatient(paVO);
	}

	@Override
	public int updateDoctor(DoctorVO docVO)throws RemoteException {
		return memListDao.updateDoctor(docVO);
	}

	@Override
	public int getCountPatient(String paId)throws RemoteException {
		return memListDao.getCountPatient(paId);
	}

	@Override
	public List<PatientVO> getAllPatient()throws RemoteException {
		return memListDao.getAllPatient();
	}

	@Override
	public List<DoctorVO> getAllDoctor()throws RemoteException {
		return memListDao.getAllDoctor();
	}

	@Override
	public List<PatientVO> getSearchPatient(Map<String, String> searchMap)throws RemoteException {
		return memListDao.getSearchPatient(searchMap);
	}

	@Override
	public List<DoctorVO> getSearchDoctor(Map<String, String> searchMap) throws RemoteException {
		return memListDao.getSearchDoctor(searchMap);
	}

}
