package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.SelectionResult;
import jp.co.tc.recruit.repository.SelectionResultReposiyory;

/**
 * 選考結果のサービスクラス
 * @author TC-0120
 *
 */
@Service
public class SelectionResultService {

	@Autowired
	SelectionResultReposiyory selectionResultReposiyory;

	public List<SelectionResult> findAll(){
		return selectionResultReposiyory.findByOrderBySelectionResultId();
	}
}
