package jp.glory.bookshelf.test.matcher;

import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * 本棚Matcher
 * 
 * @author Junki yamada
 * 
 */
public class ShelfMatcher extends BaseMatcher<Shelf> {

	/** 期待値 */
	private final Shelf expected;

	/** 実測 */
	private Shelf actual;

	/**
	 * コンストラクタ
	 * 
	 * @param expected 期待値
	 */
	private ShelfMatcher(final Shelf expected) {

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

		if (!(item instanceof Shelf)) {

			return false;
		}

		actual = (Shelf) item;

		if ((actual.getShelfId().getValue() != expected.getShelfId().getValue())) {

			return false;
		}

		if (!StringUtils.equals(actual.getName().getValue(), expected.getName().getValue())) {

			return false;
		}

		if (expected.getBookCount() != actual.getBookCount()) {

			return false;
		}

		final List<Book> actualBookList = actual.getBookList();
		final List<Book> expectedBookList = expected.getBookList();
		for (int i = 0; i < expected.getBookCount(); i++) {

			final Book actualBook = actualBookList.get(i);
			final Book expectedBook = expectedBookList.get(i);

			if (!actualBook.getBookId().isSame(expectedBook.getBookId())) {

				return false;
			}
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void describeTo(final Description description) {

		description.appendText("expected : ");

		description.appendValue(getShelfValue(expected));

		description.appendText("actual : ");

		if (actual == null) {

			description.appendValue("null");
		} else {

			if (!(actual instanceof Shelf)) {

				description.appendValue("different class. [" + actual.getClass().getName() + "]");
			} else {

				description.appendValue(getShelfValue(actual));
			}
		}
	}

	/***
	 * 本の値を出力する
	 * 
	 * @param value
	 * @return
	 */
	private String getShelfValue(final Shelf value) {

		final StringBuilder builder = new StringBuilder();

		builder.append("[ShelfId=" + value.getShelfId().getValue() + "]");

		builder.append("[Name=" + value.getName().getValue() + "]");

		builder.append("[Title=");

		boolean isNotFirst = false;
		for (final Book book : value.getBookList()) {

			if (isNotFirst) {
				builder.append(",");
			}

			builder.append(book.getBookId().getValue());

			isNotFirst = true;
		}
		builder.append("]");

		return builder.toString();
	}

	/**
	 * 本棚の検証
	 * 
	 * @param expected 期待値
	 * @return Mathcerオブジェクト
	 */
	public static ShelfMatcher sameShelf(final Shelf expected) {

		return new ShelfMatcher(expected);
	}
}
