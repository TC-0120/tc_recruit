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
		String expected = "2020-06-10 13:01";
		SimpleDateFormat dateFormat = new SimpleDateFormat("2020-06-10 13:01");
		Date date = new Date();

		try {
			//execute method
			String actual = dateFormatter.toString(new Date());
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testToString_replace_正常() {
		//set up
		String expected = "2020-06-10T13:01";
		String actual = "2020-06-10 13:01";

		try {
			//execute method
			actual = actual.replace(" ", "T");
			//asseat
			assertEquals(expected, actual);

		} catch (Exception e) {
			fail();
		}
	}


	@Test
	public void testToString_format_異常() {
		//set up
		/*String expected = "2020-06-10 13:01";*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("2020-06-10 13:01:01");
		Date date = new Date();

		try {
			//execute method
			dateFormatter.toString(null);
			/*assertEquals(expected, actual);*/
			fail();

		} catch (Exception e) {
			//asseat
			assertEquals("フォーマットが異なります", e.getMessage());

		}
	}

	@Test
	public void testToString_replace_異常() {
		//set up
		/*String expected = "2020-06-10T13:01";*/
		String actual = "2020-06-10T13:01";

		try {
			//execute method
			actual.replace(" ", "T");
			fail();

		} catch (Exception e) {
			//asseat
			assertEquals("タイムゾーンが挿入されていません", e.getMessage());
		}
	}

}
