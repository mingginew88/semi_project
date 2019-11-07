package admin.adminDB;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.ListDataEvent;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.DoctorVO;
import VO.MedicineVO;
import VO.MessageVO;
import VO.ScheduleVO;
import VO.VisitclinicVO;

public class AdminDBDao implements IAdminDBDao {
	
	private static AdminDBDao adminDBDao = new AdminDBDao();
	private SqlMapClient smc;
	
	private AdminDBDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AdminDBDao getInstance() {
		if(adminDBDao == null) {
			adminDBDao = new AdminDBDao();
		}
		return adminDBDao;
	}

	@Override
	public List<MedicineVO> getAllMedi() throws RemoteException {
		List<MedicineVO> mediList = null;
		try {
			mediList = smc.queryForList("adminDB.getAllMedi");
		} catch (SQLException e) {
			e.printStackTrace();
			mediList = null;
		}
		return mediList;
		
	}

	@Override
	public List<MedicineVO> searchMedi(String mediName) throws RemoteException {
		List<MedicineVO> mediList = null;
		try {
			mediList =  smc.queryForList("adminDB.searchMedi", mediName);
		} catch (SQLException e) {
			e.printStackTrace();
			mediList = null;
		}
		return mediList;
	}

	@Override
	public int addMedi(MedicineVO medi) throws RemoteException {
		int cnt = 0;
		try {
			Object obj = smc.insert("adminDB.addMedi", medi);
			if(obj == null) {
				cnt = 1;
			}else {
				cnt = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteMedi(int mediCode) throws RemoteException {
		int cnt = 0; 
		try {
			cnt = smc.delete("adminDB.deleteMedi", mediCode);
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public List<VisitclinicVO> getVCList() throws RemoteException {
		List<VisitclinicVO> vstList;
		try {
			vstList = smc.queryForList("adminDB.getVCList");
		} catch (SQLException e) {
			e.printStackTrace();
			vstList = null;
		}
		return vstList;
	}

	@Override
	public List<VisitclinicVO> searchVs(Map<String, String> searchMap) throws RemoteException {
		List<VisitclinicVO> vsList = null;
		String str = "adminDB.";
		if(searchMap.get("searchField").equals("pa_id")) {
			str += "searchPaVs";
		}else if(searchMap.get("searchField").equals("doctor_num")) {
			str += "searchDocvs";
		}else if(searchMap.get("searchField").equals("vstclinic_cont")) {
			str +="searchContVs";
		}else {
			str +="searchDateVS";
		}
		
		try {
			vsList = smc.queryForList(str, searchMap);
		} catch (SQLException e) {
			e.printStackTrace();
			vsList = null;
		}
		return vsList;
	}

	

	@Override
	public List<MessageVO> getMsgList() throws RemoteException {
		List<MessageVO> msgList = null;
		try {
			msgList = smc.queryForList("adminDB.getMsgList");
			
		} catch (SQLException e) {
			e.printStackTrace();
			msgList = null;
		}
		return msgList;
	}
	

	@Override
	public List<MessageVO> searchSdMsg(String sender) throws RemoteException {
		List<MessageVO> msgList = null;
		try {
			msgList = smc.queryForList("adminDB.searchSdMsg", sender);
			
		} catch (SQLException e) {
			e.printStackTrace();
			msgList = null;
		}
		return msgList;
	}

	@Override
	public List<MessageVO> searchRcMsg(String receiver) throws RemoteException {
		List<MessageVO> msgList = null;
		try {
			msgList = smc.queryForList("adminDB.searchRcMsg", receiver);
		} catch (SQLException e) {
			e.printStackTrace();
			msgList =null;
		}
		return msgList;
	}

	@Override
	public int deleteMsg(int msgNum) throws RemoteException {
		int cnt = 0; 
		try {
			cnt = smc.delete("adminDB.deleteMsg", msgNum);
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public String getDoctorName(int doctorNum) throws RemoteException {
			String docName="";
		try {
			docName = (String) smc.queryForObject("adminDB.getdoctorName", doctorNum);
			
		} catch (SQLException e) {
			e.printStackTrace();
			docName="";
		}
		return docName;
	}

	@Override
	public List<ScheduleVO> getScheList() {
		List<ScheduleVO> scheList = null;
		try {
			scheList = smc.queryForList("adminDB.getScheList");
		} catch (SQLException e) {
			e.printStackTrace();
			scheList = null;
		}
		return scheList;
	}

	@Override
	public List<ScheduleVO> searchDocSche(String doctor_name) {
		List<ScheduleVO> searchList = null;
		try {
			searchList = smc.queryForList("adminDB.searchDocSche", doctor_name);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
