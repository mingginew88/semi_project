package admin;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class AdminMainDao implements IAdminMainDao {
	
	private static AdminMainDao adminMainDao;
	private SqlMapClient smc;
	
	private AdminMainDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AdminMainDao getInstance() {
		if(adminMainDao == null) {
			adminMainDao = new AdminMainDao();
		}
		return adminMainDao;
	}
	
	
}
