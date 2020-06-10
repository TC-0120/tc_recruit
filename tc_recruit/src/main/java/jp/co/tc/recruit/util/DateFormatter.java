package jp.co.tc.recruit.util;

/*import java.sql.Date;*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日程フォーマットクラス
 *
 * @author TC-0115
 */
public class DateFormatter {
	/**
	 * 日程をDate型からString型に変換
	 *
	 * @param date 日程
	 * @return
	 */
	public static String toString(Date date) {
		if (date == null) {
			return null;
		} else {
			//yyyy-MM-ddとHH:mmの間にTを挟まないと、画面で登録されている値が表示できない
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date).replace(" ", "T");
		}
	}

	/**
	 * 日程をString型からDate型に変換
	 *
	 * @param stringDate 選考日程
	 * @return
	 */

	public static Date toDate(String stringDate) {
		if (stringDate.isEmpty() || stringDate == null) {
			return null;
		} else {
			try {
				//yyyy-MM-ddとHH:mmの間にTが挟まれて、画面からの入力値が送られてくる
				return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate.replace("T", " "));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	/*public static Date toDate(Date date) {
		if (date.toString().isEmpty() || date == null) {
			return null;
		} else {
			try {
				//yyyy-MM-ddとHH:mmの間にTが挟まれて、画面からの入力値が送られてくる
				String stringDate = date.toString().replace("T", " ");
				java.sql.Date sqlDate = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate).toString());
				return sqlDate;



			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	}*/

}
