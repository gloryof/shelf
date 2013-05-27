package jp.glory.bookshelf.domain.book.degit;

import org.apache.commons.lang3.StringUtils;

/**
 * チェックディジットの設定
 * 
 * @author Junki Yamada
 * 
 */
public enum CheckDegitConf {

	ISBN10(10, 11, new int[] {
			10, 9, 8, 7, 6, 5, 4, 3, 2
	}),

	ISBN13(13, 10, new int[] {
			1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3
	}),

	NOTHING(0, 0, new int[] {});

	/** 文字数 */
	private final int charLength;

	/** モジュラス */
	private final int modulus;

	/** ウェイト */
	private final int[] weight;

	/**
	 * コンストラクタ
	 * 
	 * @param charLength 文字長
	 * @param modulus モジュラス
	 * @param weight ウェイト
	 */
	private CheckDegitConf(final int charLength, final int modulus, final int[] weight) {

		this.charLength = charLength;
		this.modulus = modulus;
		this.weight = weight;
	}

	/**
	 * チェックディジットの設定を取得する
	 * 
	 * @param value 値
	 * @return チェックディジットの設定
	 */
	public static CheckDegitConf getCheckDegitConf(final String value) {

		if (StringUtils.isEmpty(value)) {

			return NOTHING;
		}

		final String test = value.substring(0, value.length() - 1);
		if (!test.matches("[0-9]*")) {

			return NOTHING;
		}

		for (final CheckDegitConf conf : CheckDegitConf.values()) {

			if (conf.charLength == value.length()) {

				return conf;
			}
		}

		return NOTHING;
	}

	/**
	 * @return charLength
	 */
	public int getCharLength() {
		return charLength;
	}

	/**
	 * @return modulus
	 */
	public int getModulus() {
		return modulus;
	}

	/**
	 * @return weight
	 */
	public int[] getWeight() {
		return weight;
	}
}
