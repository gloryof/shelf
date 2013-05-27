package jp.glory.bookshelf.domain.book.rule;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.value.BookId;

import org.junit.Test;

public class BookListDeletingRuleTest {

	@Test
	public void 削除対象の本リストのサイズが1の場合_エラーにならない() {

		final List<BookId> idList = new ArrayList<>();
		idList.add(new BookId(1L));

		final BookListDeletingRule sut = new BookListDeletingRule(idList);

		final ValidateErrors actualErrors = sut.validate();

		assertThat(actualErrors, is(not(nullValue())));
		assertThat(actualErrors.hasError(), is(false));
	}

	@Test
	public void 削除対象の本リストのサイズが3の場合_エラーにならない() {

		final List<BookId> idList = new ArrayList<>();
		idList.add(new BookId(1L));
		idList.add(new BookId(2L));
		idList.add(new BookId(3L));

		final BookListDeletingRule sut = new BookListDeletingRule(idList);

		final ValidateErrors actualErrors = sut.validate();

		assertThat(actualErrors, is(not(nullValue())));
		assertThat(actualErrors.hasError(), is(false));
	}

	@Test
	public void Nullを渡した場合_エラーになる() {

		final BookListDeletingRule sut = new BookListDeletingRule(null);

		final ValidateErrors actualErrors = sut.validate();

		assertThat(actualErrors, is(not(nullValue())));
		assertThat(actualErrors.hasError(), is(true));

		final List<ErrorInfo> errorList = actualErrors.toList();
		assertThat(errorList.size(), is(1));
		assertThat(errorList.get(0), is(invalidBy(ErrorMessage.BOOK_DELETE_ERROR)));
	}

	@Test
	public void 空のリストを渡した場合_エラーになる() {

		final BookListDeletingRule sut = new BookListDeletingRule(new ArrayList<BookId>());

		final ValidateErrors actualErrors = sut.validate();

		assertThat(actualErrors, is(not(nullValue())));
		assertThat(actualErrors.hasError(), is(true));

		final List<ErrorInfo> errorList = actualErrors.toList();
		assertThat(errorList.size(), is(1));
		assertThat(errorList.get(0), is(invalidBy(ErrorMessage.BOOK_DELETE_ERROR)));
	}

	@Test
	public void 削除対象の本リストにNullが含まれる場合_エラーになる() {

		final List<BookId> idList = new ArrayList<>();
		idList.add(new BookId(1L));
		idList.add(null);
		idList.add(new BookId(3L));

		final BookListDeletingRule sut = new BookListDeletingRule(idList);

		final ValidateErrors actualErrors = sut.validate();

		assertThat(actualErrors, is(not(nullValue())));
		assertThat(actualErrors.hasError(), is(true));

		final List<ErrorInfo> errorList = actualErrors.toList();
		assertThat(errorList.size(), is(1));
		assertThat(errorList.get(0), is(invalidBy(ErrorMessage.BOOK_DELETE_ERROR)));
	}

	@Test
	public void 削除対象の本リストに未採番IDが含まれる場合_エラーになる() {

		final List<BookId> idList = new ArrayList<>();
		idList.add(new BookId(1L));
		idList.add(new BookId());
		idList.add(new BookId(3L));

		final BookListDeletingRule sut = new BookListDeletingRule(idList);

		final ValidateErrors actualErrors = sut.validate();

		assertThat(actualErrors, is(not(nullValue())));
		assertThat(actualErrors.hasError(), is(true));

		final List<ErrorInfo> errorList = actualErrors.toList();
		assertThat(errorList.size(), is(1));
		assertThat(errorList.get(0), is(invalidBy(ErrorMessage.BOOK_DELETE_ERROR)));
	}
}
