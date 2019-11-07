package board.qna;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import VO.AnswerVO;
import VO.QuestionVO;

public interface QnAServiceInf extends Remote {

	/**
	 * 질문작성
	 * @param questionVo
	 * @return
	 */
	public int insertQuestion(QuestionVO questionVo) throws RemoteException;
	
	/**
	 * 답변작성
	 * @param answerVo
	 * @return
	 * @throws RemoteException
	 */
	public int insertAnswer(AnswerVO answerVo) throws RemoteException;
	
	/**
	 * 질문 삭제
	 * @param questionVo
	 * @return
	 */
	public int deleteQuestion(int questionVo) throws RemoteException;
	
	/**
	 * 질문 수정
	 * @param questionVo
	 * @return
	 */
	public int updateQuestion(QuestionVO questionVo) throws RemoteException;
	
	/**
	 * 답변 수정
	 * @param answerVo
	 * @return
	 * @throws RemoteException
	 */
	public int updateAnswer(AnswerVO answerVo) throws RemoteException;
	
	/**
	 * 그 질문에 대한 답변 가져오기
	 * @param answerNum
	 * @return
	 * @throws RemoteException
	 */
	public List<AnswerVO> getAnswerCont(int answerNum) throws RemoteException;
	
	/**
	 * 번호 있는지 없는지 검색
	 * @param num
	 * @return
	 * @throws RemoteException
	 */
	public int searchQnANum(int num) throws RemoteException;
	
	/**
	 * 질문사항 검색
	 * @param questionVo
	 * @return
	 */
	public List<QuestionVO> searchQuestion(String questionVo) throws RemoteException;
	
	/**
	 * 질문 데이터 저장
	 * @return
	 */
	public List<QuestionVO> getAllQuestion() throws RemoteException;
	
	
}
