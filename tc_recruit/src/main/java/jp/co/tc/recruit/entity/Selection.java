package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="XXTC_SELECTION")
public class Selection implements Serializable {

	@EmbeddedId
	private SelectionPK slcPK;

	@Column(name="selection_date", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date slcDate;

	@Column(name="selection_result")
	private Integer slcResult;

	public Selection() {
	}

	public Selection(SelectionPK slcPK) {
		this.slcPK = slcPK;
	}

	public SelectionPK getSlcPK() {
		return slcPK;
	}

	public void setSlcPK(SelectionPK slcPK) {
		this.slcPK = slcPK;
	}

	public Date getSlcDate() {
		return slcDate;
	}

	public void setSlcDate(Date slcDate) {
		this.slcDate = slcDate;
	}

	public Integer getSlcResult() {
		return slcResult;
	}

	public void setSlcResult(Integer slcResult) {
		this.slcResult = slcResult;
	}

	@Embeddable
	public static class SelectionPK implements Serializable{
		@Column(name="candidate_id")
		private Integer candidateId;

		@Column(name="selection_status_id")
		private Integer slcStatusId;

		public SelectionPK() {
		}

		public SelectionPK(Integer candidateId, Integer slcStatusId) {
			this.candidateId = candidateId;
			this.slcStatusId = slcStatusId;
		}

		public Integer getCandidateId() {
			return candidateId;
		}

		public void setCandidateId(Integer candidateId) {
			this.candidateId = candidateId;
		}

		public Integer getSlcStatusId() {
			return slcStatusId;
		}

		public void setSlcStatusId(Integer slcStatusId) {
			this.slcStatusId = slcStatusId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
			result = prime * result + ((slcStatusId == null) ? 0 : slcStatusId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SelectionPK other = (SelectionPK) obj;
			if (candidateId == null) {
				if (other.candidateId != null)
					return false;
			} else if (!candidateId.equals(other.candidateId))
				return false;
			if (slcStatusId == null) {
				if (other.slcStatusId != null)
					return false;
			} else if (!slcStatusId.equals(other.slcStatusId))
				return false;
			return true;
		}


	}
}

