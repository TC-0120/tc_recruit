package jp.co.tc.recruit.util;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DateFormatterTest {

	// target class
	DateFormatter dateFormatter;

	@Before
	public void setUp() {
		dateFormatter = new DateFormatter();
	}

	@Test
	public void testToString_format_正常() {
		//set up
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String expected = dateFormat.format(date).replace(" ", "T");

		try {
			//execute method
			String actual = DateFormatter.toString(new Date());
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}
	}


	//正常のとき、exception起こせばなんでも良い？
	@Test
	public void testToString_null_正常() {
		//set up
		Date date = null;
		String expected = null;

		try {
			//execute method
			String actual = DateFormatter.toString(date);
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}
	}

	/*//失敗　いらない？？
	@Test
	public void testToString_null_異常() {
		//set up
		Date date = null;

		try {
			//execute method
			DateFormatter.toString(date);
			fail();

		} catch (Exception e) {
			//asseat
			assertEquals("String型へのフォーマット変換ができませんでした", e.getMessage());
		}
	}*/

	@Test
	public void testToDate_format_正常() {
		//set up
		Date dateExpected = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formatChangedExpected = dateFormat.format(dateExpected);
		String stringDate = formatChangedExpected.replace(" ", "T");

		Date dateActual = new Date();
		String formatChangedActual = dateFormat.format(dateActual);

		try {
			Date expected = dateFormat.parse(stringDate.replace("T", " "));

			//execute method
			Date actual = DateFormatter.toDate(formatChangedActual);
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testToDate_empty_正常() {
		//set up
		String stringDate = "";
		String expected = null;

		try {

			//execute method
			Date actual = DateFormatter.toDate(stringDate);
			;
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testToDate_null_正常() {
		//set up
		String stringDate = null;
		String expected = null;

		try {

			//execute method
			Date actual = DateFormatter.toDate(stringDate);
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testToDate_parseException_正常() {
		//set up
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dateActual = new Date();
		String formatChangedActual = dateFormat.format(dateActual).replace(" ", "S");

		Date expected = null;
		try {
			//execute method
			Date actual = DateFormatter.toDate(formatChangedActual);
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}

	}

	/*//nullは異常値ではない
	@Test
	public void testToDate_null_異常() {
		//set up
		String date = null;

		try {
			//execute method
			DateFormatter.toDate(date);
			fail();

		} catch (Exception e) {
			//asseat
			assertEquals("Date型へのフォーマット変換ができませんでした", e.getMessage());
		}
	}*/

	//失敗　nullまたはemptyのときや例外発生時にnullを返す仕様になっているため、例外が発生し得ない？
	//他、試したもの　→　全てfailに落ちる
	//ひらがな  あ
	//正常値　2020-12-03T24:55
	//異常値　2020/12/03 24:55  - を / に　タイムゾーンが入ってくる想定を入っていない形に
	//異常値　2020/3/3T24:55    yyyy-mm-dd を yyyy-m-d に(タイムゾーンあり)
	//異常値　2020/3/3 24:55    yyyy-mm-dd を yyyy-m-d に(タイムゾーンなし)

	/*@Test
	public void testToDate_empty_異常() {
		//set up
		String date = "2020-12-03T24:55";

		try {
			//execute method
			DateFormatter.toDate(date);
			fail();

		} catch (Exception e) {
			//asseat
			assertEquals("Date型へのフォーマット変換ができませんでした", e.getMessage());
		}
	}*/

}
