package jp.glory.bookshelf.domain.book.degit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ISBN10チェックディジット
 * 
 * @author Junki Yamada
 * 
 */
public class Isbn10CheckDegit extends IsbnCheckDegit {

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public Isbn10CheckDegit(final String value) {

		super(value, CheckDegitConf.ISBN10);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<Integer, String> createConvertMap() {

		final Map<Integer, String> tempMap = new HashMap<>();

		tempMap.put(10, "X");
		tempMap.put(11, "0");

		return Collections.unmodifiableMap(tempMap);
	}
}
