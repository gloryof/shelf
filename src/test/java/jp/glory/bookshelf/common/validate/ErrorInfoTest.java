package jp.glory.bookshelf.common.validate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;

import org.junit.Test;

public class ErrorInfoTest {

	private ErrorInfo sut = null;

	@Test
	public void メッセージパラメータが指定されている場合() {

		final String expectedColumn = "テスト";
		final String expectedMessage = expectedColumn + "を入力してください。";

		sut = new ErrorInfo(ErrorMessage.VALUE_IS_EMPTY, new String[] {
			"テスト"
		});

		assertThat(sut.getMessage(), is(expectedMessage));
	}

	@Test
	public void メッセージパラメータが指定されていない場合() {

		final ErrorMessage expectedMessage = ErrorMessage.ISBN_STYLE_INVALID;

		sut = new ErrorInfo(expectedMessage);

		assertThat(sut.getMessage(), is(expectedMessage.getTemplate()));
	}
}
