package jp.co.tc.recruit.Bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class TransitionRateBean implements Serializable{
	/**
	 * 該当人数
	 */
	private Integer hitNum;

	/**
	 * 対母集団率
	 */
	private Double populationRate;

	/**
	 * 移行率
	 */
	private Double transitionRate;

	private String rowName;

	public void setPopulationRate(Double populationRate) {
		this.populationRate = Double.parseDouble(String.format("%.1f", populationRate));
		if(Double.isNaN(populationRate)) {
			this.populationRate = 0.0;
		}
	}

	public void setTransitionRate(Double transitionRate) {
		this.transitionRate = Double.parseDouble(String.format("%.1f", transitionRate));
		if(Double.isNaN(transitionRate)) {
			this.transitionRate = 0.0;
		}
	}
}
