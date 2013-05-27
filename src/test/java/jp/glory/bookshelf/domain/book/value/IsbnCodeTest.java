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
public class IsbnCodeTest {

	public static class コンストラクタのテスト {

		@Test
		public void パラメータに値を指定した場合() {

			final String expectedValue = "978-4-274-06885-0";
			final IsbnCode sut = new IsbnCode(expectedValue);

			assertThat(sut.getValue(), is(expectedValue));
		}

		@Test
		public void パラメータにNullを指定した場合() {

			final IsbnCode sut = new IsbnCode(null);

			assertThat(sut.getValue(), is(""));
		}
	}

	public static class ISBN13形式＿ハイフンつきの場合 {

		private IsbnCode sut = null;

		@Before
		public void setUp() {

			sut = new IsbnCode("978-47981-219-63");
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}

		@Test
		public void getOnlyNumbersでハイフンなしの値が取得できる() {

			assertThat(sut.getOnlyCodes(), is("9784798121963"));
		}
	}

	public static class ISBN13形式＿ハイフンなしの場合 {

		private IsbnCode sut = null;

		private final String expectedValue = "9784274068560";

		@Before
		public void setUp() {

			sut = new IsbnCode(expectedValue);
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}

		@Test
		public void getOnlyNumbersで元の値が取得できる() {

			assertThat(sut.getOnlyCodes(), is(expectedValue));
		}
	}

	public static class ISBN10形式＿ハイフンありの場合 {

		private IsbnCode sut = null;

		@Before
		public void setUp() {

			sut = new IsbnCode("479-8121-967");
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}

		@Test
		public void getOnlyNumbersで元の値が取得できる() {

			assertThat(sut.getOnlyCodes(), is("4798121967"));
		}
	}

	public static class ISBN10形式＿ハイフンなしの場合 {

		private IsbnCode sut = null;

		private final String expectedValue = "477415377X";

		@Before
		public void setUp() {

			sut = new IsbnCode(expectedValue);
		}

		@Test
		public void validateを実行するとエラーがない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}

		@Test
		public void getOnlyNumbersで元の値が取得できる() {

			assertThat(sut.getOnlyCodes(), is(expectedValue));
		}
	}

	public static class ISBNの形式が不正な場合 {

		private IsbnCode sut = null;

		@Before
		public void setUp() {

			sut = new IsbnCode("12345");
		}

		@Test
		public void validateを実行するとISBN形式不正エラーになる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.ISBN_STYLE_INVALID)));
		}
	}

	public static class Nullを設定した場合 {

		private IsbnCode sut = null;

		@Before
		public void setUp() {

			sut = new IsbnCode(null);
		}

		@Test
		public void validateを実行すると未入力チェックエラーとなる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.VALUE_IS_EMPTY, new String[] {
				IsbnCode.LABEL
			})));
		}

		@Test
		public void getOnlyNumbersでブランクが取得できる() {

			assertThat(sut.getOnlyCodes(), is(""));
		}
	}

	public static class 入力バイト数のチェック {

		@Test
		public void 入力バイト数が20バイトの場合＿入力チェックにならない() {

			final IsbnCode sut = new IsbnCode("97-84-79-81-21-9-6-3");

			final ValidateErrors errors = sut.validate();
			assertThat(errors.hasNotError(), is(true));
		}

		@Test
		public void 入力バイト数が21バイトの場合＿バイト数チェックエラーとなる() {

			final IsbnCode sut = new IsbnCode("12-34-56-78-90-1-2-34");

			final ValidateErrors errors = sut.validate();
			final List<ErrorInfo> actualErrorList = errors.toList();

			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					IsbnCode.LABEL, String.valueOf(20)
			})));
		}
	}

}
