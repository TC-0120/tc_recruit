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
@Table(name="XXTC_SELECTION_STATUS")
public class SelectionStatus implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="selection_status_id")
	private Integer slcStatusId;

	@Column(name="selection_status_name")
	private String slcStatusName;

	@OneToMany(mappedBy="slcStatus", cascade=CascadeType.ALL)
	private List<Candidate> candidates;

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

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

}
