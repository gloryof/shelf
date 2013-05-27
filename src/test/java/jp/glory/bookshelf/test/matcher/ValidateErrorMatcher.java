package jp.glory.bookshelf.test.matcher;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * 検証エラーMatcher
 * 
 * @author Junki yamada
 * 
 */
public class ValidateErrorMatcher extends BaseMatcher<ErrorInfo> {

	/** 期待値 */
	private final ErrorInfo expected;

	/** 実測 */
	private ErrorInfo actual;

	/**
	 * コンストラクタ
	 * 
	 * @param validateMessage 検証メッセージ
	 * @param messageParams メッセージパラメータ
	 */
	private ValidateErrorMatcher(final ErrorMessage validateMessage, final String[] messageParams) {

		expected = new ErrorInfo(validateMessage, messageParams);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(final Object item) {

		if (item == null) {

			return false;
		}

		if (!(item instanceof ErrorInfo)) {

			return false;
		}

		actual = (ErrorInfo) item;
		return StringUtils.equals(actual.getMessage(), expected.getMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void describeTo(final Description description) {

		description.appendText("expected : ");

		description.appendValue(expected.getMessage());

		description.appendText("actual : ");

		if (actual == null) {

			description.appendValue("null");
		} else {

			if (!(actual instanceof ErrorInfo)) {

				description.appendValue("different class. [" + actual.getClass().getName() + "]");
			} else {

				description.appendValue(actual.getMessage());
			}
		}
	}

	/**
	 * 検証エラーチェック
	 * 
	 * @param validateMessage 検証メッセージ
	 * @param messageParams メッセージパラメータ
	 * @return Mathcerオブジェクト
	 */
	public static ValidateErrorMatcher invalidBy(final ErrorMessage validateMessage, final String[] messageParams) {

		return new ValidateErrorMatcher(validateMessage, messageParams);
	}

	/**
	 * 検証エラーチェック
	 * 
	 * @param validateMessage 検証メッセージ
	 * @return Mathcerオブジェクト
	 */
	public static ValidateErrorMatcher invalidBy(final ErrorMessage validateMessage) {

		return new ValidateErrorMatcher(validateMessage, new String[] {});
	}
}
