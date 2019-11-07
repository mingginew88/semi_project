package appointment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.AppointmentVO;

public class AppointmentService extends UnicastRemoteObject implements IAppointmentService {
	private static IAppointmentDao iappointmentdao;

	public AppointmentService() throws RemoteException{
		iappointmentdao = AppointmentDao.getInterface();
	}

	@Override
	public int updateAppointment(AppointmentVO apptmtvo) throws RemoteException {
		return iappointmentdao.updateAppointment(apptmtvo);
	}

	@Override
	public List<AppointmentVO> searchAppointmentAll(String pa_id) throws RemoteException {
		return iappointmentdao.searchAppointmentAll(pa_id);
	}

	
	
	
}
