package searchData;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.MedicineVO;
import VO.PatientVO;
import VO.VisitclinicVO;

public class SearchDataDao implements ISearchDataDao{
	private static SearchDataDao sdDao;
	
	private SqlMapClient smc;
	
	private SearchDataDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SearchDataDao getInstance() {
		return sdDao == null? sdDao = new SearchDataDao() : sdDao;
	}

	@Override
	public List<PatientVO> getAllPa(int doctor_num) {
		List<PatientVO> paList = null;
		
		try {
			paList = smc.queryForList("searchData.getAllPa", doctor_num);
		} catch (SQLException e) {
			e.printStackTrace();
			paList = null;
		}
		
		return paList;
	}

	@Override
	public List<MedicineVO> getAllMedi() {
		List<MedicineVO> mediList = null;
		
		try {
			mediList = smc.queryForList("searchData.getAllMedi");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mediList;
	}

	@Override
	public Date getPaRecentVSTClinicDate(String pa_id) {
		Date RecentVSTClinicDate = null;
		
		try {
			RecentVSTClinicDate = (Date) smc.queryForObject("searchData.getPaRecentVSTClinicDate",pa_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return RecentVSTClinicDate;
		
	}

	@Override
	public List<PatientVO> searchPa(String pa_name) {
		List<PatientVO> PaList = null;
		
		try {
			PaList = smc.queryForList("searchData.searchPa",pa_name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return PaList;
	}

	@Override
	public List<MedicineVO> searchMedi(String medi_name) {
		List<MedicineVO> mediList = null;
		
		try {
			mediList = smc.queryForList("searchData.searchMedi",medi_name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mediList;
	}

	@Override
	public List<VisitclinicVO> getPaVSTClinicDate(Map map) throws RemoteException {
		List<VisitclinicVO> VSTClinic_date = null;
		try {
			VSTClinic_date = smc.queryForList("searchData.getPaVSTClinicDate", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return VSTClinic_date;
	}
	
	
}
