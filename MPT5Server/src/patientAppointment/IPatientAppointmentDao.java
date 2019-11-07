package patientAppointment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.AppointmentVO;
import VO.DepartmentVO;
import VO.DoctorVO;

public interface IPatientAppointmentDao {
	
	public int addAppoint(AppointmentVO apptVO);
	
	public List<DepartmentVO> getDeptList();
	
	public List<DoctorVO> getDoctorList(int dept_num);
	
	public List<String> getTime(int doctor_num);
	
	public String getPaName(String pa_id);
	
	
}
