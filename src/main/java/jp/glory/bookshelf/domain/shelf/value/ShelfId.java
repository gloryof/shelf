package jp.glory.bookshelf.domain.shelf.value;

/**
 * 本棚ID
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfId {

	/** 本棚ID */
	public static final String LABEL = "本棚ID";

	/** 値 */
	private final long value;

	/** 採番済フラグ */
	private final boolean nubered;

	/**
	 * コンストラクタ
	 */
	public ShelfId() {

		this.value = 0;
		this.nubered = false;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	public ShelfId(final Long value) {

		if (value != null) {

			this.value = value;
			this.nubered = true;
		} else {

			this.value = 0;
			this.nubered = false;
		}
	}

	/**
	 * 本棚[未分類]の本棚IDを返却する
	 * 
	 * @return 本棚ID
	 */
	public static ShelfId getUnclassifiedShelfsId() {

		return new ShelfId(0L);
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
	 * 同じ本棚IDかを判定する
	 * 
	 * @param compareShelfId 比較本棚ID
	 * @return 同じ場合：true、異なる場合：false
	 */
	public boolean isSame(final ShelfId compareShelfId) {

		if (compareShelfId == null) {

			return false;
		}

		if (!isNumbered()) {

			return false;
		}

		if (!compareShelfId.isNumbered()) {

			return false;
		}

		if (value != compareShelfId.getValue()) {

			return false;
		}

		return true;
	}

	/**
	 * @return value
	 */
	public long getValue() {
		return value;
	}
}
