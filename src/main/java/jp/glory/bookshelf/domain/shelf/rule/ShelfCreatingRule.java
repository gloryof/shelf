package jp.glory.bookshelf.domain.shelf.rule;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;

/**
 * 本棚作成ルール
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfCreatingRule {

	/** 本棚 */
	private final Shelf shelf;

	/**
	 * コンストラクタ
	 * 
	 * @param shelf 本棚
	 */
	public ShelfCreatingRule(final Shelf shelf) {

		this.shelf = shelf;
	}

	/**
	 * 検証を行う
	 * 
	 * @return エラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = shelf.validate();

		if (!shelf.isNew()) {

			errors.add(new ErrorInfo(ErrorMessage.CREATED_ERROR, new String[] {
				Shelf.LABEL
			}));
		}

		return errors;
	}
}
