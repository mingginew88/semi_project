package disease;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.DiseaseVO;
import examination.ExaminationDao;

public class DiseaseDao implements IDiseaseDao{

	private static DiseaseDao diseaseDao = new DiseaseDao();
	private SqlMapClient smc;
	
	private DiseaseDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DiseaseDao getInterface() {
		if(diseaseDao == null) {
			diseaseDao = new DiseaseDao();
		}
		return diseaseDao;
	}

	@Override
	public List<DiseaseVO> searchDiseaseAll(String pa_id) {
		List<DiseaseVO> diseaseList = new ArrayList<DiseaseVO>();
		
		try {
			diseaseList=smc.queryForList("Disease.searchDiseaseAll",pa_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return diseaseList;
	}
	
}
