package login;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AdminisratorVO;
import VO.DoctorVO;
import VO.PatientVO;

public class LoginDao implements ILoginDao{
	private static LoginDao loginDao = new LoginDao();
	private SqlMapClient smc;
	
	private LoginDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static LoginDao getInstance() {
		if(loginDao==null) {
			loginDao = new LoginDao();
		}
		return loginDao;
	}
	
	
	@Override
	public int loginChkPatient(PatientVO patientVo) {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("Login.loginChkPatient", patientVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public PatientVO getPatientInfo(String pa_id) {
		PatientVO patientVo = null;
		try {
			patientVo = (PatientVO) smc.queryForObject("Login.getPatientInfo", pa_id);
		} catch (SQLException e) {
			patientVo = null;
			e.printStackTrace();
		}
		return patientVo;
	}

	@Override
	public int checkPatient(String pa_id) {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("Login.checkPatient", pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public int addPatient(PatientVO patientVo) {
		Object obj = null;

		int cnt = 0;
		try {
			obj = smc.insert("Login.addPatient", patientVo);
			if (obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int loginChkDoctor(DoctorVO doctorVo) {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("Login.loginChkDoctor", doctorVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// System.out.println("cnt : " + cnt);
		return cnt;
	}

	@Override
	public int loginChkAdmin(AdminisratorVO adminVo) {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("Login.loginChkAdmin", adminVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public DoctorVO getDoctorInfo(int doctor_num) {
		DoctorVO doctorVo = null;
		try {
			doctorVo = (DoctorVO) smc.queryForObject("Login.getDoctorInfo", doctor_num);
		} catch (SQLException e) {
			doctorVo = null;
			e.printStackTrace();
		}
		return doctorVo;
	}

	@Override
	public AdminisratorVO getAdminInfo(String admin_id) {
		AdminisratorVO adminVo = null;
		try {
			adminVo = (AdminisratorVO) smc.queryForObject("Login.getAdminInfo", admin_id);
		} catch (SQLException e) {
			adminVo = null;
			e.printStackTrace();
		}
		return adminVo;
	}

	@Override
	public String getFindId(PatientVO patientVo) {
		String pa_id = "";
		try {
			pa_id = (String) smc.queryForObject("Login.getFindId", patientVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pa_id;
	}

	@Override
	public String getFindPw(PatientVO patientVo) {
		String pa_pw = "";
		try {
			pa_pw = (String) smc.queryForObject("Login.getFindPw", patientVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pa_pw;
	}

	@Override
	public int checkPatientForId(PatientVO patientVo) throws RemoteException {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("Login.checkPatientForId", patientVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int checkPatientForPw(PatientVO patientVo) throws RemoteException {
		int cnt = 0;
		try {
			cnt = (int) smc.queryForObject("Login.checkPatientForPw", patientVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	
	

	
	
	
	
	

}
