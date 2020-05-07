package jp.co.tc.recruit.constant;

/**
 * 選考ステータス詳細定数クラス
 *
 * @author TC-0115
 */
public class SlcStatusDtlConstant {

	//値は選考ステータス詳細マスタのID
	public static final Integer ADJUSTING = 1;
	public static final Integer SELECTING = 2;
	public static final Integer PASSING = 3;
	public static final Integer FAILURE = 4;
	public static final Integer REFUSAL = 5;
	public static final Integer WATING_ACCEPTANCE = 6;
	public static final Integer ACCEPTANCE = 7;
	public static final Integer CONFIRMED = 8;
	public static final Integer FINISHED = 9;
	public static final Integer BLANK = 10;

	//選考ステータス詳細検索における一覧、要対応の値
	public static final Integer ALL = 0;
	public static final Integer NEED_RESPOND = -1;

	//選考情報管理の選考結果における保留中の値
	public static final Integer PENDING = 0;

	private SlcStatusDtlConstant() {}
}
