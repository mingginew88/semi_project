package board.notice;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.AdminisratorVO;
import VO.NoticeVO;
import admin.adminDB.AdminDBService;
import board.notice.NoticeDao;
import board.notice.NoticeDaoInf;

public class NoticeService extends UnicastRemoteObject implements NoticeServiceInf{
	
//	private static NoticeService noticeService = new NoticeService();
	private NoticeDaoInf noticeDao;
	public NoticeService() throws RemoteException {
		noticeDao = NoticeDao.getInstance();
	}

//	public static NoticeService getInstance() throws RemoteException {
//		if(noticeService == null) {
//			noticeService = new NoticeService();
//		}
//		return noticeService;
//	}
	
	/**
	 * 공지사항 입력
	 */
	@Override
	public int insertNotice(NoticeVO noticeVo) throws RemoteException {
		return noticeDao.insertNotice(noticeVo);
	}
	
	/**
	 * 공지사항 삭제
	 */
	@Override
	public int deleteNotice(int noticeVo) throws RemoteException {
		return noticeDao.deleteNotice(noticeVo);
	}

	/**
	 * 공지사항 수정
	 */
	@Override
	public int updateNotice(NoticeVO noticeVo) throws RemoteException {
		return noticeDao.updateNotice(noticeVo);
	}

	/**
	 * 검색 메서드
	 */
	@Override
	public List<NoticeVO> searchNotice(String noticeVo) throws RemoteException {
		return noticeDao.searchNotice(noticeVo);
	}
	
	/**
	 * 테이블뷰에 뿌려질 공지사항전체 데이터
	 */
	@Override
	public List<NoticeVO> getAllNotice() throws RemoteException {
		return noticeDao.getAllNotice();
	}

	/**
	 * 관리자 아이디를 가져오기 위한 메서드
	 */
	@Override
	public AdminisratorVO getAdmin(String AdminId) throws RemoteException {
		return noticeDao.getAdmin(AdminId);
	}

	@Override
	public int checkAdmin(String loginId) throws RemoteException {
		return noticeDao.checkAdmin(loginId);
	}


}
