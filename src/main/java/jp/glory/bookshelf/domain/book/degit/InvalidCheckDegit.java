package jp.glory.bookshelf.domain.book.degit;

import java.util.HashMap;
import java.util.Map;

/**
 * 不正チェックディジット
 * 
 * @author Junki Yamada
 * 
 */
public class InvalidCheckDegit extends IsbnCheckDegit {

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public InvalidCheckDegit(final String value) {

		super(value, CheckDegitConf.NOTHING);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int calculateTotal() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<Integer, String> createConvertMap() {

		return new HashMap<Integer, String>();
	}
}
