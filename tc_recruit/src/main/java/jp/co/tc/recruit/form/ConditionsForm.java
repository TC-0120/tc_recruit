package jp.co.tc.recruit.form;

/**
 * 検索フォームクラス
 *
 * @author TC-0115
 *
 */
public class ConditionsForm {
	private Integer slcStatusId;
	private Integer slcStatusDtlId;
	private Integer order;
	private Integer direction;
	private String from;
	private String to;

	//ソート項目のID
	public static final int CANDIDATE_ID = 1;
	public static final int CANDIDATE_NAME = 2;
	public static final int GENDER = 3;
	public static final int EDU_BACK= 4;
	public static final int AGENT = 5;
	public static final int REFERRER = 6;
	public static final int SLC_STATUS = 7;
	public static final int SLC_STATUS_DTL = 8;
	public static final int SLC_DATE = 9;

	//並び替え順番
	public static final int ASC = 1;
	public static final int DESC = 2;

	public ConditionsForm() {
		slcStatusId = 0;
		slcStatusDtlId = 0;
		order = 1;
		direction = 1;
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
