package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="XXTC_SELECTION_STATUS_DETAIL")
public class SelectionStatusDetail implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtlId;

	@Column(name="selection_status_detail_name")
	private String slcStatusDtlName;

	@Column(name="selection_status_flag")
	private Integer slcStatusFlag;

	@OneToMany(mappedBy="slcStatusDtl", cascade=CascadeType.ALL)
	private List<Candidate> candidates;

	public Integer getSlcStatusDtlId() {
		return slcStatusDtlId;
	}

	public void setSlcStatusDtlId(Integer slcStatusDtlId) {
		this.slcStatusDtlId = slcStatusDtlId;
	}

	public String getSlcStatusDtlName() {
		return slcStatusDtlName;
	}

	public void setSlcStatusDtlName(String slcStatusDtlName) {
		this.slcStatusDtlName = slcStatusDtlName;
	}

	public Integer getSlcStatusFlag() {
		return slcStatusFlag;
	}

	public void setSlcStatusFlag(Integer slcStatusFlag) {
		this.slcStatusFlag = slcStatusFlag;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}
}
