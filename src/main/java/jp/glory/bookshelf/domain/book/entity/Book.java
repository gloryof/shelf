package jp.glory.bookshelf.domain.book.entity;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本エンティティ
 * 
 * @author Junki Yamada
 * 
 */
public class Book {

	/** ラベル */
	public static final String LABEL = "本";

	/** ID */
	private final BookId bookId;

	/** 親本棚ID */
	private ShelfId parentShelfId = null;

	/** ISBNコード */
	private IsbnCode isbnCode = null;;

	/** タイトル */
	private Title title = null;

	/** 価格 */
	private Price price = null;

	/**
	 * コンストラクタ
	 */
	public Book() {

		this(new BookId());
	}

	/**
	 * コンストラクタ
	 * 
	 * @param bookId ID
	 */
	public Book(final BookId bookId) {

		this.bookId = bookId;
		isbnCode = new IsbnCode("");
		title = new Title("");
		price = new Price(0);
		parentShelfId = new ShelfId();
	}

	/**
	 * 入力チェック
	 * 
	 * @return 入力チェックエラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = new ValidateErrors();

		if (!parentShelfId.isNumbered()) {

			errors.add(new ErrorInfo(ErrorMessage.VALUE_IS_NOT_SETTING, new String[] {
				ShelfId.LABEL
			}));
		}

		errors.addAll(isbnCode.validate());
		errors.addAll(title.validate());
		errors.addAll(price.validate());

		return errors;
	}

	/**
	 * 新規の本かを判定する
	 * 
	 * @return 新規の本の場合：true、新規の本ではない場合：false
	 */
	public Boolean isNew() {

		return !bookId.isNumbered();
	}

	/**
	 * @return parentShelfId
	 */
	public ShelfId getParentShelfId() {
		return parentShelfId;
	}

	/**
	 * @param parentShelfId セットする parentShelfId
	 */
	public void setParentShelfId(final ShelfId parentShelfId) {
		this.parentShelfId = parentShelfId;
	}

	/**
	 * @return isbnCode
	 */
	public IsbnCode getIsbnCode() {
		return isbnCode;
	}

	/**
	 * @param isbnCode セットする isbnCode
	 */
	public void setIsbnCode(final IsbnCode isbnCode) {
		this.isbnCode = isbnCode;
	}

	/**
	 * @return title
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @param title セットする title
	 */
	public void setTitle(final Title title) {
		this.title = title;
	}

	/**
	 * @return price
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * @param price セットする price
	 */
	public void setPrice(final Price price) {
		this.price = price;
	}

	/**
	 * @return bookId
	 */
	public BookId getBookId() {
		return bookId;
	}

}
