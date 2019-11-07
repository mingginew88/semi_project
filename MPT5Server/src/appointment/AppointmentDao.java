package appointment;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AppointmentVO;
public class AppointmentDao implements IAppointmentDao{
	private static AppointmentDao appointmentDao = new AppointmentDao();
	private SqlMapClient smc;
	
	private AppointmentDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AppointmentDao getInterface() {
		if(appointmentDao == null) {
			appointmentDao = new AppointmentDao();
		}
		return appointmentDao;
	}

	@Override
	public int updateAppointment(AppointmentVO apptmtvo) {
		int cnt = 0;
		try {
			cnt = smc.update("Appointment.updateAppointment",apptmtvo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<AppointmentVO> searchAppointmentAll(String pa_id) {
		List<AppointmentVO> appl = new ArrayList<>();
		try {
			appl = smc.queryForList("Appointment.searchAppointmentAll",pa_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return appl;
	}

	
	
	
}
