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
public class PriceTest {

	public static class 設定値が0の場合 {

		private final int expectedValue = 0;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(expectedValue);
		}

		@Test
		public void getValueでコンストラクタのパラメータと同じ値が返却される() {

			assertThat(sut.getValue(), is(expectedValue));
		}

		@Test
		public void calculateTaxIncludedで0が返却される() {

			assertThat(sut.calculateTaxIncluded(), is(expectedValue));
		}

		@Test
		public void validateで入力チェックエラーにならない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class 設定値が1000の場合 {

		private final int baseValue = 1000;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(baseValue);
		}

		@Test
		public void getValueでコンストラクタのパラメータと同じ値が返却される() {

			assertThat(sut.getValue(), is(baseValue));
		}

		@Test
		public void calculateTaxIncludedで税込価格が返却される() {

			assertThat(sut.calculateTaxIncluded(), is(1050));
		}

		@Test
		public void validateで入力チェックエラーにならない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class 設定値が1500の場合 {

		private final int baseValue = 1500;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(baseValue);
		}

		@Test
		public void getValueでコンストラクタのパラメータと同じ値が返却される() {

			assertThat(sut.getValue(), is(baseValue));
		}

		@Test
		public void calculateTaxIncludedで税込価格が返却される() {

			assertThat(sut.calculateTaxIncluded(), is(1575));
		}

		@Test
		public void validateで入力チェックエラーにならない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class 設定値が2999の場合 {

		private final int baseValue = 2999;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(baseValue);
		}

		@Test
		public void getValueでコンストラクタのパラメータと同じ値が返却される() {

			assertThat(sut.getValue(), is(baseValue));
		}

		@Test
		public void calculateTaxIncludedで税込価格が返却される() {

			assertThat(sut.calculateTaxIncluded(), is(3148));
		}

		@Test
		public void validateで入力チェックエラーにならない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}

	}

	public static class 設定値が3555の場合 {

		private final int baseValue = 3555;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(baseValue);
		}

		@Test
		public void getValueでコンストラクタのパラメータと同じ値が返却される() {

			assertThat(sut.getValue(), is(baseValue));
		}

		@Test
		public void calculateTaxIncludedで税込価格が返却される() {

			assertThat(sut.calculateTaxIncluded(), is(3732));
		}

		@Test
		public void validateで入力チェックエラーにならない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}

	}

	public static class 設定値がマイナス1の場合 {

		private final int baseValue = -1;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(baseValue);
		}

		@Test
		public void getValueでコンストラクタのパラメータと同じ値が返却される() {

			assertThat(sut.getValue(), is(baseValue));
		}

		@Test
		public void calculateTaxIncludedで0が返却される() {

			assertThat(sut.calculateTaxIncluded(), is(0));
		}

		@Test
		public void validateで最小値エラーとなる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.MIN_VALUE_ERROR, new String[] {
					Price.LABEL, "0"
			})));
		}

	}

	public static class バイト数が6バイトの場合 {

		private final int baseValue = 123456;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(baseValue);
		}

		@Test
		public void validateで入力チェックエラーにならない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasNotError(), is(true));
		}
	}

	public static class バイト数が7バイトの場合 {

		private final int baseValue = 1234567;

		private Price sut = null;

		@Before
		public void setUp() {

			sut = new Price(baseValue);
		}

		@Test
		public void validateでバイト数チェックエラーになる() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = errors.toList();
			assertThat(actualErrorList.size(), is(1));
			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					Price.LABEL, "6"
			})));
		}
	}
}
