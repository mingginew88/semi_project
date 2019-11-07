package board.qna;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import VO.AnswerVO;
import VO.QuestionVO;

public class QnADao implements QnADaoInf {
	
	private static QnADao qnaDao = new QnADao();
	
	private SqlMapClient smc;
	
	public QnADao() {
		try {
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static QnADao getInstance() {
		if(qnaDao==null) {
			qnaDao = new QnADao();
		}
		return qnaDao;
	}

	@Override
	public int insertQuestion(QuestionVO questionVo) throws RemoteException {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.insert("Question.insertQnA", questionVo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	@Override
	public int insertAnswer(AnswerVO answerVo) throws RemoteException {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.insert("Question.insertAnswer", answerVo);
			if(obj == null) {
				cnt=1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteQuestion(int questionVo) throws RemoteException {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.delete("Question.deleteQnA", questionVo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int updateQuestion(QuestionVO questionVo) throws RemoteException {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.update("Question.updateQnA", questionVo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	@Override
	public int updateAnswer(AnswerVO answerVo) throws RemoteException {
		Object obj = null;
		int cnt = 0;
		try {
			obj = smc.update("Question.updateAnswer" , answerVo);
			if(obj == null) {
				cnt = 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<QuestionVO> searchQuestion(String questionVo){
		List<QuestionVO> questList = null;
		try {
			questList = smc.queryForList("Question.searchQnA", questionVo);
			
		} catch (SQLException e) {
			questList = null;
			e.printStackTrace();
		}
		return questList;
	}

	@Override
	public List<QuestionVO> getAllQuestion() throws RemoteException {
		List<QuestionVO> qnaList;
		try {
			qnaList = smc.queryForList("Question.getAllQnA");
		} catch (SQLException e) {
			e.printStackTrace();
			qnaList = null;
		}
		return qnaList;
	}

	@Override
	public List<AnswerVO> getAnswerCont(int answerNum) throws RemoteException {
		List<AnswerVO> anum;
		try {
			anum = smc.queryForList("Question.searchAnswerCont", answerNum);
		} catch (SQLException e) {
			e.printStackTrace();
			anum = null;
		}
		return anum;
	}


}
