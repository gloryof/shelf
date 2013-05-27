package jp.glory.bookshelf.domain.book.entity;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookTest {

	public static class 初期状態の場合 {

		private Book sut = null;

		@Before
		public void setUp() {

			sut = new Book();
		}

		@Test
		public void 各項目は未設定() {

			final BookId actualBookId = sut.getBookId();
			final IsbnCode actualIsbnCode = sut.getIsbnCode();
			final Price actualPrice = sut.getPrice();
			final Title actualTitle = sut.getTitle();
			final ShelfId actualParentShelfId = sut.getParentShelfId();

			assertThat(sut.isNew(), is(true));
			assertThat(actualBookId.isNumbered(), is(false));
			assertThat(actualIsbnCode.getValue(), is(""));
			assertThat(actualPrice.getValue(), is(0));
			assertThat(actualTitle.getValue(), is(""));
			assertThat(actualParentShelfId.getValue(), is(0L));
		}
	}

	public static class 全ての項目がエラー {

		private Book sut = null;

		@Before
		public void setUp() {

			sut = new Book();
			sut.setIsbnCode(new IsbnCode(""));
			sut.setPrice(new Price(-1));
			sut.setTitle(new Title(""));
			sut.setParentShelfId(new ShelfId());
		}

		@Test
		public void validateで全項目エラーとなる() {

			final ValidateErrors actualErrors = sut.validate();
			final List<ErrorInfo> actualErrorList = actualErrors.toList();

			assertThat(actualErrorList.size(), is(4));

			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.VALUE_IS_NOT_SETTING, new String[] {
				ShelfId.LABEL
			})));
			assertThat(actualErrorList.get(1), is(invalidBy(ErrorMessage.VALUE_IS_EMPTY, new String[] {
				IsbnCode.LABEL
			})));
			assertThat(actualErrorList.get(2), is(invalidBy(ErrorMessage.VALUE_IS_EMPTY, new String[] {
				Title.LABEL
			})));
			assertThat(actualErrorList.get(3), is(invalidBy(ErrorMessage.MIN_VALUE_ERROR, new String[] {
					Price.LABEL, "0"
			})));
		}
	}

	public static class 全ての値が正常な値 {

		private Book sut = null;

		private BookId expectedBookId = null;

		private IsbnCode expectedIsbnCode = null;;

		private Title expectedTitle = null;

		private Price expectedPrice = null;

		private ShelfId expectedParentShelfId = null;

		@Before
		public void setUp() {

			expectedBookId = new BookId(123456L);
			expectedIsbnCode = new IsbnCode("9784274068850");
			expectedPrice = new Price(1500);
			expectedTitle = new Title("テスト");
			expectedParentShelfId = new ShelfId(654321L);

			sut = new Book(expectedBookId);
			sut.setIsbnCode(expectedIsbnCode);
			sut.setTitle(expectedTitle);
			sut.setPrice(expectedPrice);
			sut.setParentShelfId(expectedParentShelfId);
		}

		@Test
		public void 取得値の確認() {

			final BookId actualBookId = sut.getBookId();
			final IsbnCode actualIsbnCode = sut.getIsbnCode();
			final Title actualTitle = sut.getTitle();
			final Price actualPrice = sut.getPrice();
			final ShelfId actualParentShelfId = sut.getParentShelfId();

			assertThat(sut.isNew(), is(false));
			assertThat(actualBookId.getValue(), is(expectedBookId.getValue()));
			assertThat(actualIsbnCode.getValue(), is(expectedIsbnCode.getValue()));
			assertThat(actualTitle.getValue(), is(expectedTitle.getValue()));
			assertThat(actualPrice.getValue(), is(expectedPrice.getValue()));
			assertThat(actualParentShelfId.getValue(), is(expectedParentShelfId.getValue()));
		}

		@Test
		public void validateでエラーがない() {

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError.hasNotError(), is(true));
		}
	}
}
