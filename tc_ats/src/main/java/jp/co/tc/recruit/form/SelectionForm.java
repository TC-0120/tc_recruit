package jp.co.tc.recruit.form;

import java.io.Serializable;

import lombok.Data;
@Data
public class SelectionForm implements Serializable{
	private Integer candidateId;

	private Integer slcStatusId;

	private String slcDate;

	private Integer deleteFlag;
}
