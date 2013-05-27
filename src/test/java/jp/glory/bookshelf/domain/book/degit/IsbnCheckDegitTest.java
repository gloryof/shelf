package jp.glory.bookshelf.domain.book.degit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class IsbnCheckDegitTest {

	public static class Factoryメソッドの確認 {

		@Test
		public void バイト数が10バイトの場合_ISBN10のチェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create("1234567890");

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(Isbn10CheckDegit.class)));
		}

		@Test
		public void バイト数が13バイトの場合_ISBN13のチェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create("1234567890123");

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(Isbn13CheckDegit.class)));
		}

		@Test
		public void バイト数が9バイトの場合_不正チェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create("123456789");

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(InvalidCheckDegit.class)));
		}

		@Test
		public void バイト数が11バイトの場合_不正チェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create("12345678901");

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(InvalidCheckDegit.class)));
		}

		@Test
		public void バイト数が12バイトの場合_不正チェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create("123456789012");

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(InvalidCheckDegit.class)));
		}

		@Test
		public void バイト数が14バイトの場合_不正チェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create("12345678901234");

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(InvalidCheckDegit.class)));
		}

		@Test
		public void Nullの場合_不正チェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create(null);

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(InvalidCheckDegit.class)));
		}

		@Test
		public void ブランクの場合_不正チェックディジットが返却される() {

			final IsbnCheckDegit actualObject = IsbnCheckDegit.create("");

			assertThat(actualObject, is(not(nullValue())));
			assertThat(actualObject, is(instanceOf(InvalidCheckDegit.class)));
		}
	}

	public static class 不正な形式な場合_falseが返却される {

		@Test
		public void バイト数が9バイトの場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create("123456789");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void バイト数が11バイトの場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create("12345678901");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void バイト数が12バイトの場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create("123456789012");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void バイト数が14バイトの場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create("12345678901234");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void 値がNullの場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create(null);

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void 値が空文字の場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create("");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void 文字数が10バイトでチェックディジット以外にアルファベットがある場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create("47981223X4");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void 文字数が13バイトでチェックディジット以外にアルファベットがある場合() {

			final IsbnCheckDegit sut = IsbnCheckDegit.create("47981223123X4");

			assertThat(sut.isValid(), is(false));
		}

	}
}
