package appointment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.AppointmentVO;

public interface IAppointmentService extends Remote{
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int updateAppointment(AppointmentVO apptmtvo) throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<AppointmentVO> searchAppointmentAll(String pa_id) throws RemoteException;
}
