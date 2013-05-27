package jp.glory.bookshelf.domain.book.rule;

import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.value.BookId;

/**
 * 本リスト削除時のルール
 * 
 * @author Junki Yamada
 * 
 */
public class BookListDeletingRule {

	/** 本IDリスト */
	private final List<BookId> bookIdList;

	/**
	 * コンストラクタ
	 * 
	 * @param bookIdList 本IDリスト
	 */
	public BookListDeletingRule(final List<BookId> bookIdList) {

		this.bookIdList = bookIdList;
	}

	/**
	 * 検証を行う
	 * 
	 * @return エラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = new ValidateErrors();

		if (hasError()) {

			errors.add(new ErrorInfo(ErrorMessage.BOOK_DELETE_ERROR));
		}

		return errors;
	}

	/**
	 * エラーがあるか判定する
	 * 
	 * @return エラーがある場合：true、エラーがない場合:false
	 */
	private boolean hasError() {

		if (bookIdList == null) {

			return true;
		}

		if (bookIdList.isEmpty()) {

			return true;
		}

		for (final BookId bookId : bookIdList) {

			if (bookId == null) {

				return true;
			}

			if (!bookId.isNumbered()) {

				return true;
			}
		}

		return false;
	}
}
