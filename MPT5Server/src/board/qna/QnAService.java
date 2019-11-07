package board.qna ;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import VO.AnswerVO;
import VO.QuestionVO;

public class QnAService extends UnicastRemoteObject implements QnAServiceInf{

	private QnADaoInf qnaDao;
	
	public QnAService() throws RemoteException {
		qnaDao = QnADao.getInstance();
	}

	@Override
	public int insertQuestion(QuestionVO questionVo) throws RemoteException {
		return qnaDao.insertQuestion(questionVo);
	}

	@Override
	public int insertAnswer(AnswerVO answerVo) throws RemoteException {
		return qnaDao.insertAnswer(answerVo);
	}
	
	@Override
	public int deleteQuestion(int questionVo) throws RemoteException {
		return qnaDao.deleteQuestion(questionVo);
	}

	@Override
	public int updateQuestion(QuestionVO questionVo) throws RemoteException {
		return qnaDao.updateQuestion(questionVo);
	}
	
	@Override
	public int updateAnswer(AnswerVO answerVo) throws RemoteException {
		return qnaDao.updateAnswer(answerVo);
	}

	@Override
	public List<QuestionVO> searchQuestion(String questionVo) throws RemoteException {
		return qnaDao.searchQuestion(questionVo);
	}

	@Override
	public List<QuestionVO> getAllQuestion() throws RemoteException {
		return qnaDao.getAllQuestion();
	}

	@Override
	public List<AnswerVO> getAnswerCont(int answerNum) throws RemoteException {
		return qnaDao.getAnswerCont(answerNum);
	}

}
