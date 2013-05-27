package jp.glory.bookshelf.domain.shelf.entity;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本棚
 * 
 * @author Junki Yamada
 * 
 */
public class Shelf {

	/** ラベル */
	public static final String LABEL = "本棚";

	/** 本棚ID */
	private final ShelfId shelfId;
	/** 本リスト */
	private final List<Book> bookList;

	/** 名前 */
	private Name name = null;

	/**
	 * コンストラクタ
	 */
	public Shelf() {

		this(new ShelfId(), new ArrayList<Book>());
	}

	/**
	 * コンストラクタ
	 * 
	 * @param shelfId 本棚ID
	 * @param bookList 本リスト
	 */
	public Shelf(final ShelfId shelfId, final List<Book> bookList) {

		this.shelfId = shelfId;
		this.bookList = bookList;
		this.name = new Name("");
	}

	/**
	 * 新規の本棚かを判定する
	 * 
	 * @return 新規の本棚の場合：true、新規の本棚ではない場合：false
	 */
	public boolean isNew() {

		return !shelfId.isNumbered();
	}

	/**
	 * 本の数を取得する
	 * 
	 * @return 本の数
	 */
	public int getBookCount() {

		return bookList.size();
	}

	/**
	 * 本を追加する
	 * 
	 * @param book 本
	 */
	public void add(final Book book) {

		bookList.add(book);
	}

	/**
	 * 本を除去する
	 * 
	 * @param bookId 本 ID
	 * @return 除去された場合：true、除去されていない場合：false
	 */
	public boolean remove(final BookId bookId) {

		for (int i = 0; i < bookList.size(); i++) {

			final Book book = bookList.get(i);
			if (book.getBookId().getValue() == bookId.getValue()) {

				bookList.remove(i);
				return true;
			}
		}

		return false;
	}

	/**
	 * 入力チェックを行う
	 * 
	 * @return エラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = new ValidateErrors();

		errors.addAll(name.validate());

		return errors;
	}

	/**
	 * 本を取得する
	 * 
	 * @param bookId 本ID
	 * @return 本
	 */
	public Book getBook(final BookId bookId) {

		for (final Book book : bookList) {

			if (book.getBookId().isSame(bookId)) {

				return book;
			}
		}

		return null;
	}

	/**
	 * 同じ本棚かを判定する
	 * 
	 * @param compareShelfId 本棚ID
	 * @return 同じ本棚の場合：true、違う本棚の場合:false
	 */
	public boolean isSameShelf(final ShelfId compareShelfId) {

		if (isNew()) {

			return false;
		}

		return shelfId.isSame(compareShelfId);
	}

	/**
	 * @return name
	 */
	public Name getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(final Name name) {
		this.name = name;
	}

	/**
	 * @return shelfId
	 */
	public ShelfId getShelfId() {
		return shelfId;
	}

	/**
	 * @return bookList
	 */
	public List<Book> getBookList() {
		return bookList;
	}

	/**
	 * 本IDリストを返却する
	 * 
	 * @return 本IDリスト
	 */
	public List<BookId> getBookIdList() {

		final List<BookId> bookIdList = new ArrayList<>();

		for (final Book book : bookList) {

			bookIdList.add(book.getBookId());
		}

		return bookIdList;
	}

}
