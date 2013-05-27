package jp.glory.bookshelf.domain.shelf.rule;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Test;

public class ShelfMovingBookRuleTest {

	@Test
	public void fromにmovingListの本が全て含まれている場合＿かつ＿toがNulではない場合_エラーにならない() {

		final List<Book> moveBookList = createBookList(new long[] {
				30000001, 30000002
		});

		final ShelfId fromShelfId = new ShelfId(1000L);
		final List<Book> fromTempList = createBookList(new long[] {
				10000001, 10000002, 10000002
		});

		final List<Book> fromList = new ArrayList<>();
		fromList.addAll(fromTempList);
		fromList.addAll(moveBookList);

		final ShelfId toShelfId = new ShelfId(2000L);
		final List<Book> toList = createBookList(new long[] {
				20000001, 20000002
		});

		final Shelf fromShelf = new Shelf(fromShelfId, fromList);
		final Shelf toShelf = new Shelf(toShelfId, toList);

		final List<BookId> moveBookIdList = new ArrayList<>();
		for (final Book moveBook : moveBookList) {

			moveBookIdList.add(moveBook.getBookId());
		}

		final ShelfMovingBookRule sut = new ShelfMovingBookRule(fromShelf, toShelf, moveBookIdList);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasNotError(), is(true));
	}

	@Test
	public void fromに対象の本がない場合＿移動エラーになる() {

		final List<Book> moveBookList = createBookList(new long[] {
			30000001
		});

		final ShelfId fromShelfId = new ShelfId(1000L);
		final List<Book> fromList = createBookList(new long[] {
				10000001, 10000002, 10000002
		});

		final ShelfId toShelfId = new ShelfId(2000L);
		final List<Book> toList = createBookList(new long[] {
				20000001, 20000002
		});

		final Shelf fromShelf = new Shelf(fromShelfId, fromList);
		final Shelf toShelf = new Shelf(toShelfId, toList);

		final List<BookId> moveBookIdList = new ArrayList<>();
		for (final Book moveBook : moveBookList) {

			moveBookIdList.add(moveBook.getBookId());
		}

		final ShelfMovingBookRule sut = new ShelfMovingBookRule(fromShelf, toShelf, moveBookIdList);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));

		final List<ErrorInfo> actualValidateList = errors.toList();
		assertThat(actualValidateList.size(), is(1));
		assertThat(actualValidateList.get(0), is(invalidBy(ErrorMessage.BOOK_MOVE_ERROR)));

	}

	@Test
	public void fromの本棚がない場合＿移動エラーになる() {

		final List<Book> moveBookList = createBookList(new long[] {
			30000001
		});

		final ShelfId toShelfId = new ShelfId(2000L);
		final Shelf toShelf = new Shelf(toShelfId, new ArrayList<Book>());

		final List<BookId> moveBookIdList = new ArrayList<>();
		for (final Book moveBook : moveBookList) {

			moveBookIdList.add(moveBook.getBookId());
		}

		final ShelfMovingBookRule sut = new ShelfMovingBookRule(null, toShelf, moveBookIdList);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));

		final List<ErrorInfo> actualValidateList = errors.toList();
		assertThat(actualValidateList.size(), is(1));
		assertThat(actualValidateList.get(0), is(invalidBy(ErrorMessage.BOOK_MOVE_ERROR)));

	}

	@Test
	public void toに対象の本がない場合＿移動エラーになる() {

		final List<Book> moveBookList = createBookList(new long[] {
			30000001
		});

		final ShelfId fromShelfId = new ShelfId(1000L);
		final Shelf fromShelf = new Shelf(fromShelfId, new ArrayList<Book>());

		final List<BookId> moveBookIdList = new ArrayList<>();
		for (final Book moveBook : moveBookList) {

			moveBookIdList.add(moveBook.getBookId());
		}

		final ShelfMovingBookRule sut = new ShelfMovingBookRule(fromShelf, null, moveBookIdList);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));

		final List<ErrorInfo> actualValidateList = errors.toList();
		assertThat(actualValidateList.size(), is(1));
		assertThat(actualValidateList.get(0), is(invalidBy(ErrorMessage.BOOK_MOVE_ERROR)));

	}

	@Test
	public void 移動対象の本がない場合＿移動エラーになる() {

		final List<Book> moveBookList = createBookList(new long[] {});

		final ShelfId fromShelfId = new ShelfId(1000L);

		final ShelfId toShelfId = new ShelfId(2000L);

		final Shelf fromShelf = new Shelf(fromShelfId, new ArrayList<Book>());
		final Shelf toShelf = new Shelf(toShelfId, new ArrayList<Book>());

		final List<BookId> moveBookIdList = new ArrayList<>();
		for (final Book moveBook : moveBookList) {

			moveBookIdList.add(moveBook.getBookId());
		}

		final ShelfMovingBookRule sut = new ShelfMovingBookRule(fromShelf, toShelf, moveBookIdList);

		final ValidateErrors errors = sut.validate();
		assertThat(errors.hasError(), is(true));

		final List<ErrorInfo> actualValidateList = errors.toList();
		assertThat(actualValidateList.size(), is(1));
		assertThat(actualValidateList.get(0), is(invalidBy(ErrorMessage.BOOK_MOVE_ERROR)));

	}

	private List<Book> createBookList(final long[] bookIdValues) {

		final List<Book> bookList = new ArrayList<>();

		for (final long bookIdValue : bookIdValues) {

			final BookId bookId = new BookId(bookIdValue);
			bookList.add(new Book(bookId));
		}

		return bookList;
	}
}
