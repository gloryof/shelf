package jp.glory.bookshelf.domain.shelf.entity;

import static jp.glory.bookshelf.test.matcher.BookMatcher.*;
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
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfTest {

	public static class コンストラクタのテスト {

		@Test
		public void パラメータなしの場合() {

			final Shelf sut = new Shelf();
			final ShelfId actualId = sut.getShelfId();

			assertThat(sut.isNew(), is(true));
			assertThat(actualId.isNumbered(), is(false));
			assertThat(sut.getBookCount(), is(0));
			assertThat(sut.getName().getValue(), is(""));
		}

		@Test
		public void パラメータありの場合() {

			final ShelfId expectedId = new ShelfId(123456L);
			final List<Book> expectedList = createDefaulList(expectedId);

			final int expectedSize = expectedList.size();

			final Shelf sut = new Shelf(expectedId, expectedList);

			final ShelfId actualId = sut.getShelfId();

			assertThat(sut.isNew(), is(false));
			assertThat(actualId.isNumbered(), is(true));
			assertThat(sut.getBookCount(), is(expectedSize));
			assertThat(sut.getName().getValue(), is(""));

			final List<Book> actualList = sut.getBookList();

			for (int i = 0; i < expectedSize; i++) {

				final Book expectedBook = expectedList.get(i);
				final BookId expectedBookId = expectedBook.getBookId();

				final Book actualBook = actualList.get(i);
				final BookId actualBookId = actualBook.getBookId();

				assertThat(actualBookId.getValue(), is(expectedBookId.getValue()));
			}
		}

	}

	public static class 初期状態の場合 {

		private Shelf sut = null;

		@Before
		public void setUp() {

			sut = new Shelf();
		}

		@Test
		public void addでBookが追加される() {

			final Book expectedBook = new Book(new BookId(123456L));
			expectedBook.setIsbnCode(new IsbnCode("978-4-274-06885-0"));
			expectedBook.setTitle(new Title("テスト"));
			expectedBook.setPrice(new Price(1500));
			expectedBook.setParentShelfId(new ShelfId(10L));

			sut.add(expectedBook);

			assertThat(sut.getBookCount(), is(1));

			final List<Book> actualBookList = sut.getBookList();
			final Book actualBook = actualBookList.get(0);

			assertThat(actualBook, is(sameBook(expectedBook)));
		}

		@Test
		public void validateでエラーが返却される() {

			final ValidateErrors actualErrors = sut.validate();
			final List<ErrorInfo> actualErrorList = actualErrors.toList();

			assertThat(actualErrors.hasError(), is(true));
			assertThat(actualErrorList.size(), is(1));

			assertThat(actualErrorList.get(0), is(invalidBy(ErrorMessage.VALUE_IS_EMPTY, new String[] {
				Name.LABEL
			})));
		}

		@Test
		public void isSameShelfIdに本棚IDを指定するとfalseが返却される() {

			final ShelfId compareShelfId = new ShelfId(1500L);

			assertThat(sut.isSameShelf(compareShelfId), is(false));
		}

		@Test
		public void getBookListで空のリストが返却される() {

			final List<Book> actualBookList = sut.getBookList();

			assertThat(actualBookList, is(not(nullValue())));
			assertThat(actualBookList.isEmpty(), is(true));
		}

		@Test
		public void getBookIdListで空のリストが返却される() {

			final List<BookId> actualBookIdList = sut.getBookIdList();

			assertThat(actualBookIdList, is(not(nullValue())));
			assertThat(actualBookIdList.isEmpty(), is(true));
		}
	}

	public static class すでに3冊本が登録されて＿名前がつけられている場合 {

		private Shelf sut = null;

		private final Name shelfName = new Name("テスト");

		@Before
		public void setUp() {

			final ShelfId shelfId = new ShelfId(1234L);
			sut = new Shelf(shelfId, createDefaulList(shelfId));
			sut.setName(shelfName);
		}

		@Test
		public void getBookListで追加した本リストが返却される() {

			final List<Book> actualBookList = sut.getBookList();
			final List<Book> beforeList = createDefaulList(sut.getShelfId());

			assertThat(actualBookList, is(not(nullValue())));
			for (int i = 0; i < beforeList.size(); i++) {

				assertThat(actualBookList.get(i), is(sameBook(beforeList.get(i))));
			}

		}

		@Test
		public void getBookListIdで追加した本IDリストが返却される() {

			final List<BookId> actualBookIdList = sut.getBookIdList();
			final List<Book> expectedList = createDefaulList(sut.getShelfId());

			assertThat(actualBookIdList, is(not(nullValue())));
			for (int i = 0; i < expectedList.size(); i++) {

				final BookId actualBookId = actualBookIdList.get(i);
				final BookId expectedBookId = expectedList.get(i).getBookId();
				assertThat(actualBookId.isSame(expectedBookId), is(true));
			}

		}

		@Test
		public void getNameで本棚名が返却される() {

			final List<Book> actualBookList = sut.getBookList();
			final List<Book> beforeList = createDefaulList(sut.getShelfId());
			final int expectedSize = beforeList.size();

			assertThat(sut.getBookCount(), is(expectedSize));
			assertThat(sut.getName().getValue(), is(shelfName.getValue()));

			for (int i = 0; i < beforeList.size(); i++) {

				assertThat(actualBookList.get(i), is(sameBook(beforeList.get(i))));
			}

		}

		@Test
		public void getBookCountで追加した本の数が返却される() {

			final int expectedSize = createDefaulList(sut.getShelfId()).size();

			assertThat(sut.getBookCount(), is(expectedSize));
		}

		@Test
		public void validateでエラーが発生しない() {

			final ValidateErrors errors = sut.validate();

			assertThat(errors.hasError(), is(false));
		}

		@Test
		public void addでBookが追加される() {

			final Book expectedBook = new Book(new BookId(123456L));
			expectedBook.setIsbnCode(new IsbnCode("978-4-274-06885-0"));
			expectedBook.setTitle(new Title("テスト"));
			expectedBook.setPrice(new Price(1500));
			expectedBook.setParentShelfId(sut.getShelfId());

			final List<Book> beforeList = createDefaulList(sut.getShelfId());
			final int expectedSize = beforeList.size() + 1;

			sut.add(expectedBook);

			assertThat(sut.getBookCount(), is(expectedSize));

			final List<Book> actualBookList = sut.getBookList();

			for (int i = 0; i < beforeList.size(); i++) {

				assertThat(actualBookList.get(i), is(sameBook(beforeList.get(i))));
			}

			final Book actualAdditinalBook = actualBookList.get(actualBookList.size() - 1);

			assertThat(actualAdditinalBook, is(sameBook(expectedBook)));
		}

		@Test
		public void deleteで存在する本を指定した場合に削除される() {

			final boolean isDelete = sut.remove(createDefaultBook2(sut.getShelfId()).getBookId());

			assertThat(isDelete, is(true));

			final int expectedSize = createDefaulList(sut.getShelfId()).size() - 1;

			final List<Book> actualList = sut.getBookList();

			assertThat(actualList.size(), is(expectedSize));
			assertThat(actualList.get(0), is(sameBook(createDefaultBook1(sut.getShelfId()))));
			assertThat(actualList.get(1), is(sameBook(createDefaultBook3(sut.getShelfId()))));
		}

		@Test
		public void deleteで存在する本を指定した場合に削除されない() {

			final List<Book> beforeList = createDefaulList(sut.getShelfId());
			final int expectedSize = beforeList.size();

			final boolean isDelete = sut.remove(new BookId(9999L));
			final List<Book> actualBookList = sut.getBookList();

			assertThat(isDelete, is(false));

			assertThat(sut.getBookCount(), is(expectedSize));

			for (int i = 0; i < beforeList.size(); i++) {

				assertThat(actualBookList.get(i), is(sameBook(beforeList.get(i))));
			}
		}

		@Test
		public void getBookに本IDを指定すると紐付く本が返却される() {

			final Book expectedBook = createDefaultBook2(sut.getShelfId());
			final Book actualBook = sut.getBook(expectedBook.getBookId());

			assertThat(actualBook, is(sameBook(expectedBook)));
		}

		@Test
		public void getBookに存在しない本IDを指定するとNullが返却される() {

			final Book actualBook = sut.getBook(new BookId(99999L));

			assertThat(actualBook, is(nullValue()));
		}

		@Test
		public void getBookにNullを指定するとNullが返却される() {

			final Book actualBook = sut.getBook(null);

			assertThat(actualBook, is(nullValue()));
		}

		@Test
		public void isSameShelfIdに同一の本棚IDを指定するとtrueが返却されれる() {

			assertThat(sut.isSameShelf(sut.getShelfId()), is(true));
		}

		@Test
		public void isSameShelfIdに異なる本棚IDを指定するとfalseが返却されれる() {

			final ShelfId compareShelfId = new ShelfId(sut.getShelfId().getValue() + 1);

			assertThat(sut.isSameShelf(compareShelfId), is(false));
		}

		@Test
		public void isSameShelfIdにNullを指定するとfalseが返却されれる() {

			assertThat(sut.isSameShelf(null), is(false));
		}
	}

	private static List<Book> createDefaulList(final ShelfId parentShelfId) {

		final List<Book> returnList = new ArrayList<>();

		returnList.add(createDefaultBook1(parentShelfId));
		returnList.add(createDefaultBook2(parentShelfId));
		returnList.add(createDefaultBook3(parentShelfId));

		return returnList;
	}

	private static Book createDefaultBook1(final ShelfId parentShelfId) {

		final Book data = new Book(new BookId(1001L));

		data.setIsbnCode(new IsbnCode("9784274068850"));
		data.setTitle(new Title("デフォルト０１"));
		data.setPrice(new Price(1000));
		data.setParentShelfId(parentShelfId);

		return data;
	}

	private static Book createDefaultBook2(final ShelfId parentShelfId) {

		final Book data = new Book(new BookId(2002L));

		data.setIsbnCode(new IsbnCode("9784798119151"));
		data.setTitle(new Title("デフォルト０２"));
		data.setPrice(new Price(2000));
		data.setParentShelfId(parentShelfId);

		return data;
	}

	private static Book createDefaultBook3(final ShelfId parentShelfId) {

		final Book data = new Book(new BookId(3003L));

		data.setIsbnCode(new IsbnCode("9784822211882"));
		data.setTitle(new Title("デフォルト０３"));
		data.setPrice(new Price(3000));
		data.setParentShelfId(parentShelfId);

		return data;
	}
}
