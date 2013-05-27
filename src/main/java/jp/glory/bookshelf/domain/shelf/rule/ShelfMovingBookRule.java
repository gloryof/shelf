package jp.glory.bookshelf.domain.shelf.rule;

import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;

/**
 * 本移動時のルール
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfMovingBookRule {

	/** 移動元本棚 */
	private final Shelf fromShelf;

	/** 移動先本棚 */
	private final Shelf toShelf;

	/** 移動対象の本リスト */
	private final List<BookId> moveBookIdList;

	/**
	 * コンストラクタ
	 * 
	 * @param fromShelf 移動元本棚
	 * @param toShelf 移動先本棚
	 * @param moveBookIdList 移動本IDリスト
	 */
	public ShelfMovingBookRule(final Shelf fromShelf, final Shelf toShelf, final List<BookId> moveBookIdList) {

		this.fromShelf = fromShelf;
		this.toShelf = toShelf;
		this.moveBookIdList = moveBookIdList;
	}

	/**
	 * 検証を行う
	 * 
	 * @return エラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = new ValidateErrors();

		if (isDisableMoveBook(fromShelf, toShelf, moveBookIdList)) {

			final ErrorInfo validateError = new ErrorInfo(ErrorMessage.BOOK_MOVE_ERROR);
			errors.add(validateError);
		}

		return errors;
	}

	/**
	 * 本の移動ができるかを確認する
	 * 
	 * @param fromShelf 移動元本棚
	 * @param toShelf 移動先本棚
	 * @param moveBookIdList 移動本IDリスト
	 * @return 移動できない場合：true、移動できる場合：false
	 */
	private boolean isDisableMoveBook(final Shelf fromShelf, final Shelf toShelf, final List<BookId> moveBookIdList) {

		if (fromShelf == null) {

			return true;
		}

		if (toShelf == null) {

			return true;
		}

		if (moveBookIdList.isEmpty()) {

			return true;
		}

		for (final BookId moveBookId : moveBookIdList) {

			final Book book = fromShelf.getBook(moveBookId);
			if (book == null) {

				return true;
			}
		}

		return false;
	}
}
