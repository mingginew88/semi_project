package board.notice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.AdminisratorVO;
import VO.NoticeVO;

public interface NoticeServiceInf extends Remote{


	/**
	 * 새 공지사항 작성하기
	 * @param noticeVo
	 * @return
	 */
	public int insertNotice(NoticeVO noticeVo) throws RemoteException;

	/**
	 * 공지사항 삭제
	 * @param noticeVo
	 * @return
	 */
	public int deleteNotice(int noticeVo) throws RemoteException;

	/**
	 * 공지사항 수정
	 * @param noticeVo
	 * @return
	 */
	public int updateNotice(NoticeVO noticeVo) throws RemoteException;

	/**
	 * 공지사항 검색
	 * @param noticeVo
	 * @return
	 */
	public List<NoticeVO> searchNotice(String noticeVo) throws RemoteException;

	/**
	 * 공지사항 데이터 저장
	 * @return
	 */
	public List<NoticeVO> getAllNotice() throws RemoteException;
	
	
	/**
	 * 관리자 정보 가져 오기
	 * @return
	 * @throws RemoteException
	 */
	public AdminisratorVO getAdmin(String AdminId) throws RemoteException;
	
	/**
	 * 관리자 체크용
	 * @param loginId
	 * @return
	 * @throws RemoteException
	 */
	public int checkAdmin(String loginId)throws RemoteException;


}
