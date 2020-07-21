package jp.co.tc.recruit.repository.Impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jp.co.tc.recruit.repository.MessageStatusRepositoryCustom;

/**
	* メッセージステータステーブルのリポジトリのカスタムメソッド実装クラス
	*
	* @author TC-0117
	*
	*/

public class MessageStatusRepositoryImpl implements MessageStatusRepositoryCustom{
	@PersistenceContext
	private EntityManager em;

	/**
		* ステータスメッセージの挿入
		*
		*/
	public void updateSort(int sortNumber, int sttMsgId) {
		//挿入データのソート値を決定
		String updateNewSortData = "UPDATE MessageStatus "
				+ "SET sort = :sortNumber "
				+ "WHERE statusMessageId = :sttMsgId";
		
		em.createQuery(updateNewSortData)
		  .setParameter("sortNumber", sortNumber)
		  .setParameter("sttMsgId", sttMsgId);


		/*//ソート値決定に伴い、その後ろのデータのソート値を繰り上げ
		String updateOldSortData = "UPDATE MessageStatus "
				+ "SET sort = :sortNumberPlusOne "
				+ "WHERE sort => :sttMsgId "
				+ "AND sort < :sortNumber "
				+ "AND statusMessageId != :sttMsgId ";
				
		em.createQuery(updateOldSortData)
		  .setParameter("sortNumber", sortNumber)
		  .setParameter("sttMsgId", sttMsgId)
		  .setParameter("sortNumberPlusOne", sortNumber + 1);*/
	}
}
