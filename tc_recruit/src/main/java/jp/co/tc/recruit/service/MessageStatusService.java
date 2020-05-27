package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.MessageStatus;
import jp.co.tc.recruit.form.MessageStatusForm;
import jp.co.tc.recruit.repository.MessageStatusRepository;

@Service
public class MessageStatusService {

	@Autowired
	MessageStatusRepository msgSttRepo;
	MessageStatus msgStt = new MessageStatus();

	public MessageStatus findByStatusMessageId(Integer statusMessageId) {
		return msgSttRepo.findByStatusMessageId(statusMessageId);
	}

	public List<MessageStatus> findAllByOrderBySort() {
		return msgSttRepo.findAllByOrderBySort();
	}

	/*	public List<MessageStatus> findByOrderBySelectionStatusIdAscSelectionStatusDetailIdAsc() {
			return msgSttRepo.findByOrderBySelectionStatusIdAscSelectionStatusDetailIdAsc();
		}*/

	/**
	 * ステータスメッセージの一括更新
	 *
	 * @param msgSttForm 一括更新フォーム
	 *
	 */
	public void messageStatusUpdate(MessageStatusForm msgSttFormList) {

		for (int i = 0; i < msgSttFormList.getStatusMessageId().size(); i++) {
			Integer sttMsgIdByDB = Integer
					.parseInt((msgSttRepo.findByStatusMessageId(i + 1).getStatusMessageId()).toString());
			String sttMsgByDB = (msgSttRepo.findByStatusMessageId(i + 1).getStatusMessage()).toString();
			Integer slcSttIdByDB = Integer
					.parseInt((msgSttRepo.findByStatusMessageId(i + 1).getSelectionStatusId()).toString());
			Integer slcSttDtlIdByDB = Integer
					.parseInt((msgSttRepo.findByStatusMessageId(i + 1).getSelectionStatusDetailId()).toString());
			String iconByDB = String.valueOf(msgSttRepo.findByStatusMessageId(i + 1).getIcon());
			Integer sortByDB = Integer
					.parseInt((msgSttRepo.findByStatusMessageId(i + 1).getSort()).toString());

			Integer sttMsgIdByForm = Integer.parseInt((msgSttFormList.getStatusMessageId().get(i)).toString());
			String sttMsgByForm = (msgSttFormList.getStatusMessage().get(i)).toString();
			Integer slcSttIdByForm = Integer.parseInt((msgSttFormList.getSelectionStatusId().get(i)).toString());
			Integer slcSttDtlIdByForm = Integer
					.parseInt((msgSttFormList.getSelectionStatusDetailId().get(i)).toString());

			//DB情報と入力情報が異なる場合、DB情報書き換え実行
			if (sttMsgIdByForm != sttMsgIdByDB
					|| sttMsgByForm != sttMsgByDB
					|| slcSttIdByForm != slcSttIdByDB
					|| slcSttDtlIdByForm != slcSttDtlIdByDB) {
				msgStt.setStatusMessageId(sttMsgIdByForm);
				msgStt.setStatusMessage(sttMsgByForm);
				msgStt.setSelectionStatusId(slcSttIdByForm);
				msgStt.setSelectionStatusDetailId(slcSttDtlIdByForm);
				msgStt.setIcon(iconByDB);
				msgStt.setSort(sortByDB);

				if (sttMsgIdByForm <= 9) {
					msgStt.setMessageStatusFlag(1);
				} else {
					msgStt.setMessageStatusFlag(2);
				}
				msgSttRepo.saveAndFlush(msgStt);
			}
		}
	}

	/**
	 * ステータスメッセージの挿入
	 *
	 * @param msgStt 挿入データ
	 *
	 */
	public void messageStatusInput(MessageStatus msgSttForm) {
		String sttMsgByForm = msgSttForm.getStatusMessage().toString();
		Integer slcSttIdByForm = Integer.parseInt((msgSttForm.getSelectionStatusId()).toString());
		Integer slcSttDtlIdByForm = Integer.parseInt((msgSttForm.getSelectionStatusDetailId()).toString());

		//入力値がある場合、それぞれ保存
		if (sttMsgByForm != null) {
			msgStt.setStatusMessage(sttMsgByForm);
		}
		if (slcSttIdByForm != null) {
			msgStt.setSelectionStatusId(slcSttIdByForm);
		}
		if (slcSttDtlIdByForm != null) {
			msgStt.setSelectionStatusDetailId(slcSttDtlIdByForm);
		}
		/*詳細IDが空欄なら(detailId=10)選考中グループ(1)
		そうでなければ要対応グループ(2)*/
		if (slcSttDtlIdByForm == 10) {
			msgStt.setMessageStatusFlag(1);
		} else {
			msgStt.setMessageStatusFlag(2);
		}

		msgSttRepo.save(msgStt);

		List<MessageStatus> sorted = msgSttRepo
				.findByOrderByMessageStatusFlagAscSelectionStatusIdAscSelectionStatusDetailIdAsc();
		for (int n = 0; n < sorted.size(); n++) {
			if (sorted.get(n).getSort() == null) {
				msgStt.setSort(sorted.get(n + 1).getSort());
				msgSttRepo.save(msgStt);

				for (int k = n + 2; k < sorted.size(); k++) {
					MessageStatus sort = msgSttRepo.findByStatusMessageId(k);
					sort.setSort(sorted.get(k - 1).getSort() + 1);
					msgSttRepo.save(sort);
				}
			}
		}
	}
}
