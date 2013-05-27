package jp.glory.bookshelf.common.error;

/**
 * エラーメッセージ
 * 
 * @author Junki Yamada
 * 
 */
public enum ErrorMessage {

	VALUE_IS_EMPTY("{0}を入力してください。"),
	VALUE_IS_NOT_SETTING("{0}を設定してください。"),
	MAX_LENGTH_OVER("{0}の入力桁数は{1}バイト以内で入力してください。"),
	MIN_VALUE_ERROR("{0}は{1}以上を入力してください。"),
	ISBN_STYLE_INVALID("ISBNコードが不正です。入力内容を確認してください。"),
	CREATED_ERROR("この{0}はすでに登録されています。"),
	UPDATE_ERROR("この{0}は未採番です。"),
	SHELF_DELETE_ERROR("本棚の削除に失敗しました。"),
	BOOK_MOVE_ERROR("本の移動に失敗しました。"),
	BOOK_DELETE_ERROR("本リストの削除に失敗しました。"),

	DATA_NOT_FOUND("{0}のデータが見つかりませんでした"), ;

	/** テンプレート */
	private final String template;

	/**
	 * コンストラクタ
	 * 
	 * @param template テンプレート
	 */
	private ErrorMessage(final String template) {

		this.template = template;
	}

	/**
	 * @return template
	 */
	public String getTemplate() {
		return template;
	}
}
