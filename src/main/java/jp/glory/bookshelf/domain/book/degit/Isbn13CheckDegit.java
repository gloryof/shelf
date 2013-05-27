package jp.glory.bookshelf.domain.book.degit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ISBN13チェックディジット
 * 
 * @author Junki Yamada
 * 
 */
public class Isbn13CheckDegit extends IsbnCheckDegit {

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public Isbn13CheckDegit(final String value) {

		super(value, CheckDegitConf.ISBN13);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<Integer, String> createConvertMap() {

		final Map<Integer, String> tempMap = new HashMap<>();

		tempMap.put(10, "0");

		return Collections.unmodifiableMap(tempMap);
	}
}
