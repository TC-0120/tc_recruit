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

	public List<MessageStatus> findAllByOrderByStatusMessageId() {
		return msgSttRepo.findAllByOrderByStatusMessageId();
	}

	/**
	 * ステータスメッセージの一括更新
	 *
	 * @param msgSttForm 一括更新フォーム
	 *
	 */
	public void messageStatusUpdate(MessageStatusForm msgSttFormList) {

		for (int i = 0; i < msgSttFormList.getStatusMessageId().size(); i++) {
			Integer sttMsgIdByForm = Integer.parseInt((msgSttFormList.getStatusMessageId().get(i)).toString());
			String sttMsgByForm = (msgSttFormList.getStatusMessage().get(i)).toString();
			Integer slcSttIdByForm = Integer.parseInt((msgSttFormList.getSelectionStatusId().get(i)).toString());
			Integer slcSttDtlIdByForm = Integer
					.parseInt((msgSttFormList.getSelectionStatusDetailId().get(i)).toString());
			//入力値がある場合、それぞれ保存
			if (sttMsgIdByForm != null) {
				msgStt.setStatusMessageId(sttMsgIdByForm);
			}
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
			msgSttRepo.saveAndFlush(msgStt);
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

		msgSttRepo.saveAndFlush(msgStt);
	}

}
