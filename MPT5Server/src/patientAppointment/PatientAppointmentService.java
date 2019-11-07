package patientAppointment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.AppointmentVO;
import VO.DepartmentVO;
import VO.DoctorVO;

public class PatientAppointmentService  extends UnicastRemoteObject implements IPatientAppointmentService {
	private IPatientAppointmentDao paApptDao;

	public PatientAppointmentService() throws RemoteException {
		paApptDao= PatientAppointmentDao.getInstance();
	}

	@Override
	public int addAppoint(AppointmentVO apptVO) throws RemoteException {
		return paApptDao.addAppoint(apptVO);
	}

	@Override
	public List<DepartmentVO> getDeptList() throws RemoteException {
		return paApptDao.getDeptList();
	}

	@Override
	public List<DoctorVO> getDoctorList(int dept_num) throws RemoteException {
		return paApptDao.getDoctorList(dept_num);
	}

	@Override
	public List<String> getTime(int doctor_num) throws RemoteException {
		return paApptDao.getTime(doctor_num);
	}

	@Override
	public String getPaName(String pa_id) throws RemoteException {
		return paApptDao.getPaName(pa_id);
	}
	

}
