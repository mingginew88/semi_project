package admin.memList;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.DoctorVO;
import VO.PatientVO;

public class MemListDao implements IMemListDao{
	
	private static MemListDao memListDao = new MemListDao();
	private SqlMapClient smc;
	
	private MemListDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MemListDao getInstance() {
		if(memListDao == null) {
			memListDao = new MemListDao();
		}
		return memListDao;
	}
	

	@Override
	public int insertPatient(PatientVO paVO) {
		int count = 0;
		try {
			Object obj = smc.insert("memberList.insertPatient", paVO);
			if(obj == null) {
				count = 1;
			}else {
				count = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			count = 0;
		}
		
		return count;
	}

	@Override
	public int insertDoctor(DoctorVO docVO) {
		int cnt = 0; 
		try {
			Object obj = smc.insert("memberList.insertDoctor", docVO);
			if(obj == null) {
				cnt =1;
			}else {
				cnt = 0 ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public int deleteMember(String paId) {
		int cnt = 0;
		try {
			cnt = smc.delete("memberList.deletePatient", paId);
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public int updatePatient(PatientVO paVO) {
		int cnt  = 0;
		try {
			cnt = smc.update("memberList.updatePatient", paVO);
			
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public int updateDoctor(DoctorVO docVO) {
		int cnt  = 0;
		try {
			cnt = smc.update("memberList.updateDoctor", docVO);
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public List<PatientVO> getSearchPatient(Map<String, String> searchMap) {
		List<PatientVO> paList = null;
		String str ="memberList.";
		
		if (searchMap.get("searchField").equals("pa_addr")) {
			str +="getAddrSearchPatient";
		}else if(searchMap.get("searchField").equals("pa_reg1")) {
			str +="getRegSearchPatient";
		}
		else {
			str +="getSearchPatient";
		}
		try {
			paList = smc.queryForList(str, searchMap);
			
		} catch (SQLException e) {
			e.printStackTrace();
			paList = null;
		}
		
		return paList;
	}

	@Override
	public List<DoctorVO> getSearchDoctor(Map<String, String> searchMap) {
		List<DoctorVO> docList = null;
		String str = "memberList.";
		
		if(searchMap.get("searchField").equals("doctor_addr")) {
			str += "getAddrSearchDoctor";
		}if(searchMap.get("searchField").equals("doctor_reg1")) {
			str +="getRegSearchDoctor";
		}
		else {
			str +="getSearchDoctor";
			
		}
		try {
			docList = smc.queryForList(str, searchMap);
		} catch (SQLException e) {
			e.printStackTrace();
			docList = null;
		}
		return docList;
	}

	@Override
	public int getCountPatient(String paId) {
		int cnt = 0;
		try {
			cnt  = (int) smc.queryForObject("memberList.getCountPatient", paId);
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public List<PatientVO> getAllPatient() {
		List<PatientVO> paList = null;
		try {
			paList = smc.queryForList("memberList.getAllPatient");
		} catch (SQLException e) {
			e.printStackTrace();
			paList = null;
		}
		return paList;
	}

	@Override
	public List<DoctorVO> getAllDoctor() {
		List<DoctorVO> docList = null;
		try {
			docList = smc.queryForList("memberList.getAllDoctor");
		} catch (SQLException e) {
			e.printStackTrace();
			docList = null;
		}
		return docList;
	}
	
	
	

}
