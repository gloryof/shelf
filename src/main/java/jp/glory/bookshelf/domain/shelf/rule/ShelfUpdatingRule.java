package jp.glory.bookshelf.domain.shelf.rule;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;

/**
 * 本棚更新ルール
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfUpdatingRule {

	/** 本棚 */
	private final Shelf shelf;

	/**
	 * コンストラクタ
	 * 
	 * @param shelf 本棚
	 */
	public ShelfUpdatingRule(final Shelf shelf) {

		this.shelf = shelf;
	}

	/**
	 * 検証を行う
	 * 
	 * @return エラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = shelf.validate();

		if (shelf.isNew()) {

			errors.add(new ErrorInfo(ErrorMessage.UPDATE_ERROR, new String[] {
				Shelf.LABEL
			}));
		}

		return errors;
	}
}
