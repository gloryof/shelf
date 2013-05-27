package jp.glory.bookshelf.common.validate;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ValidateErrorsTest {

	public static class 初期状態の場合 {

		private ValidateErrors sut = null;

		@Before
		public void setUp() {

			sut = new ValidateErrors();
		}

		@Test
		public void hasNonErrorでtrueが返却される() {

			assertThat(sut.hasNotError(), is(true));
		}

		@Test
		public void hasErrorでfalseが返却される() {

			assertThat(sut.hasError(), is(false));
		}

		@Test
		public void addで検証エラーが追加される() {

			final ErrorMessage expectedMessage = ErrorMessage.VALUE_IS_EMPTY;
			final String[] expectedMessageParam = new String[] {
				"test"
			};
			final ErrorInfo error = new ErrorInfo(expectedMessage, expectedMessageParam);
			sut.add(error);

			assertThat(sut.hasError(), is(true));

			final List<ErrorInfo> errorList = sut.toList();

			assertThat(errorList.size(), is(1));
			assertThat(errorList.get(0), is(invalidBy(expectedMessage, expectedMessageParam)));
		}
	}

	public static class すでに2件のエラーが追加されている場合 {

		private ValidateErrors sut = null;

		private List<ErrorInfo> baseError = null;

		@Before
		public void setUp() {

			sut = new ValidateErrors();
			baseError = new ArrayList<>();
			baseError.add(new ErrorInfo(ErrorMessage.VALUE_IS_EMPTY, new String[] {
				"テスト"
			}));
			baseError.add(new ErrorInfo(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					"テスト2", "20"
			}));

			for (final ErrorInfo error : baseError) {

				sut.add(error);
			}
		}

		@Test
		public void addにValidateErrorsのオブジェクトを指定すると全てが追加される() {

			final ValidateErrors additionalError = new ValidateErrors();
			final List<ErrorInfo> additionalErrorList = new ArrayList<>();
			additionalErrorList.add(new ErrorInfo(ErrorMessage.MIN_VALUE_ERROR, new String[] {
					"テスト3", "8"
			}));
			additionalErrorList.add(new ErrorInfo(ErrorMessage.ISBN_STYLE_INVALID));

			for (final ErrorInfo error : additionalErrorList) {

				additionalError.add(error);
			}

			sut.addAll(additionalError);

			final List<ErrorInfo> expectedList = new ArrayList<>();
			expectedList.addAll(baseError);
			expectedList.addAll(additionalErrorList);

			final int actualListSize = expectedList.size();

			final List<ErrorInfo> actualList = sut.toList();

			assertThat(actualList.size(), is(actualListSize));

			for (int i = 0; i < actualListSize; i++) {

				final ErrorInfo actualError = actualList.get(i);
				final ErrorInfo expectedError = expectedList.get(i);

				assertThat(actualError.getMessage(), is(expectedError.getMessage()));
			}
		}

	}
}
