package jp.glory.bookshelf.test.matcher;

import jp.glory.bookshelf.domain.book.entity.Book;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * 本Matcher
 * 
 * @author Junki yamada
 * 
 */
public class BookMatcher extends BaseMatcher<Book> {

	/** 期待値 */
	private final Book expected;

	/** 実測 */
	private Book actual;

	/**
	 * コンストラクタ
	 * 
	 * @param expected 期待値
	 */
	private BookMatcher(final Book expected) {

		this.expected = expected;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(final Object item) {

		if (item == null) {

			return false;
		}

		if (!(item instanceof Book)) {

			return false;
		}

		actual = (Book) item;

		if (!actual.getParentShelfId().isSame(expected.getParentShelfId())) {

			return false;
		}

		if ((actual.getBookId().getValue() != expected.getBookId().getValue())) {

			return false;
		}

		if (!StringUtils.equals(actual.getIsbnCode().getValue(), expected.getIsbnCode().getValue())) {

			return false;
		}

		if (!StringUtils.equals(actual.getTitle().getValue(), expected.getTitle().getValue())) {

			return false;
		}

		if ((actual.getPrice().getValue() != expected.getPrice().getValue())) {

			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void describeTo(final Description description) {

		description.appendText("expected : ");

		description.appendValue(getBookValues(expected));

		description.appendText("actual : ");

		if (actual == null) {

			description.appendValue("null");
		} else {

			if (!(actual instanceof Book)) {

				description.appendValue("different class. [" + actual.getClass().getName() + "]");
			} else {

				description.appendValue(getBookValues(actual));
			}
		}
	}

	/***
	 * 本の値を出力する
	 * 
	 * @param value
	 * @return
	 */
	private String getBookValues(final Book value) {

		final StringBuilder builder = new StringBuilder();

		builder.append("[ParentShelfId=" + value.getParentShelfId().getValue() + "]");

		builder.append("[BookId=" + value.getBookId().getValue() + "]");

		builder.append("[IsbnCode=" + value.getIsbnCode().getValue() + "]");

		builder.append("[Title=" + value.getTitle().getValue() + "]");

		builder.append("[Price=" + value.getPrice().getValue() + "]");

		return builder.toString();
	}

	/**
	 * 本の検証
	 * 
	 * @param expected 期待値
	 * @return Mathcerオブジェクト
	 */
	public static BookMatcher sameBook(final Book expected) {

		return new BookMatcher(expected);
	}
}
