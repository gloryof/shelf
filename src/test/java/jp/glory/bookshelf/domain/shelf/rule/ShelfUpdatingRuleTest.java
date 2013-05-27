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

public class ShelfUpdatingRuleTest {

	@Test
	public void 採番済みで入力チェックエラーがない場合＿エラーがない() throws BusinessException {

		final Shelf shelf = createBeforeData();
		shelf.setName(new Name("更新後"));

		final ShelfUpdatingRule sut = new ShelfUpdatingRule(shelf);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasNotError(), is(true));
	}

	@Test
	public void 採番済みで入力チェックエラーがある場合＿入力チェックエラーになる() throws BusinessException {

		final Shelf shelf = createBeforeData();
		shelf.setName(new Name(""));

		final ShelfUpdatingRule sut = new ShelfUpdatingRule(shelf);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));
	}

	@Test
	public void 未採番の本棚の場合＿更新エラーになる() {

		final Shelf shelf = new Shelf();
		shelf.setName(new Name("テスト"));

		final ShelfUpdatingRule sut = new ShelfUpdatingRule(shelf);

		final ValidateErrors errors = sut.validate();

		assertThat(errors.hasError(), is(true));

		final List<ErrorInfo> errorList = errors.toList();
		assertThat(errorList.size(), is(1));
		assertThat(errorList.get(0), is(invalidBy(ErrorMessage.UPDATE_ERROR, new String[] {
			Shelf.LABEL
		})));

	}

	private Shelf createBeforeData() {

		final ShelfId shelfId = new ShelfId(10001L);
		final List<Book> bookList = new ArrayList<>();
		final Name name = new Name("更新前");

		final Shelf shelf = new Shelf(shelfId, bookList);
		shelf.setName(name);

		return shelf;
	}

}
