package jp.glory.bookshelf.common.validate;

import java.text.MessageFormat;

import jp.glory.bookshelf.common.error.ErrorMessage;

/**
 * エラー情報
 * 
 * @author Junki Yamada
 * 
 */
public class ErrorInfo {

	/** メッセージ */
	private final ErrorMessage errorMessage;

	/** メッセージパラメータ */
	private final String[] messageParams;

	/**
	 * コンストラクタ
	 * 
	 * @param errorMessage メッセージ
	 * @param messageParams メッセージパラメータ
	 */
	public ErrorInfo(final ErrorMessage errorMessage, final String[] messageParams) {

		this.errorMessage = errorMessage;
		this.messageParams = messageParams;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param errorMessage メッセージ
	 */
	public ErrorInfo(final ErrorMessage errorMessage) {

		this(errorMessage, new String[] {});
	}

	/**
	 * メッセージを取得する
	 * 
	 * @return メッセージ
	 */
	public String getMessage() {

		final MessageFormat format = new MessageFormat(errorMessage.getTemplate());

		return format.format(messageParams);
	}

}
