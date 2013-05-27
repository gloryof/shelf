package jp.glory.bookshelf.domain.book.degit;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * ISBNチェックディジット
 * 
 * @author Junki Yamada
 * 
 */
public abstract class IsbnCheckDegit {

	/** 値 */
	private final String value;

	/** トータル */
	private final int total;

	/** チェックディジット設定 */
	private final CheckDegitConf conf;

	/** 変換マップ */
	private final Map<Integer, String> convertMap;

	/**
	 * ISBNチェックディジットオブジェクトを作成する
	 * 
	 * @param value 値
	 * @return ISBNチェックディジットオブジェクト
	 */
	public static IsbnCheckDegit create(final String value) {

		final CheckDegitConf conf = CheckDegitConf.getCheckDegitConf(value);

		if (CheckDegitConf.ISBN10.equals(conf)) {

			return new Isbn10CheckDegit(value);
		}

		if (CheckDegitConf.ISBN13.equals(conf)) {

			return new Isbn13CheckDegit(value);
		}

		return new InvalidCheckDegit(value);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public IsbnCheckDegit(final String value, final CheckDegitConf conf) {

		this.conf = conf;

		this.value = value;
		this.total = calculateTotal();

		convertMap = createConvertMap();
	}

	/**
	 * 正しい値かを検証する
	 * 
	 * @return 正しい値の場合:true、正しくない値の場合：false
	 */
	public boolean isValid() {

		final String calculateValue = calculateCheckDegit(convertMap);
		final String checkDegit = value.substring(conf.getCharLength() - 1);

		return StringUtils.equals(calculateValue, checkDegit);
	}

	/**
	 * トータルを計算する
	 * 
	 * @return トータル
	 */
	protected int calculateTotal() {

		final int[] weight = conf.getWeight();

		int total = 0;
		for (int i = 0; i < weight.length; i++) {

			final int number = Integer.valueOf(value.substring(i, i + 1));
			total += number * weight[i];
		}

		return total;
	}

	/**
	 * 変換マップ作成処理
	 * 
	 * @return 変換マップ
	 */
	protected abstract Map<Integer, String> createConvertMap();

	/**
	 * チェックディジットの計算をする
	 * 
	 * @param convertMap 結果変換マップ
	 * @return チェックディジット
	 */
	private String calculateCheckDegit(final Map<Integer, String> convertMap) {

		final int checkDegit = calculateBaseCheckDegit();

		if (convertMap.containsKey(checkDegit)) {

			return convertMap.get(checkDegit);
		}

		return String.valueOf(checkDegit);
	}

	/**
	 * ベースとなるチェックディジットを算出する
	 * 
	 * @return チェックディジット
	 */
	private int calculateBaseCheckDegit() {

		final int modulus = conf.getModulus();
		final int modValue = total % modulus;

		return (modulus - modValue);
	}
}
