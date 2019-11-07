package doctor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.AppointmentVO;
import VO.DoctorVO;
import VO.MessageVO;
import VO.NoticeVO;
import VO.ScheduleVO;

public class DoctorMainService extends UnicastRemoteObject implements IDoctorMainService{
	private IDoctorMainDao dmDao;
	
	public DoctorMainService() throws RemoteException {
		dmDao = DoctoMainDao.getInstance();
	}

	@Override
	public List<MessageVO> getMsg(int doctor_num) throws RemoteException {
		return dmDao.getMsg(doctor_num);
	}

	@Override
	public List<ScheduleVO> getAllSche(int doctor_num) throws RemoteException {
		return dmDao.getAllSche(doctor_num);
	}

	@Override
	public List<AppointmentVO> getAllAppt(int doctor_num) throws RemoteException {
		return dmDao.getAllAppt(doctor_num);
	}

	@Override
	public List<NoticeVO> getAllNotice() throws RemoteException {
		return dmDao.getAllNotice();
	}

	@Override
	public String searchPaName(String pa_id) throws RemoteException {
		return dmDao.searchPaName(pa_id);
	}

	@Override
	public DoctorVO getDoctorInfo(int doctor_num) throws RemoteException {
		return dmDao.getDoctorInfo(doctor_num);
	}

	@Override
	public int updateDoctorInfo(DoctorVO doctorVo) throws RemoteException {
		return dmDao.updateDoctorInfo(doctorVo);
	}

	

}
