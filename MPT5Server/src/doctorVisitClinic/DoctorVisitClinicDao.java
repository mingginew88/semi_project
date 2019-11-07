package doctorVisitClinic;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AppointmentVO;
import VO.DiseaseVO;
import VO.ExaminationVO;
import VO.MedicineVO;
import VO.PatientVO;
import VO.PrescriptionVO;
import VO.VisitclinicVO;

public class DoctorVisitClinicDao implements IDoctorVisitClinicDao{
	private static DoctorVisitClinicDao dcDao;
	private SqlMapClient smc;
	
	private DoctorVisitClinicDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DoctorVisitClinicDao getInstance() {
		return dcDao==null? dcDao=new DoctorVisitClinicDao():dcDao;
	}

	@Override
	public List<AppointmentVO> getAllAppoint(int doctor_num) {
		List<AppointmentVO> apList = null;
		try {
			apList = smc.queryForList("doctorVisitClinic.getAllAppoint", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return apList;
	}

	@Override
	public PatientVO getPaInfo(String pa_id) {
		PatientVO patient = null;
		try {
			patient = (PatientVO) smc.queryForObject("doctorVisitClinic.getPaInfo", pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}

	@Override
	public List<ExaminationVO> getExInfo(String pa_id) {
		List<ExaminationVO> examination = null;
		try {
			examination = (List<ExaminationVO>) smc.queryForList("doctorVisitClinic.getPaEx", pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examination;
	}

	@Override
	public List<MedicineVO> getMedi(Map<String, String> data){
		List<MedicineVO> mediList = null;
		try {
			mediList = smc.queryForList("doctorVisitClinic.getMedi", data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mediList;
	}

	@Override
	public List<DiseaseVO> getDis(String pa_id) {
		List<DiseaseVO> disList = null;
		try {
			disList = smc.queryForList("doctorVisitClinic.getDis", pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return disList;
	}

	@Override
	public int insertVisitClinic(VisitclinicVO vc) {
		int cnt = 0;
		System.out.println(vc.getVstclinic_cont());
		try {
			if(smc.insert("doctorVisitClinic.insertVisitClinic", vc)==null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int insertDisease(DiseaseVO dis) {
		int cnt = 0;
		try {
			if(smc.insert("doctorVisitClinic.insertDis", dis)==null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int insertPrescription(PrescriptionVO presc) {
		int cnt = 0;
		try {
			if(smc.insert("doctorVisitClinic.insertpre", presc)==null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int maxVCNum() {
		int result = 0;
		try {
			result = (int)smc.queryForObject("doctorVisitClinic.getVCMax");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int maxDisNum() {
		int result = 0;
		try {
			result = (int)smc.queryForObject("doctorVisitClinic.getDisMax");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int insertExam(ExaminationVO exam) {
		int cnt = 0;
		try {
			if(smc.insert("doctorVisitClinic.insertExam", exam)==null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public boolean searchApptVC(int appt_num) {
		boolean b = false;
		try {
			if((int)smc.queryForObject("doctorVisitClinic.searchApptVC", appt_num)>0) {
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public VisitclinicVO getPaVC(int appt_num) {
		VisitclinicVO vc = null;
		try {
			vc = (VisitclinicVO) smc.queryForObject("doctorVisitClinic.getPaVC", appt_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vc;
	}

	@Override
	public PrescriptionVO getPaPresc(int clinic_num) {
		PrescriptionVO presc = null;
		try {
			presc = (PrescriptionVO) smc.queryForObject("doctorVisitClinic.getPaPresc", clinic_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return presc;
	}

	@Override
	public DiseaseVO getPaDisease(int clinic_num) {
		DiseaseVO dis = null;
		try {
			dis = (DiseaseVO) smc.queryForObject("doctorVisitClinic.getPaDisease", clinic_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dis;
	}

	@Override
	public int updateVC(VisitclinicVO vc) {
		int cnt = 0;
		try {
			cnt = smc.update("doctorVisitClinic.updateVC", vc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updatePresc(PrescriptionVO presc) {
		int cnt = 0;
		try {
			cnt = smc.update("doctorVisitClinic.updatePresc", presc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateDisease(DiseaseVO dis) {
		int cnt = 0;
		try {
			cnt = smc.update("doctorVisitClinic.updateDisease", dis);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateExam(ExaminationVO exam) {
		int cnt = 0;
		try {
			cnt = smc.update("doctorVisitClinic.updateExam", exam);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public ExaminationVO getPaExam(int clinic_num) {
		ExaminationVO exam = null;
		try {
			exam = (ExaminationVO) smc.queryForObject("doctorVisitClinic.getPaExam", clinic_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exam;
	}
	
	
	
}
