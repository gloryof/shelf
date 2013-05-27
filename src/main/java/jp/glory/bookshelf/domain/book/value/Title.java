package jp.glory.bookshelf.domain.book.value;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;

import org.apache.commons.lang3.StringUtils;

/**
 * タイトル
 * 
 * @author Junki Yamada
 * 
 */
public class Title {

	/** 項目名ラベル */
	public static final String LABEL = "タイトル";

	/** 最大バイト数 */
	private static final int MAX_BTYES_LENGTH = 150;

	/** 値 */
	private final String value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public Title(final String value) {

		if (value != null) {

			this.value = value;
		} else {

			this.value = "";
		}
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 入力チェック
	 * 
	 * @return 入力チェックエラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = new ValidateErrors();
		if (StringUtils.isEmpty(value)) {

			errors.add(new ErrorInfo(ErrorMessage.VALUE_IS_EMPTY, new String[] {
				LABEL
			}));

			return errors;
		}

		if (MAX_BTYES_LENGTH < value.getBytes().length) {

			errors.add(new ErrorInfo(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					LABEL, String.valueOf(MAX_BTYES_LENGTH)
			}));
		}
		return errors;
	}
}
