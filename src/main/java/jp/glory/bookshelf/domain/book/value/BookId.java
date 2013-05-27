package jp.glory.bookshelf.domain.book.value;

/**
 * 本ID
 * 
 * @author Junki Yamada
 * 
 */
public class BookId {

	/** 値 */
	private final long value;

	/** 採番済フラグ */
	private final boolean nubered;

	/**
	 * コンストラクタ
	 */
	public BookId() {

		this.value = 0;
		this.nubered = false;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public BookId(final Long value) {

		if (value != null) {

			this.value = value;
			this.nubered = true;
		} else {

			this.value = 0;
			this.nubered = false;
		}
	}

	/**
	 * 採番されているかを判定する
	 * 
	 * @return 採番されている場合：true、詐番されていない場合：false
	 */
	public boolean isNumbered() {

		return nubered;
	}

	/**
	 * @return value
	 */
	public long getValue() {
		return value;
	}

	/**
	 * IDが同じかを判定する
	 * 
	 * @param compareId 比較ID
	 * @return 同じ場合：true、違う場合：false
	 */
	public boolean isSame(final BookId compareId) {

		if (!isNumbered()) {

			return false;
		}

		if (compareId == null) {

			return false;
		}

		return (this.value == compareId.getValue());
	}

}
