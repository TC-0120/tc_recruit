package jp.co.tc.recruit.form;

import java.io.Serializable;

public class SelectionStatusForm implements Serializable{

	private Integer slcStatusId;

	private String slcStatusName;

	public Integer getSlcStatusId() {
		return slcStatusId;
	}

	public void setSlcStatusId(Integer slcStatusId) {
		this.slcStatusId = slcStatusId;
	}

	public String getSlcStatusName() {
		return slcStatusName;
	}

	public void setSlcStatusName(String slcStatusName) {
		this.slcStatusName = slcStatusName;
	}


}
