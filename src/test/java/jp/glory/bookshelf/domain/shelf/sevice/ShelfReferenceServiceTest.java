package jp.glory.bookshelf.domain.shelf.sevice;

import static jp.glory.bookshelf.test.matcher.ShelfMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.expection.DataNotFoundException;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfReferenceServiceTest {

	private static ShelfRepositoryStub stub = null;

	private static ShelfReferenceService sut = null;

	public static class findByIdのテスト {

		private Shelf expectedData = null;

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();

			expectedData = createExpectedData();
			stub.addShelf(expectedData);

			sut = new ShelfReferenceService(stub);
		}

		@Test
		public void 存在するIDを指定すると紐付く本棚が返却される() {

			final Shelf actualData = sut.findById(expectedData.getShelfId());

			assertThat(actualData, is(not(nullValue())));
			assertThat(actualData, is(sameShelf(expectedData)));
		}

		@Test(expected = DataNotFoundException.class)
		public void 存在しないIDを指定するとデータなしエラーになる() {

			sut.findById(new ShelfId(10L));
		}

		@Test(expected = DataNotFoundException.class)
		public void パラメータにNullを指定するとデータなしエラーになる() {

			sut.findById(null);
		}

		private Shelf createExpectedData() {

			return new Shelf(new ShelfId(123456L), new ArrayList<Book>());
		}
	}

	public static class isExistのテスト {

		private Shelf expectedData = null;

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();

			expectedData = createExpectedData();
			stub.addShelf(expectedData);

			sut = new ShelfReferenceService(stub);
		}

		@Test
		public void 存在するIDを指定するtrueが返却される() {

			final ShelfId shelfId = expectedData.getShelfId();
			assertThat(sut.isExist(shelfId), is(true));
		}

		@Test
		public void 存在しないIDを指定するとfalseが返却される() {

			final ShelfId shelfId = new ShelfId(10L);
			assertThat(sut.isExist(shelfId), is(false));
		}

		@Test
		public void パラメータにNullを指定するとfalseが返却される() {

			assertThat(sut.isExist(null), is(false));
		}

		private Shelf createExpectedData() {

			return new Shelf(new ShelfId(123456L), new ArrayList<Book>());
		}
	}

	public static class findAllのテスト {

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();

			for (final Shelf shelf : createShelfList()) {
				stub.addShelf(shelf);
			}

			sut = new ShelfReferenceService(stub);
		}

		@Test
		public void 全件取得できる() {

			final List<Shelf> expectedShelfList = createShelfList();

			final List<Shelf> actualShelfList = sut.findAll();

			assertThat(actualShelfList.size(), is(expectedShelfList.size()));

			for (int i = 0; i < actualShelfList.size(); i++) {

				final Shelf actualShelf = actualShelfList.get(i);
				final Shelf expectedShelf = expectedShelfList.get(i);

				assertThat(actualShelf, is(sameShelf(expectedShelf)));
			}
		}

		private List<Shelf> createShelfList() {

			final List<Shelf> shelfList = new ArrayList<>();

			final long[] idValues = {
					1001, 2002, 3003
			};

			for (final long id : idValues) {

				final ShelfId shelfId = new ShelfId(id);
				final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());
				shelfList.add(shelf);
			}

			return shelfList;
		}
	}

	public static class getMoveShelfLitのテスト {

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();
			stub.addShelf(createMoveIgnoreShelf());

			final List<Shelf> moveList = createMoveList();
			for (final Shelf shelf : moveList) {

				stub.addShelf(shelf);
			}

			sut = new ShelfReferenceService(stub);
		}

		@Test
		public void 指定した本棚ID以外の本棚リストが返却される() {

			final List<Shelf> expectedShelfList = createMoveList();
			final Shelf ignoreShelf = createMoveIgnoreShelf();

			final List<Shelf> actualShelfList = sut.getMoveShelfLit(ignoreShelf.getShelfId());

			assertThat(actualShelfList, is(not(nullValue())));
			assertThat(actualShelfList.size(), is(expectedShelfList.size()));

			for (int i = 0; i < actualShelfList.size(); i++) {

				final Shelf expectedShelf = expectedShelfList.get(i);
				final Shelf actualShelf = actualShelfList.get(i);

				assertThat(actualShelf, is(sameShelf(expectedShelf)));
			}
		}

		private Shelf createMoveIgnoreShelf() {

			final ShelfId shelfId = new ShelfId(1L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}

		private List<Shelf> createMoveList() {

			final List<Shelf> moveList = new ArrayList<>();

			moveList.add(createMoveShelf01());
			moveList.add(createMoveShelf02());
			moveList.add(createMoveShelf03());
			moveList.add(createMoveShelf04());

			return moveList;
		}

		private Shelf createMoveShelf01() {

			final ShelfId shelfId = new ShelfId(11L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}

		private Shelf createMoveShelf02() {

			final ShelfId shelfId = new ShelfId(12L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}

		private Shelf createMoveShelf03() {

			final ShelfId shelfId = new ShelfId(13L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}

		private Shelf createMoveShelf04() {

			final ShelfId shelfId = new ShelfId(14L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}
	}
}
