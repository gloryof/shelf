package jp.glory.bookshelf.domain.book.rule;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Test;

public class BookCreatingRuleTest {

	@Test
	public void 未採番かつ全てが正常に入力されている場合_エラーがない() throws BusinessException {

		final Book entryBook = createNewBookData(new BookId());
		final BookCreatingRule sut = new BookCreatingRule(entryBook);

		final ValidateErrors errors = sut.validate();
		assertThat(errors.hasNotError(), is(true));
	}

	@Test
	public void 未採番かつ入力チェックエラーがある場合_入力チェックエラーになる() throws BusinessException {

		final Book entryBook = createNewBookData(new BookId());
		entryBook.setTitle(new Title(""));
		final BookCreatingRule sut = new BookCreatingRule(entryBook);

		final ValidateErrors errors = sut.validate();
		assertThat(errors.hasError(), is(true));
	}

	@Test
	public void 採番済みの場合_登録エラーになる() {

		final Book entryBook = createNewBookData(new BookId(123456L));
		final BookCreatingRule sut = new BookCreatingRule(entryBook);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));

		final List<ErrorInfo> errorList = errors.toList();

		assertThat(errorList.size(), is(1));
		assertThat(errorList.get(0), is(invalidBy(ErrorMessage.CREATED_ERROR, new String[] {
			Book.LABEL
		})));
	}

	private Book createNewBookData(final BookId bookId) {

		final IsbnCode isbnCode = new IsbnCode("978-4798121963");
		final Title title = new Title("エリック・エヴァンスのドメイン駆動設計");
		final Price price = new Price(5460);
		final ShelfId parentShelfId = new ShelfId(123456L);

		final Book book = new Book(bookId);

		book.setIsbnCode(isbnCode);
		book.setTitle(title);
		book.setPrice(price);
		book.setParentShelfId(parentShelfId);

		return book;
	}
}
