package jp.glory.bookshelf.domain.shelf.repository;

import java.util.List;

import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本棚リポジトリ
 * 
 * @author Junki Yamada
 * 
 */
public interface ShelfRepository {

	/**
	 * 本棚を新規に作成する
	 * 
	 * @param newId 新規ID
	 * @param shelf 本棚
	 */
	void create(final ShelfId newId, final Shelf shelf);

	/**
	 * 本棚IDを発行する
	 * 
	 * @return 本棚ID
	 */
	ShelfId createNewNumbering();

	/**
	 * 本棚を更新する
	 * 
	 * @param shelf 本棚
	 */
	void update(final Shelf shelf);

	/**
	 * 本棚が持っている本を更新する
	 * 
	 * @param shelf 本棚
	 */
	void updateBooks(final Shelf shelf);

	/**
	 * 本棚を削除する
	 * 
	 * @param shelfId 本棚本棚
	 */
	void delete(final ShelfId shelfId);

	/**
	 * 本棚に含まれる本を全て削除する
	 * 
	 * @param shelfId 本棚ID
	 */
	void removeAllBooks(final ShelfId shelfId);

	/**
	 * 本棚を取得する
	 * 
	 * @param id 本棚ID
	 * @return 本棚
	 */
	Shelf findById(final ShelfId id);

	/**
	 * 本棚のリストを取得する
	 * 
	 * @return 本棚リスト
	 */
	List<Shelf> findAll();

	/**
	 * 本IDから本棚IDを取得する
	 * 
	 * @param bookId 本ID
	 * @return 本棚ID
	 */
	ShelfId findIdByBookId(BookId bookId);

	/**
	 * 本棚に本を追加する
	 * 
	 * @param shelfId 本棚ID
	 * @param bookId 本ID
	 */
	void addBook(ShelfId shelfId, BookId bookId);
}
