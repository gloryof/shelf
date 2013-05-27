package jp.glory.bookshelf.common.expection;

import jp.glory.bookshelf.common.validate.ValidateErrors;

/**
 * 業務例外
 * 
 * @author Junki Yamada
 * 
 */
public class BusinessException extends Exception {

	/**
	 * シリアルバージョンUID
	 */
	private static final long serialVersionUID = -3407127629497244780L;

	/** エラー */
	private final ValidateErrors errors;

	/**
	 * コンストラクタ
	 * 
	 * @param errors エラー
	 */
	public BusinessException(final ValidateErrors errors) {

		this.errors = errors;
	}

	/**
	 * @return errors
	 */
	public ValidateErrors getErrors() {
		return errors;
	}
}
