package jp.co.tc.recruit.form;

public class ConditionsForm {
	private Integer slcStatusId;
	private Integer slcStatusDtlId;
	private Integer order;
	private Integer direction;
	private String period;

	public ConditionsForm() {
		slcStatusId = 0;
		slcStatusDtlId = 0;
		order = 1;
		direction = 1;
		period = "all";
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
