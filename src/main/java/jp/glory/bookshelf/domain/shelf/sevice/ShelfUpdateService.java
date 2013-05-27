package jp.glory.bookshelf.domain.shelf.sevice;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.common.expection.DataNotFoundException;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.rule.ShelfCreatingRule;
import jp.glory.bookshelf.domain.shelf.rule.ShelfDelettingRule;
import jp.glory.bookshelf.domain.shelf.rule.ShelfMovingBookRule;
import jp.glory.bookshelf.domain.shelf.rule.ShelfUpdatingRule;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本棚更新サービス
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfUpdateService {

	/** リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository リポジトリ
	 */
	public ShelfUpdateService(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * 新しい本棚を作成する
	 * 
	 * @param shelf 本棚
	 * @return 採番後の本棚ID
	 * @throws BusinessException 入力チェックがある場合、業務例外がスローされる
	 */
	public ShelfId createNewSelf(final Shelf shelf) throws BusinessException {

		final ValidateErrors errors = new ShelfCreatingRule(shelf).validate();
		if (errors.hasError()) {

			throw new BusinessException(errors);
		}

		final ShelfId newShelfId = repository.createNewNumbering();
		repository.create(newShelfId, shelf);

		return newShelfId;
	}

	/**
	 * 本棚名を更新する
	 * 
	 * @param shelf 本棚
	 * @throws BusinessException 入力チェックがある場合、業務例外がスローされる
	 */
	public void updateShelfName(final Shelf shelf) throws BusinessException {

		final ValidateErrors errors = new ShelfUpdatingRule(shelf).validate();
		if (errors.hasError()) {

			throw new BusinessException(errors);
		}

		final Shelf updatingShelf = repository.findById(shelf.getShelfId());

		if (updatingShelf == null) {

			throw new DataNotFoundException(Shelf.LABEL);
		}

		updatingShelf.setName(shelf.getName());

		repository.update(updatingShelf);
	}

	/**
	 * 本の移動を行う
	 * 
	 * @param fromShelfId 移動元本棚ID
	 * @param toShelfId 移動先本棚ID
	 * @param moveBookIdList 移動対象本IDリスト
	 * @throws BusinessException 移動対象がない場合例外をスローする
	 */
	public void move(final ShelfId fromShelfId, final ShelfId toShelfId, final List<BookId> moveBookIdList)
			throws BusinessException {

		final Shelf fromShelf = repository.findById(fromShelfId);
		final Shelf toShelf = repository.findById(toShelfId);

		final ValidateErrors errors = new ShelfMovingBookRule(fromShelf, toShelf, moveBookIdList).validate();

		if (errors.hasError()) {

			throw new BusinessException(errors);
		}

		moveBookList(fromShelf, toShelf, moveBookIdList);
	}

	/**
	 * 本を本棚に追加する
	 * 
	 * @param shelfId 本棚ID
	 * @param bookId 本ID
	 */
	public void add(final ShelfId shelfId, final BookId bookId) {

		repository.addBook(shelfId, bookId);
	}

	/**
	 * 本棚を削除する
	 * 
	 * @param shelfIdList 削除対象本棚IDリスト
	 * @throws BusinessException 業務例外
	 */
	public void delete(final List<ShelfId> shelfIdList) throws BusinessException {

		final ShelfDelettingRule rule = new ShelfDelettingRule(shelfIdList);
		final ValidateErrors errors = rule.validate();
		if (errors.hasError()) {

			throw new BusinessException(errors);
		}

		final List<BookId> bookIdList = new ArrayList<>();
		for (final ShelfId shelfId : shelfIdList) {

			final Shelf shelf = repository.findById(shelfId);

			bookIdList.addAll(shelf.getBookIdList());

			repository.removeAllBooks(shelfId);
			repository.delete(shelfId);
		}

		final ShelfId moveShelfId = ShelfId.getUnclassifiedShelfsId();
		for (final BookId bookId : bookIdList) {

			repository.addBook(moveShelfId, bookId);
		}
	}

	/**
	 * 本リストを移動する
	 * 
	 * @param fromShelf 移動元本棚
	 * @param toShelf 移動先本棚
	 * @param moveBookIdList 本リスト
	 */
	private void moveBookList(final Shelf fromShelf, final Shelf toShelf, final List<BookId> moveBookIdList) {

		for (final BookId moveBookId : moveBookIdList) {

			final Book book = fromShelf.getBook(moveBookId);

			toShelf.add(book);
			fromShelf.remove(moveBookId);
		}

		repository.updateBooks(toShelf);
		repository.updateBooks(fromShelf);
	}

}
