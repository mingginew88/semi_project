package patientAppointment;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AppointmentVO;
import VO.DepartmentVO;
import VO.DoctorVO;

public class PatientAppointmentDao implements IPatientAppointmentDao {
	private static PatientAppointmentDao paApptDao;
	private SqlMapClient smc;
	
	private PatientAppointmentDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PatientAppointmentDao getInstance() {
		if(paApptDao == null) {
			paApptDao = new PatientAppointmentDao();
		}
		return paApptDao;
	}
	
	@Override
	public int addAppoint(AppointmentVO apptVO) {
		int cnt = 0; 
		try {
			Object obj = smc.insert("patientAppointment.addVSAppoint",apptVO);
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
	public List<DepartmentVO> getDeptList() {
		List<DepartmentVO> deptList = null;
		try {
			deptList = smc.queryForList("patientAppointment.getDeptList");
		} catch (SQLException e) {
			e.printStackTrace();
			deptList = null;
		}
		return deptList;
	}

	@Override
	public List<DoctorVO> getDoctorList(int dept_num) {
		List<DoctorVO> docList = null;
		try {
			docList = smc.queryForList("patientAppointment.getDocList", dept_num);
			
		} catch (SQLException e) {
			e.printStackTrace();
			docList = null;
		}
		return docList;
	}

	@Override
	public List<String> getTime(int doctor_num) {
		List<String> time = null;
		try {  
			time = smc.queryForList("patientAppointment.getDate", doctor_num); 
			
		} catch (SQLException e) {
			e.printStackTrace();
			time = null;
		}
		return time;
	}


	@Override
	public String getPaName(String pa_id) {
		String paName ="";
		try {
			paName = (String) smc.queryForObject("patientAppointment.getPaName", pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
			paName=null;
		}
		return paName;
	}

}
