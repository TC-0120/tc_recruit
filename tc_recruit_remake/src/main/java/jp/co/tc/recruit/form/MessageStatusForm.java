package jp.co.tc.recruit.form;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * ステータス変更フォームクラス
 *
 */
@Data
public class MessageStatusForm {
        private List<Integer> statusMessageId;
        @NotNull
        @Size(min = 1, max = 10)
        private List<String> statusMessage ;
        private List<Integer> selectionStatusId;
        private List<Integer> selectionStatusDetailId;
        private List<Integer> messageStatusFlag;
	public MessageStatusForm() {
		statusMessageId = null;
		statusMessage = null;
		selectionStatusId = null;
		selectionStatusDetailId = null;
		messageStatusFlag = null;
	}

}
