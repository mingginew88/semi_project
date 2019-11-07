package examination;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.ExaminationVO;
import appointment.AppointmentDao;

public class ExaminationDao implements IExaminationDao{
	
	private static ExaminationDao examinationDao = new ExaminationDao();
	private SqlMapClient smc;
	
	private ExaminationDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ExaminationDao getInterface() {
		if(examinationDao == null) {
			examinationDao = new ExaminationDao();
		}
		return examinationDao;
	}

	@Override
	public List<ExaminationVO> searchExaminationAll(String pa_id) {
		List<ExaminationVO> exvol = new ArrayList<ExaminationVO>();
		try {
			exvol = smc.queryForList("Examination.searchExaminationAll",pa_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exvol;
	}

}
