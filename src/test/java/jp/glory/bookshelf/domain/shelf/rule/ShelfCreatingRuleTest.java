package jp.glory.bookshelf.domain.shelf.rule;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Test;

public class ShelfCreatingRuleTest {

	@Test
	public void 入力エラーがない未採番の本棚場合＿エラーにならない() throws BusinessException {

		final Shelf shelf = new Shelf();
		shelf.setName(new Name("テスト"));

		final ShelfCreatingRule sut = new ShelfCreatingRule(shelf);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasNotError(), is(true));
	}

	@Test
	public void 入力エラーがある未採番の本棚場合＿入力チェックエラーになる() throws BusinessException {

		final Shelf shelf = new Shelf();

		final ShelfCreatingRule sut = new ShelfCreatingRule(shelf);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));
	}

	@Test
	public void 採番済みの本棚の場合＿登録エラーになる() {

		final Shelf shelf = new Shelf(new ShelfId(12345L), new ArrayList<Book>());
		shelf.setName(new Name("テスト"));

		final ShelfCreatingRule sut = new ShelfCreatingRule(shelf);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));

		final List<ErrorInfo> errorList = errors.toList();
		assertThat(errorList.size(), is(1));
		assertThat(errorList.get(0), is(invalidBy(ErrorMessage.CREATED_ERROR, new String[] {
			Shelf.LABEL
		})));
	}
}
