package jp.glory.bookshelf.domain.book.value;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;

/**
 * 価格
 * 
 * @author Junki Yamada
 * 
 */
public class Price {

	/** ラベル */
	public static final String LABEL = "価格";

	/** 最大バイト数 */
	private static final int MAX_BYTE_LENGTH = 6;

	/** 税率 */
	private static final int TAX = 5;

	/** 値 */
	private final int value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public Price(final int value) {

		this.value = value;
	}

	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 税込価格を計算する
	 * 
	 * @return 税込み価格
	 */
	public int calculateTaxIncluded() {

		if (value < 0) {

			return 0;
		}

		return value + ((value * TAX) / 100);
	}

	/**
	 * 入力チェック
	 * 
	 * @return 入力チェックエラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = new ValidateErrors();

		if (value < 0) {

			errors.add(new ErrorInfo(ErrorMessage.MIN_VALUE_ERROR, new String[] {
					LABEL, "0"
			}));
		}

		final int inputLength = String.valueOf(Math.abs(value)).length();
		if (MAX_BYTE_LENGTH < inputLength) {

			errors.add(new ErrorInfo(ErrorMessage.MAX_LENGTH_OVER, new String[] {
					LABEL, String.valueOf(MAX_BYTE_LENGTH)
			}));
		}

		return errors;
	}
}
