package jp.glory.bookshelf.domain.book.repository;

import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本リポジトリ
 * 
 * @author Junki Yamada
 * 
 */
public interface BookRepository {

	/**
	 * 新規に本を作成する
	 * 
	 * @param bookId 本ID
	 * @param book 本
	 */
	void create(final BookId bookId, final Book book);

	/**
	 * 本を更新する
	 * 
	 * @param book 本
	 */
	void update(final Book book);

	/**
	 * 本を削除する
	 * 
	 * @param bookId 本ID
	 */
	void delete(final BookId bookId);

	/**
	 * 本を取得する
	 * 
	 * @param bookId 本ID
	 * @return 本
	 */
	Book findById(final BookId bookId);

	/**
	 * 本棚IDから本を取得する
	 * 
	 * @param shelfId 本棚ID
	 * @return 本リスト
	 */
	List<Book> findListByShelfId(final ShelfId shelfId);

	/**
	 * ISBNから本リストを取得する
	 * 
	 * @param isbnCode ISBNコード
	 * @return 本リスト
	 */
	List<Book> findListByIsbnCode(IsbnCode isbnCode);

	/**
	 * 新しい本IDを採番する
	 * 
	 * @return 本ID
	 */
	BookId createNewNumbering();
}
