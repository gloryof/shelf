package jp.glory.bookshelf.domain.book.service;

import static jp.glory.bookshelf.test.matcher.BookMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.common.expection.DataNotFoundException;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepositoryStub;
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
public class BookServiceTest {

	private static BookService sut = null;

	private static BookRepositoryStub stub = null;

	public static class findByIdのテスト {

		@Before
		public void setUp() {

			final BookRepositoryStub stub = new BookRepositoryStub();
			stub.addBook(TestTools.createBookData01());

			sut = new BookService(stub);
		}

		@Test
		public void 存在するIDを指定すると紐付く本が返却される() {

			final Book expectedBook = TestTools.createBookData01();

			final Book actualBook = sut.findById(expectedBook.getBookId());

			assertThat(actualBook, is(not(nullValue())));
			assertThat(actualBook, is(sameBook(expectedBook)));
		}

		@Test(expected = DataNotFoundException.class)
		public void 存在しないIDを指定するとデータなしエラーになる() {

			final Book expectedBook = TestTools.createBookData01();

			final BookId searchId = new BookId(expectedBook.getBookId().getValue() + 1);
			sut.findById(searchId);
		}

		@Test
		public void IDにNullを指定するとNullが返却される() {

			final Book actualBook = sut.findById(null);

			assertThat(actualBook, is(nullValue()));
		}
	}

	public static class findListByIsbnCodeのテスト {

		@Before
		public void setUp() {

			final BookRepositoryStub stub = new BookRepositoryStub();
			stub.addBook(TestTools.createBookData01());
			stub.addBook(TestTools.createBookData02());
			stub.addBook(TestTools.createBookData03());

			sut = new BookService(stub);
		}

		@Test
		public void 存在しないISBNコードを指定すると空のリストが返却される() {

			final List<Book> actualList = sut.findListByIsbnCode(new IsbnCode("978-4774143439"));

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.isEmpty(), is(true));
		}

