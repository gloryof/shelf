package jp.glory.bookshelf.domain.book.rule;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;

/**
 * 本更新時のルール
 * 
 * @author Junki Yamada
 * 
 */
public class BookUpdatingRule {

	/** 本 */
	private final Book book;

	/**
	 * コンストラクタ
	 * 
	 * @param book 本
	 */
	public BookUpdatingRule(final Book book) {

		this.book = book;
	}

	/**
	 * 検証を行う
	 * 
	 * @return エラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = book.validate();

		if (book.isNew()) {

			errors.add(new ErrorInfo(ErrorMessage.UPDATE_ERROR, new String[] {
				Book.LABEL
			}));
		}

		return errors;
	}
}
