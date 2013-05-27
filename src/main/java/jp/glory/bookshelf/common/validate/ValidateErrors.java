package jp.glory.bookshelf.common.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 検証エラーリスト
 * 
 * @author Junki Yamada
 * 
 */
public class ValidateErrors {

	/** 検証エラーリスト */
	private final List<ErrorInfo> errorList = new ArrayList<>();

	/**
	 * 検証エラーがないかを判定する
	 * 
	 * @return エラーがない場合：true、エラーがある場合：false
	 */
	public boolean hasNotError() {

		return errorList.isEmpty();
	}

	/**
	 * 検証エラーがあるかを判定する
	 * 
	 * @return エラーがある場合：true、エラーがない場合：false
	 */
	public boolean hasError() {

		return !hasNotError();
	}

	/**
	 * 検証エラーを追加する
	 * 
	 * @param error 検証エラー
	 */
	public void add(final ErrorInfo error) {

		errorList.add(error);
	}

	/**
	 * 検証エラーリストに変換する<br/>
	 * 返却されるリストは変更不可。
	 * 
	 * @return 検証エラーリスト
	 */
	public List<ErrorInfo> toList() {

		return Collections.unmodifiableList(errorList);
	}

	/**
	 * 検証エラーを全て追加する
	 * 
	 * @param additionalError 検証エラーリスト
	 */
	public void addAll(final ValidateErrors additionalError) {

		for (final ErrorInfo error : additionalError.toList()) {

			add(error);
		}
	}

}
