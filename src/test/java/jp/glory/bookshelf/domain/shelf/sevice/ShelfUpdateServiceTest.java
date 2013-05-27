package jp.glory.bookshelf.domain.shelf.sevice;

import static jp.glory.bookshelf.test.matcher.BookMatcher.*;
import static jp.glory.bookshelf.test.matcher.ShelfMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.common.expection.DataNotFoundException;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfUpdateServiceTest {

	private static ShelfRepositoryStub stub = null;

	private static ShelfUpdateService sut = null;

	public static class createNewSelfのテスト {

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();
			sut = new ShelfUpdateService(stub);
		}

		@Test
		public void 入力エラーがない未採番の本棚場合＿採番された後に追加される() throws BusinessException {

			final Shelf shelf = new Shelf();
			final long expectedNumber = stub.getCurrentNumber();
			shelf.setName(new Name("テスト"));

			final ShelfId actualShelfId = sut.createNewSelf(shelf);
			assertThat(actualShelfId.getValue(), is(expectedNumber));

			final Shelf afterShelf = stub.findById(actualShelfId);

			assertThat(afterShelf.isNew(), is(false));
			assertThat(afterShelf.getName().getValue(), is(shelf.getName().getValue()));
		}

		@Test(expected = BusinessException.class)
		public void 入力エラーがある未採番の本棚場合＿入力チェックエラーになる() throws BusinessException {

			final Shelf shelf = new Shelf();

			sut.createNewSelf(shelf);
		}
	}

	public static class updateShelfNameのテスト {

		private Shelf beforeData = null;

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();
			beforeData = createBeforeData();
			stub.addShelf(beforeData);

			sut = new ShelfUpdateService(stub);
		}

		@Test
		public void 入力チェックエラーがない場合＿更新される() throws BusinessException {

			final Shelf expectedShelf = createBeforeData();
			expectedShelf.setName(new Name("更新後"));

			sut.updateShelfName(expectedShelf);

			final Shelf actualShelf = stub.findById(expectedShelf.getShelfId());

			assertThat(actualShelf, is(sameShelf(expectedShelf)));
		}

		@Test(expected = BusinessException.class)
		public void 入力チェックエラーがある場合＿入力チェックエラーになる() throws BusinessException {

			final Shelf expectedShelf = createBeforeData();
			expectedShelf.setName(new Name(""));

			sut.updateShelfName(expectedShelf);
		}

		@Test(expected = DataNotFoundException.class)
		public void 存在しないデータの場合_データなしエラーになる() throws BusinessException {

			final ShelfId shelfId = new ShelfId(12345L);
			final List<Book> bookList = new ArrayList<>();
			final Name name = new Name("更新後");

			final Shelf shelf = new Shelf(shelfId, bookList);
			shelf.setName(name);

			sut.updateShelfName(shelf);
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

	public static class addのテスト {

		private Shelf beforeData = null;

		@Before
		public void setUp() {

			final ShelfId shelfId = new ShelfId(1500L);
			beforeData = new Shelf(shelfId, new ArrayList<Book>());

			stub = new ShelfRepositoryStub();
			stub.addShelf(beforeData);

			sut = new ShelfUpdateService(stub);
		}

		@Test
		public void パラメータで指定した本IDが本棚に追加される() {

			final BookId expectedBookId = new BookId(2000L);
			final ShelfId shelfId = beforeData.getShelfId();

			sut.add(shelfId, expectedBookId);

			final Shelf actualShelf = stub.findById(shelfId);
			final Book actualBook = actualShelf.getBook(expectedBookId);

			assertThat(actualBook, is(not(nullValue())));
		}
	}

	public static class moveのテスト {

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();
			sut = new ShelfUpdateService(stub);
		}

		@Test
		public void fromからtoに選択した本が移動する() throws BusinessException {

			final ShelfId fromShelfId = new ShelfId(1000L);
			final List<Book> fromTempList = TestTools.createBookList(fromShelfId, new long[] {
					10000001, 10000002, 10000002
			});

			final List<Book> moveBookList = TestTools.createBookList(fromShelfId, new long[] {
					30000001, 30000002
			});

			final List<Book> fromList = new ArrayList<>();
			fromList.addAll(fromTempList);
			fromList.addAll(moveBookList);

			final ShelfId toShelfId = new ShelfId(2000L);
			final List<Book> toList = TestTools.createBookList(toShelfId, new long[] {
					20000001, 20000002
			});

			final Shelf fromShelf = new Shelf(fromShelfId, fromList);
			final Shelf toShelf = new Shelf(toShelfId, toList);

			stub.addShelf(fromShelf);
			stub.addShelf(toShelf);

			final List<BookId> moveBookIdList = new ArrayList<>();
			for (final Book moveBook : moveBookList) {

				moveBookIdList.add(moveBook.getBookId());
			}

			sut.move(fromShelfId, toShelfId, moveBookIdList);

			final Shelf fromActualShelf = stub.findById(fromShelfId);
			final Shelf toActualShelf = stub.findById(toShelfId);

			assertThat(fromActualShelf.getBookCount(), is(3));
			assertThat(toActualShelf.getBookCount(), is(4));

			final List<Book> fromActualBookList = fromActualShelf.getBookList();
			for (int i = 0; i < fromActualBookList.size(); i++) {

				final Book actualFromBook = fromActualBookList.get(i);
				final Book expectedFromBook = fromTempList.get(i);
				assertThat(actualFromBook, is(sameBook(expectedFromBook)));
			}

			final List<Book> toActualBookList = toActualShelf.getBookList();
			for (int i = 0; i < toActualBookList.size(); i++) {

				final Book actualToBook = toActualBookList.get(i);
				final Book expectedToBook = toList.get(i);
				assertThat(actualToBook, is(sameBook(expectedToBook)));
			}
		}

		@Test(expected = BusinessException.class)
		public void 移動エラーで例外がスローされる() throws BusinessException {

			sut.move(new ShelfId(), new ShelfId(), new ArrayList<BookId>());
		}
	}

	public static class deleteのテスト {

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();
			sut = new ShelfUpdateService(stub);
		}

		@Test(expected = BusinessException.class)
		public void 削除エラーで例外がスローされる() throws BusinessException {

			sut.delete(null);
		}

		@Test
		public void 本棚のみが削除され_本は全て未分類本棚に移動される() throws BusinessException {

			final Shelf shelf01 = TestTools.createShelf01();
			final Shelf shelf02 = TestTools.createShelf02();
			final Shelf shelf03 = TestTools.createShelf03();

			final Shelf moveShelf = new Shelf();

			final List<Book> expectedMoveBookList = new ArrayList<>();
			expectedMoveBookList.addAll(shelf01.getBookList());
			expectedMoveBookList.addAll(shelf02.getBookList());
			expectedMoveBookList.addAll(shelf03.getBookList());

			stub.addShelf(shelf01);
			stub.addShelf(shelf02);
			stub.addShelf(shelf03);
			stub.addShelf(moveShelf);

			final List<ShelfId> shelfIdList = new ArrayList<>();
			shelfIdList.add(shelf01.getShelfId());
			shelfIdList.add(shelf02.getShelfId());
			shelfIdList.add(shelf03.getShelfId());

			sut.delete(shelfIdList);

			for (final ShelfId shelfId : shelfIdList) {

				assertThat(stub.findById(shelfId), is(nullValue()));
			}

			final Shelf nonCategoryShelf = stub.findById(moveShelf.getShelfId());
			final List<Book> actualBookList = nonCategoryShelf.getBookList();

			assertThat(actualBookList.size(), is(expectedMoveBookList.size()));
			for (int i = 0; i < expectedMoveBookList.size(); i++) {

				final Book actualToBook = actualBookList.get(i);
				final Book expectedToBook = expectedMoveBookList.get(i);
				assertThat(actualToBook.getBookId(), is(expectedToBook.getBookId()));
			}
		}
	}

	private static class TestTools {

		private static List<Book> createBookList(final ShelfId shelfId, final long[] bookIdValues) {

			final List<Book> bookList = new ArrayList<>();

			for (final long bookIdValue : bookIdValues) {

				final BookId bookId = new BookId(bookIdValue);
				final Book book = new Book(bookId);
				book.setParentShelfId(shelfId);

				bookList.add(book);
			}

			return bookList;
		}

		private static Shelf createShelf01() {

			final ShelfId shelfId = new ShelfId(1000L);
			final List<Book> bookList = TestTools.createBookList(shelfId, new long[] {
					10000001, 10000002, 10000002
			});

			return new Shelf(shelfId, bookList);
		}

		private static Shelf createShelf02() {

			final ShelfId shelfId = new ShelfId(2000L);
			final List<Book> bookList = TestTools.createBookList(shelfId, new long[] {
					20000001, 20000002
			});

			return new Shelf(shelfId, bookList);
		}

		private static Shelf createShelf03() {

			final ShelfId shelfId = new ShelfId(1000L);
			final List<Book> bookList = TestTools.createBookList(shelfId, new long[] {
				30000001
			});

			return new Shelf(shelfId, bookList);
		}

	}
}
