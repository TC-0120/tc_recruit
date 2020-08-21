package jp.co.tc.recruit.form;

import lombok.Data;

/**
 * 検索フォームクラス
 *
 * @author TC-0115
 *
 */
@Data
public class ConditionsForm {
	private Integer slcStatusId;
	private Integer slcStatusDtlId;
	private Integer order;
	private Integer direction;
	private Integer freeSearchId;
	private String freeSearchWord;
	private String from;
	private String to;

	//ソート項目のID
	public static final int CANDIDATE_ID = 1;
	public static final int CANDIDATE_NAME = 2;
	public static final int CANDIDATE_NAME_FURIGANA = 3;
	public static final int GENDER = 4;
	public static final int EDU_BACK= 5;
	public static final int CANDIDATE_EMAIL_ADDRESS = 6;
	public static final int AGENT = 7;
	public static final int AGENT_FEE = 8;
	public static final int APTITUDE = 9;
	public static final int APTITUDE_SCORE = 10;
	public static final int SLC_STATUS = 11;
	public static final int SLC_STATUS_DTL = 12;
	public static final int SLC_DATE = 13;
	public static final int REMARKS = 14;
	public static final int INSERT_USER = 15;
	public static final int INSERT_DATE = 16;
	public static final int UPDATE_USER = 17;
	public static final int UPDATE_DATE = 18;
	public static final int DELETE_FLAG = 19;

	//自由検索のID
	public static final int SEARCH_NO_SELECT = 0;
	public static final int SEARCH_CANDIDATE_NAME = 1;
	public static final int SEARCH_CANDIDATE_NAME_FURIGANA = 2;
	public static final int SEARCH_EDU_BACK = 3;
	public static final int SEARCH_AGENT = 4;
	public static final int SEARCH_APTITUDE = 5;
	public static final int DELETE_STATUS = 6;


	//並び替え順番
	public static final int ASC = 1;
	public static final int DESC = 2;

	public ConditionsForm() {
		slcStatusId = 0;
		slcStatusDtlId = 0;
		order = 1;
		direction = 1;
		freeSearchId = 0;
		freeSearchWord = "";
		from = "";
		to = "";
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Integer getFreeSearchId() {
		return freeSearchId;
	}

	public void setFreeSearchId(Integer freeSearchId) {
		this.freeSearchId = freeSearchId;
	}

	public String getFreeSearchWord() {
		return freeSearchWord;
	}

	public void setFreeSearchWord(String freeSearchWord) {
		this.freeSearchWord = freeSearchWord;
	}

	public Integer getSlcStatusId() {
		return slcStatusId;
	}
	public void setSlcStatusId(Integer slcStatusId) {
		this.slcStatusId = slcStatusId;
	}
	public Integer getSlcStatusDtlId() {
		return slcStatusDtlId;
	}
	public void setSlcStatusDtlId(Integer slcStatusDtlId) {
		this.slcStatusDtlId = slcStatusDtlId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
