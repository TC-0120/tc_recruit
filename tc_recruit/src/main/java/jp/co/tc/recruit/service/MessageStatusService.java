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

	public MessageStatus findByStatusMessageId(Integer statusMessageId) {
		return msgSttRepo.findByStatusMessageId(statusMessageId);
	}

	public List<MessageStatus> findAllByOrderByStatusMessageId(){
		return msgSttRepo.findAllByOrderByStatusMessageId();
	}

	/**
	 * ステータスメッセージの一括更新
	 *
	 * @param cId 候補者ID
	 * @param msgSttForm 一括更新フォーム
	 *
	 */
	public void messageStatusUpdate(MessageStatusForm msgSttForm) {
		/*List<MessageStatus> msgSttList = new ArrayList<MessageStatus>();*/
		MessageStatus msgStt = new MessageStatus();

		for (int i = 0; i < msgSttForm.getStatusMessageId().size(); i++) {
			Integer sttMsgIdByForm = Integer.parseInt((msgSttForm.getStatusMessageId().get(i)).toString());
			String sttMsgByForm = (msgSttForm.getStatusMessage().get(i)).toString();
			Integer slcSttIdByForm = Integer.parseInt((msgSttForm.getSelectionStatusId().get(i)).toString());
			Integer slcSttDtlIdByForm = Integer.parseInt((msgSttForm.getSelectionStatusDetailId().get(i)).toString());
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
			msgSttRepo.saveAndFlush(msgStt);
		}


	}

}
