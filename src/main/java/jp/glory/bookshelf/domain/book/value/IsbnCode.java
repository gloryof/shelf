package jp.glory.bookshelf.domain.book.value;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.degit.IsbnCheckDegit;

import org.apache.commons.lang3.StringUtils;

/**
 * ISBNコード
 * 
 * @author Junki Yamada
 * 
 */
public class IsbnCode {

	/** 項目名ラベル */
	public static final String LABEL = "ISBNコード";

	/** 最大バイト数 */
	private static final int MAX_BTYES_LENGTH = 20;

	/** 値 */
	private final String value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public IsbnCode(final String value) {

		if (value != null) {
			this.value = value;
		} else {

			this.value = "";
		}
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

		if (MAX_BTYES_LENGTH < value.length()) {

			errors.add(new ErrorInfo(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					LABEL, String.valueOf(MAX_BTYES_LENGTH)
			}));
			return errors;
		}

		final IsbnCheckDegit check = IsbnCheckDegit.create(getOnlyCodes());
		if (!check.isValid()) {

			errors.add(new ErrorInfo(ErrorMessage.ISBN_STYLE_INVALID));
			return errors;
		}

		return errors;
	}

	/**
	 * コードのみで取得する
	 * 
	 * @return 数字のみの値
	 */
	public String getOnlyCodes() {

		return value.replaceAll("-", "");
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
}
