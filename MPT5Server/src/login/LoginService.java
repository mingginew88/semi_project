package login;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import VO.AdminisratorVO;
import VO.DoctorVO;
import VO.PatientVO;
import login.ILoginDao;
import login.LoginDao;

public class LoginService extends UnicastRemoteObject implements ILoginService{
	//private static LoginService loginService = new LoginService();
	private ILoginDao loginDao;
	
	public LoginService() throws RemoteException {
		loginDao = LoginDao.getInstance();
	}
	
//	public static LoginService getInstance() {
//		if(loginService==null) {
//			loginService = new LoginService();
//		}
//		return loginService;
//	}
	
	@Override
	public int loginChkPatient(PatientVO patientVo) throws RemoteException {
		return loginDao.loginChkPatient(patientVo);
	}

	@Override
	public PatientVO getPatientInfo(String pa_id) throws RemoteException {
		return loginDao.getPatientInfo(pa_id);
	}

	@Override
	public int checkPatient(String pa_id) throws RemoteException {
		return loginDao.checkPatient(pa_id);
	}

	@Override
	public int addPatient(PatientVO patientVo) throws RemoteException {
		return loginDao.addPatient(patientVo);
	}

	@Override
	public int loginChkDoctor(DoctorVO doctorVo) throws RemoteException {
		return loginDao.loginChkDoctor(doctorVo);
	}

	@Override
	public int loginChkAdmin(AdminisratorVO adminVo) throws RemoteException {
		return loginDao.loginChkAdmin(adminVo);
	}

	@Override
	public DoctorVO getDoctorInfo(int doctor_num) throws RemoteException {
		return loginDao.getDoctorInfo(doctor_num);
	}

	@Override
	public AdminisratorVO getAdminInfo(String admin_id) throws RemoteException {
		return loginDao.getAdminInfo(admin_id);
	}

	@Override
	public String getFindId(PatientVO patientVo) throws RemoteException {
		return loginDao.getFindId(patientVo);
	}

	@Override
	public String getFindPw(PatientVO patientVo) throws RemoteException {
		return loginDao.getFindPw(patientVo);
	}

	@Override
	public int checkPatientForId(PatientVO patientVo) throws RemoteException {
		return loginDao.checkPatientForId(patientVo);
	}

	@Override
	public int checkPatientForPw(PatientVO patientVo) throws RemoteException {
		return loginDao.checkPatientForPw(patientVo);
	}
	
	
	
	

}
