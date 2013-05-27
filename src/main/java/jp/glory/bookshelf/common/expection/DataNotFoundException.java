package jp.glory.bookshelf.common.expection;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;

/**
 * データが存在しなかった場合の例外
 * 
 * @author Junki Yamada
 * 
 */
public class DataNotFoundException extends RuntimeException {

	/**
	 * シリアルバージョンUID
	 */
	private static final long serialVersionUID = 5760242343860063424L;

	/**
	 * コンストラクタ
	 * 
	 * @param dataName データ名
	 */
	public DataNotFoundException(final String dataName) {

		super(DataNotFoundException.createMessage(dataName));
	}

	/**
	 * メッセージ作成
	 * 
	 * @param dataName データ名
	 * @return メッセージ
	 */
	private static String createMessage(final String dataName) {

		final ErrorMessage message = ErrorMessage.DATA_NOT_FOUND;

		final ErrorInfo info = new ErrorInfo(message, new String[] {
			dataName
		});

		return info.getMessage();
	}
}
