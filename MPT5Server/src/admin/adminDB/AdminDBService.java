package admin.adminDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import VO.DoctorVO;
import VO.MedicineVO;
import VO.MessageVO;
import VO.ScheduleVO;
import VO.VisitclinicVO;

public class AdminDBService extends UnicastRemoteObject implements IAdminDBService {
	
	private IAdminDBDao adminDBDao;
	public AdminDBService() throws RemoteException {
		adminDBDao = AdminDBDao.getInstance(); 
	}
	@Override
	public List<MedicineVO> getAllMedi() throws RemoteException {
		return adminDBDao.getAllMedi();
	}
	@Override
	public List<MedicineVO> searchMedi(String mediName) throws RemoteException {
		return adminDBDao.searchMedi(mediName);
	}
	@Override
	public int addMedi(MedicineVO medi) throws RemoteException {
		return adminDBDao.addMedi(medi);
	}
	@Override
	public int deleteMedi(int mediCode) throws RemoteException {
		return adminDBDao.deleteMedi(mediCode);
	}
	@Override
	public List<VisitclinicVO> getVCList() throws RemoteException {
		return adminDBDao.getVCList();
	}
	@Override
	public List<VisitclinicVO> searchVs(Map<String, String> searchMap) throws RemoteException {
		return adminDBDao.searchVs(searchMap);
	}
	
	@Override
	public List<MessageVO> getMsgList() throws RemoteException {
		return adminDBDao.getMsgList();
	}
	@Override
	public List<MessageVO> searchSdMsg(String sender) throws RemoteException {
		return adminDBDao.searchSdMsg(sender);
	}
	@Override
	public List<MessageVO> searchRcMsg(String receiver) throws RemoteException {
		return adminDBDao.searchRcMsg(receiver);
	}
	@Override
	public int deleteMsg(int msgNum) throws RemoteException {
		return adminDBDao.deleteMsg(msgNum);
	}
	@Override
	public String getDoctorName(int doctorNum) throws RemoteException {
		
		return adminDBDao.getDoctorName(doctorNum);
	}
	@Override
	public List<ScheduleVO> getScheList()  throws RemoteException{
		return adminDBDao.getScheList();
	}
	@Override
	public List<ScheduleVO> searchDocSche(String doctor_name)  throws RemoteException{
		return adminDBDao.searchDocSche(doctor_name);
	}		
			

}
