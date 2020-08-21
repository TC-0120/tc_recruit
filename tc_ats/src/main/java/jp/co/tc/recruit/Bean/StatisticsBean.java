package jp.co.tc.recruit.Bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class StatisticsBean implements Serializable{
	/**
	 * 集計数
	 */
	private Integer count;
	/**
	 * 集計数のラベル名
	 */
	private String name;
}
