package board.notice;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;


import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AdminisratorVO;
import VO.NoticeVO;

public class NoticeDao implements NoticeDaoInf {
	
	private static NoticeDao noticedao = new NoticeDao();
	
	private SqlMapClient smc;
	
	public NoticeDao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	public static NoticeDao getInstance() {
		if (noticedao==null) {
			noticedao = new NoticeDao();
		}
		return noticedao;
	}
	
	@Override
	public int insertNotice(NoticeVO noticeVo) {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.insert("Notice.insertNotice", noticeVo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteNotice(int noticeVo) {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.insert("Notice.deleteNotice", noticeVo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateNotice(NoticeVO noticeVo) {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.insert("Notice.updateNotice", noticeVo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<NoticeVO> searchNotice(String noticeVo) {
		List<NoticeVO> noticeList = null;
		try {
			noticeList = smc.queryForList("Notice.searchNotice", noticeVo);
		} catch (SQLException e) {
			noticeList = null;
			e.printStackTrace();
		}
		return noticeList;
	}

	@Override
	public List<NoticeVO> getAllNotice() {
		List<NoticeVO> NoList;
		try {
			NoList = smc.queryForList("Notice.getAllNotice");
		} catch (SQLException e) {
			NoList = null;
			e.printStackTrace();
		}
		return NoList;
	}

	@Override
	public AdminisratorVO getAdmin(String adminId) {
		AdminisratorVO admin = null;
		try {
			admin = (AdminisratorVO) smc.queryForObject("Notice.getAdmin", adminId);
		} catch (SQLException e) {
			admin = null;
			e.printStackTrace();
		}
		return admin;
	}

	@Override
	public int checkAdmin(String loginId) throws RemoteException {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.insert("Notice.checkAdmin", loginId);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	
	}
	
}
