package jp.glory.bookshelf.domain.shelf.rule;

import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本棚削除ルール
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfDelettingRule {

	/** 本棚IDリスト */
	private final List<ShelfId> shelfIdList;

	/**
	 * コンストラクタ
	 * 
	 * @param shelfIdList 本棚IDリスト
	 */
	public ShelfDelettingRule(final List<ShelfId> shelfIdList) {

		this.shelfIdList = shelfIdList;
	}

	/**
	 * 検証を行う
	 * 
	 * @return エラーリスト
	 */
	public ValidateErrors validate() {

		final ValidateErrors errors = new ValidateErrors();

		if (hasError()) {

			final ErrorInfo errorInfo = new ErrorInfo(ErrorMessage.SHELF_DELETE_ERROR);
			errors.add(errorInfo);
		}

		return errors;
	}

	/**
	 * エラーがあるかを判定する
	 * 
	 * @return エラーがある場合：true、エラーがない場合：false
	 */
	private boolean hasError() {

		if (shelfIdList == null) {

			return true;
		}

		if (shelfIdList.isEmpty()) {

			return true;
		}

		for (final ShelfId shelfId : shelfIdList) {

			if (validateShelfId(shelfId)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * 本棚IDにエラーがあるか判定する
	 * 
	 * @return エラーがある場合：true、エラーがない場合:false
	 */
	private boolean validateShelfId(final ShelfId shelfId) {

		if (shelfId == null) {

			return true;
		}

		if (!shelfId.isNumbered()) {

			return true;
		}

		return false;
	}
}
