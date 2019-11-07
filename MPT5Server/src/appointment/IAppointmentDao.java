package appointment;

import java.rmi.RemoteException;
import java.util.List;

import VO.AppointmentVO;

public interface IAppointmentDao {

	/**
	 * 예약 정보를 업데이트하는 메서드
	 * @param apptmtvo
	 * @return 값은 불린
	 */
	public int updateAppointment(AppointmentVO apptmtvo);
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<AppointmentVO> searchAppointmentAll(String pa_id);
}
