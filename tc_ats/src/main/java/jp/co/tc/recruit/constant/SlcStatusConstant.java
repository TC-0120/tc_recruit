package jp.co.tc.recruit.constant;

/**
 * 選考ステータス定数クラス
 *
 * @author TC-0115
 */
public class SlcStatusConstant {

	//値は選考ステータスマスタのID
	/**
	 * 説明会
	 */
	public static final Integer INFORMATION_SESSION = 1;
	/**
	 * 書類選考
	 */
	public static final Integer APPLICANT_SCREENING = 2;
	/**
	 * 一次面接
	 */
	public static final Integer FIRST_INTERVIEW = 3;
	/**
	 * 二次面接
	 */
	public static final Integer SECOND_INTERVIEW = 4;
	/**
	 * 最終面接
	 */
	public static final Integer LAST_INTERVIEW = 5;
	/**
	 * 内定
	 */
	public static final Integer INFORMAL_OFFER = 6;
	/**
	 * 入社手続き
	 */
	public static final Integer JOINNING_PROCEDURE = 7;
	public static final Integer APTITUDE_TEST = 8;

	//選考ステータス検索における一覧の値
	public static final Integer ALL = 0;

	private SlcStatusConstant() {}
}
