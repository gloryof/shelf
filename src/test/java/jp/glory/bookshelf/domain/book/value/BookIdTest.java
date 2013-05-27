package jp.glory.bookshelf.domain.book.value;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookIdTest {

	private static BookId sut = null;

	public static class デフォルトコンストラクタの場合 {

		@Before
		public void setUp() {

			sut = new BookId();
		}

		@Test
		public void getValueで0が返却される() {

			assertThat(sut.getValue(), is(0L));
		}

		@Test
		public void isNumberedでfalseが返却される() {

			assertThat(sut.isNumbered(), is(false));
		}

		@Test
		public void isSameに同じ値のIDを指定した場合_falseが返却される() {

			final BookId compareId = new BookId(0L);

			assertThat(sut.isSame(compareId), is(false));
		}

		@Test
		public void isSameに未採番のIDを指定した場合_falseが返却される() {

			final BookId compareId = new BookId();

			assertThat(sut.isSame(compareId), is(false));
		}
	}

	public static class コンストラクタにNullが指定された場合 {

		@Before
		public void setUp() {

			sut = new BookId(null);
		}

		@Test
		public void getValueで0が返却される() {

			assertThat(sut.getValue(), is(0L));
		}

		@Test
		public void isNumberedでfalseが返却される() {

			assertThat(sut.isNumbered(), is(false));
		}

		@Test
		public void isSameに同じ値のIDを指定した場合_falseが返却される() {

			final BookId compareId = new BookId(0L);

			assertThat(sut.isSame(compareId), is(false));
		}

		@Test
		public void isSameに未採番のIDを指定した場合_falseが返却される() {

			final BookId compareId = new BookId();

			assertThat(sut.isSame(compareId), is(false));
		}
	}

	public static class コンストラクタに値が指定された場合 {

		private static final long expectedValue = 123;

		@Test
		public void パラメータなし() {

			sut = new BookId(expectedValue);
		}

		@Test
		public void getValueでコンストラクタに指定した値が返却される() {

			assertThat(sut.getValue(), is(expectedValue));
		}

		@Test
		public void isNumberdでtrueが返却される() {
			assertThat(sut.isNumbered(), is(true));
		}

		@Test
		public void isSameに同じ値を指定するとtrueが返却される() {

			final BookId compareObject = new BookId(sut.getValue());

			assertThat(sut.isSame(compareObject), is(true));
		}

		@Test
		public void isSameに違う値を指定するとfalseが返却される() {

			final BookId compareObject = new BookId(sut.getValue() + 1);

			assertThat(sut.isSame(compareObject), is(false));
		}

		@Test
		public void isSameにNullを指定するとfalseが返却される() {

			assertThat(sut.isSame(null), is(false));
		}

		@Test
		public void isSameに指定した比較対象が未採番の場合falseが返却される() {

			final BookId compareShelfId = new BookId();

			assertThat(sut.isSame(compareShelfId), is(false));
		}
	}

}
