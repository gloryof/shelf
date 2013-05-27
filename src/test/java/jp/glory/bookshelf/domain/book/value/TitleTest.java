package jp.glory.bookshelf.domain.book.value;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TitleTest {

	public static class コンストラクタのテスト {

		@Test
		public void パラメータに値を指定して取得できる() {

			final String expectedValue = "アジャイルサムライ";
			final Title sut = new Title(expectedValue);

			assertThat(sut.getValue(), is(expectedValue));
		}

		@Test
		public void パラメータにNullを指定するとブランクが取得できる() {

			final Title sut = new Title(null);

			assertThat(sut.getValue(), is(""));
		}
	}

	public static class 値が設定されている場合 {

		private Title sut = null;

		@Before
		public void setUp() {

			sut = new Title("ドメイン駆動設計");
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class Nullが設定されている場合 {

		private Title sut = null;

		@Before
		public void setUp() {

			sut = new Title(null);
		}

		@Test
		public void getValueで空文字が返却される() {

			assertThat(sut.getValue(), is(""));
		}

		@Test
		public void validateを実行すると未入力チェックエラーとなる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.VALUE_IS_EMPTY, new String[] {
				Title.LABEL
			})));
		}
	}

	public static class 入力文字数が150バイトの場合 {

		private Title sut = null;

		@Before
		public void setUp() {

			final StringBuilder builder = new StringBuilder();

			for (int i = 0; i < 150; i++) {

				builder.append("a");
			}

			sut = new Title(builder.toString());
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class 入力文字数が半角151バイトの場合 {

		private Title sut = null;

		@Before
		public void setUp() {

			final StringBuilder builder = new StringBuilder();

			for (int i = 0; i < 151; i++) {

				builder.append("a");
			}

			sut = new Title(builder.toString());
		}

		@Test
		public void validateを実行するとバイト数チェックエラーとなる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					Title.LABEL, String.valueOf(150)
			})));
		}
	}

	public static class 入力文字数が全角153バイトの場合 {

		private Title sut = null;

		@Before
		public void setUp() {

			final StringBuilder builder = new StringBuilder();

			for (int i = 0; i < 51; i++) {

				builder.append("あ");
			}

			sut = new Title(builder.toString());
		}

		@Test
		public void validateを実行するとバイト数チェックエラーとなる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					Title.LABEL, String.valueOf(150)
			})));
		}
	}
}
