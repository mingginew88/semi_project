package patient;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AppointmentVO;
import VO.PatientVO;
import VO.VisitclinicVO;

public class PatientInfoDao implements IPatientInfoDao {
	private static PatientInfoDao patientInfoDao = new PatientInfoDao();
	private SqlMapClient smc;
	
	private PatientInfoDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static PatientInfoDao getInterface() {
		if(patientInfoDao == null) {
			patientInfoDao = new PatientInfoDao();
		}
		return patientInfoDao;
	}
	
	
	@Override
	public int updatePatientInfo(PatientVO patientVo) {
		int cnt = 0;		
		try {
			cnt = smc.update("Patient.updatePatientInfo", patientVo);
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		} 		
		return cnt;
	}

	
	@Override
	public int deletePatientInfo(String pa_id) {
		int cnt = 0;
		try {
			cnt = smc.delete("Patient.deletePatientInfo", pa_id); 
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}		
		return cnt;
	}


	@Override
	public List<PatientVO> serchPatientInfoName(String pa_name) {
		List<PatientVO> result = new ArrayList<>();
		try {
			result = smc.queryForList("Patient.selectPatientInfoName",pa_name);
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}


	@Override
	public List<PatientVO> serchPatientInfoAll() {
		List<PatientVO> result = new ArrayList<>();
		try {
			result = smc.queryForList("Patient.selectPatientInfoAll");
		} catch (SQLException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}


	@Override
	public List<AppointmentVO> searchPaAppt(String pa_id) {
		List<AppointmentVO> apptList = new ArrayList<>();
		try {
			apptList = smc.queryForList("Patient.searchPaAppt", pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return apptList;
	}
	
	
	
}
