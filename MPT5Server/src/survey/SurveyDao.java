package survey;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
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
import VO.SurveyVO;
import VO.VisitclinicVO;

public class SurveyDao implements ISurveyDao{
	private static SurveyDao sDao;
	private SqlMapClient smc;
	
	private SurveyDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SurveyDao getInstance() {
		return sDao==null? sDao=new SurveyDao():sDao;
	}

	@Override
	public int insertsurvey(SurveyVO survey) {
		int cnt = 0;
		try {
			if(smc.insert("survey.insertSurvey", survey)==null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	
	
}
