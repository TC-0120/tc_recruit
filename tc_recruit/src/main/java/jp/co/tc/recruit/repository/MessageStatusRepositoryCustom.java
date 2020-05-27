package jp.co.tc.recruit.repository;

/**
 * メッセージステータスリポジトリのカスタムメソッドを定義したインターフェイス
 *
 * @author TC-0117
 *
 */
public interface MessageStatusRepositoryCustom {
	public void updateSort(int sortNumber, int sttMsgId);
}
