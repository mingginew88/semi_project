package patientAppointment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.AppointmentVO;
import VO.DepartmentVO;
import VO.DoctorVO;

public interface IPatientAppointmentService extends Remote {

	public int addAppoint(AppointmentVO apptVO) throws RemoteException;

	public List<DepartmentVO> getDeptList() throws RemoteException;

	public List<DoctorVO> getDoctorList(int dept_num) throws RemoteException;
	
	public List<String> getTime(int doctor_num) throws RemoteException;
	
	public String getPaName(String pa_id) throws RemoteException;

	
}
