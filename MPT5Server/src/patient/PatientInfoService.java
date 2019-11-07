package patient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.AppointmentVO;
import VO.PatientVO;

public class PatientInfoService extends UnicastRemoteObject implements IPatientInfoService {
	private static IPatientInfoDao patientInfoDao;
	
	public PatientInfoService() throws RemoteException{
		patientInfoDao = PatientInfoDao.getInterface();
	}

	@Override
	public int updatePatientInfo(PatientVO patientVo) throws RemoteException{
		return patientInfoDao.updatePatientInfo(patientVo);
	}

	@Override
	public int deletePatientInfo(String pa_id) throws RemoteException{
		return patientInfoDao.deletePatientInfo(pa_id);
	}

	@Override
	public List<PatientVO> serchPatientInfoName(String pa_name) throws RemoteException {
		return patientInfoDao.serchPatientInfoName(pa_name);
	}

	@Override
	public List<PatientVO> serchPatientInfoAll() throws RemoteException {
		return patientInfoDao.serchPatientInfoAll();
	}

	@Override
	public List<AppointmentVO> searchPaAppt(String pa_id) throws RemoteException {
		return patientInfoDao.searchPaAppt(pa_id);
	}
	
	
}
