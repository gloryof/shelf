package jp.glory.bookshelf.domain.shelf.value;

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
public class NameTest {

	public static class コンストラクタのテスト {

		@Test
		public void パラメータに値を指定して取得できる() {

			final String expectedValue = "アジャイル";
			final Name sut = new Name(expectedValue);

			assertThat(sut.getValue(), is(expectedValue));
		}

		@Test
		public void パラメータにNullを指定するとブランクが取得できる() {

			final Name sut = new Name(null);

			assertThat(sut.getValue(), is(""));
		}
	}

	public static class 値が設定されている場合 {

		private Name sut = null;

		@Before
		public void setUp() {

			sut = new Name("ドメイン駆動");
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class Nullが設定されている場合 {

		private Name sut = null;

		@Before
		public void setUp() {

			sut = new Name(null);
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
				Name.LABEL
			})));
		}
	}

	public static class 入力文字数が150バイトの場合 {

		private Name sut = null;

		@Before
		public void setUp() {

			final StringBuilder builder = new StringBuilder();

			for (int i = 0; i < 150; i++) {

				builder.append("a");
			}

			sut = new Name(builder.toString());
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class 入力文字数が半角151バイトの場合 {

		private Name sut = null;

		@Before
		public void setUp() {

			final StringBuilder builder = new StringBuilder();

			for (int i = 0; i < 151; i++) {

				builder.append("a");
			}

			sut = new Name(builder.toString());
		}

		@Test
		public void validateを実行するとバイト数チェックエラーとなる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					Name.LABEL, String.valueOf(150)
			})));
		}
	}

	public static class 入力文字数が全角153バイトの場合 {

		private Name sut = null;

		@Before
		public void setUp() {

			final StringBuilder builder = new StringBuilder();

			for (int i = 0; i < 51; i++) {

				builder.append("あ");
			}

			sut = new Name(builder.toString());
		}

		@Test
		public void validateを実行するとバイト数チェックエラーとなる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					Name.LABEL, String.valueOf(150)
			})));
		}
	}
}