		@Test
		public void 存在するISBNコードを指定すると本のリストが返却される() {

			final List<Book> actualList = sut.findListByIsbnCode(TestTools.createBookData02().getIsbnCode());

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(2));
		}

		@Test
		public void Nullを指定すると空のリストが返却される() {

			final List<Book> actualList = sut.findListByIsbnCode(null);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.isEmpty(), is(true));
		}

		@Test
		public void 未設定のISBNコードを指定すると空のリストが返却される() {

			final List<Book> actualList = sut.findListByIsbnCode(new IsbnCode(""));

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.isEmpty(), is(true));
		}
	}

	public static class createのテスト {

		@Before
		public void setUp() {

			stub = new BookRepositoryStub();
			sut = new BookService(stub);
		}

		@Test
		public void 入力チェックエラーがない場合_本が追加され_本IDが返却される() throws BusinessException {

			final Book entryBook = TestTools.createNewBookData(new BookId());
			final long number = stub.getCurrentNumber();
			final BookId expectedBookId = new BookId(number);

			final BookId actualBookId = sut.create(entryBook);
			assertThat(actualBookId.getValue(), is(expectedBookId.getValue()));

			final Book expectedBook = TestTools.createNewBookData(expectedBookId);
			final Book actualBook = sut.findById(expectedBookId);

			assertThat(actualBook, is(not(nullValue())));
			assertThat(actualBook, is(sameBook(expectedBook)));
		}

		@Test(expected = BusinessException.class)
		public void 入力チェックエラーがある場合_業務例外が投げられる() throws BusinessException {

			final Book entryBook = TestTools.createNewBookData(new BookId());
			entryBook.setTitle(new Title(""));

			sut.create(entryBook);
		}

	}

	public static class updateのテスト {

		private BookId bookId = new BookId(10001L);

		@Before
		public void setUp() {

			stub = new BookRepositoryStub();
			stub.addBook(createNewBookData(bookId));

			sut = new BookService(stub);
		}

		@Test
		public void 入力チェックエラーがない場合＿更新される() throws BusinessException {

			final Book expectedBook = sut.findById(bookId);
			expectedBook.setTitle(new Title("SQLアンチパターン"));
			expectedBook.setIsbnCode(new IsbnCode("978-4873115894"));
			expectedBook.setPrice(new Price(3360));
			expectedBook.setParentShelfId(new ShelfId(123L));

			sut.update(expectedBook);

			final Book actualBook = sut.findById(expectedBook.getBookId());

			assertThat(actualBook, is(sameBook(expectedBook)));
		}

		@Test(expected = BusinessException.class)
		public void 入力チェックエラーがある場合_業務例外が投げられる() throws BusinessException {

			final Book book = sut.findById(bookId);
			book.setTitle(new Title(""));
			book.setIsbnCode(new IsbnCode("978-4873115894"));
			book.setPrice(new Price(3360));

			sut.update(book);
		}

		private Book createNewBookData(final BookId bookId) {

			final IsbnCode isbnCode = new IsbnCode("978-4798121963");
			final Title title = new Title("エリック・エヴァンスのドメイン駆動設計");
			final Price price = new Price(5460);

			final Book book = new Book(bookId);

			book.setIsbnCode(isbnCode);
			book.setTitle(title);
			book.setPrice(price);

			return book;
		}
	}

	public static class deleteBooksのテスト {

		@Before
		public void setUp() {

			stub = new BookRepositoryStub();

			stub.addBook(TestTools.createBookData01());
			stub.addBook(TestTools.createBookData02());
			stub.addBook(TestTools.createBookData03());

			sut = new BookService(stub);
		}

		@Test
		public void 入力チェックエラーがない場合＿削除される() throws BusinessException {

			final List<BookId> deletingBookIdList = createBookIdList();
			sut.deleteBooks(deletingBookIdList);

			for (final BookId bookId : deletingBookIdList) {

				assertThat(stub.findById(bookId), is(nullValue()));
			}
		}

		@Test(expected = BusinessException.class)
		public void 入力チェックエラーがある場合_例外が投げられる() throws BusinessException {

			sut.deleteBooks(new ArrayList<BookId>());
		}

		private List<BookId> createBookIdList() {

			final List<BookId> bookIdList = new ArrayList<>();

			bookIdList.add(TestTools.createBookData01().getBookId());
			bookIdList.add(TestTools.createBookData02().getBookId());
			bookIdList.add(TestTools.createBookData03().getBookId());

			return bookIdList;
		}

	}

	private static class TestTools {

		private static Book createNewBookData(final BookId bookId) {

			final IsbnCode isbnCode = new IsbnCode("978-4798121963");
			final Title title = new Title("エリック・エヴァンスのドメイン駆動設計");
			final Price price = new Price(5460);
			final ShelfId parentShelfId = new ShelfId(1000L);

			final Book book = new Book(bookId);

			book.setIsbnCode(isbnCode);
			book.setTitle(title);
			book.setPrice(price);
			book.setParentShelfId(parentShelfId);

			return book;
		}

		private static Book createBookData01() {

			final BookId bookId = new BookId(10001L);
			final IsbnCode isbnCode = new IsbnCode("978-4-274-06896-6");
			final Title title = new Title("関数プログラミング入門");
			final Price price = new Price(1500);
			final ShelfId parentShelfId = new ShelfId(1000L);

			final Book book = new Book(bookId);

			book.setIsbnCode(isbnCode);
			book.setTitle(title);
			book.setPrice(price);
			book.setParentShelfId(parentShelfId);

			return book;
		}

		private static Book createBookData02() {

			final BookId bookId = new BookId(10002L);
			final IsbnCode isbnCode = new IsbnCode("978-4822229917");
			final Title title = new Title("ITアーキテクトのためのシステム設計実践ガイド VOL.1");
			final Price price = new Price(1980);
			final ShelfId parentShelfId = new ShelfId(1000L);

			final Book book = new Book(bookId);

			book.setIsbnCode(isbnCode);
			book.setTitle(title);
			book.setPrice(price);
			book.setParentShelfId(parentShelfId);

			return book;
		}

		private static Book createBookData03() {

			final BookId bookId = new BookId(10003L);
			final IsbnCode isbnCode = new IsbnCode("978-4822229917");
			final Title title = new Title("ITアーキテクトのためのシステム設計実践ガイド VOL.1");
			final Price price = new Price(1980);
			final ShelfId parentShelfId = new ShelfId(1000L);

			final Book book = new Book(bookId);

			book.setIsbnCode(isbnCode);
			book.setTitle(title);
			book.setPrice(price);
			book.setParentShelfId(parentShelfId);

			return book;
		}
	}
}
