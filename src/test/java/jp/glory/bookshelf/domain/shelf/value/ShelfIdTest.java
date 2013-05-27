package jp.glory.bookshelf.domain.shelf.value;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfIdTest {

	private static ShelfId sut = null;

	public static class デフォルトコンストラクタの場合 {

		@Before
		public void setUp() {

			sut = new ShelfId();
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

			final ShelfId compareShelfId = new ShelfId(0L);

			assertThat(sut.isSame(compareShelfId), is(false));
		}

		@Test
		public void isSameに未採番のIDを指定した場合_falseが返却される() {

			final ShelfId compareShelfId = new ShelfId();

			assertThat(sut.isSame(compareShelfId), is(false));
		}
	}

	public static class コンストラクタのパラメータにNullを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfId(null);
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

			final ShelfId compareShelfId = new ShelfId(0L);

			assertThat(sut.isSame(compareShelfId), is(false));
		}

		@Test
		public void isSameに未採番のIDを指定した場合_falseが返却される() {

			final ShelfId compareShelfId = new ShelfId();

			assertThat(sut.isSame(compareShelfId), is(false));
		}
	}

	public static class コンストラクタのパラメータに値を指定した場合 {

		private static final long expectedValue = 123;

		@Test
		public void パラメータなし() {

			sut = new ShelfId(expectedValue);
		}

		@Test
		public void getValueでコンストラクタに指定された値が返却される() {

			assertThat(sut.getValue(), is(expectedValue));
		}

		@Test
		public void isNumberedでtrueが返却される() {

			assertThat(sut.isNumbered(), is(true));
		}

		@Test
		public void isSameに同一の本棚IDを指定した場合_trueが返却される() {

			final ShelfId compareShelfId = new ShelfId(expectedValue);

			assertThat(sut.isSame(compareShelfId), is(true));
		}

		@Test
		public void isSameに異なる本棚IDを指定した場合_falseが返却される() {

			final ShelfId compareShelfId = new ShelfId(expectedValue + 1);

			assertThat(sut.isSame(compareShelfId), is(false));
		}

		@Test
		public void isSameにNullを指定した場合_falseが返却される() {

			assertThat(sut.isSame(null), is(false));
		}

		@Test
		public void isSameに指定した比較対象が未採番の場合_falseが返却される() {

			final ShelfId compareShelfId = new ShelfId();

			assertThat(sut.isSame(compareShelfId), is(false));
		}
	}

	public static class getUnclassifiedShelfsIdのテスト {

		@Test
		public void 未分類の本棚IDが返却される() {

			final ShelfId actualShelfId = ShelfId.getUnclassifiedShelfsId();

			assertThat(actualShelfId, is(not(nullValue())));
			assertThat(actualShelfId.getValue(), is(0L));
			assertThat(actualShelfId.isNumbered(), is(true));
		}
	}
}
