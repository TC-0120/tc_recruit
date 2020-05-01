package jp.co.tc.recruit.constant;

/**
 * 選考ステータス定数クラス
 *
 * @author TC-0115
 */
public class SlcStatusConstant {

	//値は選考ステータスマスタのID
	public static final Integer INFORMATION_SESSION = 1;
	public static final Integer APPLICANT_SCREENING = 2;
	public static final Integer FIRST_INTERVIEW = 3;
	public static final Integer SECOND_INTERVIEW = 4;
	public static final Integer LAST_INTERVIEW = 5;
	public static final Integer INFORMAL_OFFER = 6;
	public static final Integer JOINNING_PROCEDURE = 7;
	public static final Integer APTITUDE_TEST = 8;

	//選考ステータス検索における一覧の値
	public static final Integer ALL = 0;

	private SlcStatusConstant() {}
}
