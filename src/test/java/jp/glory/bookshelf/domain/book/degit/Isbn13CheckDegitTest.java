package jp.glory.bookshelf.domain.book.degit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class Isbn13CheckDegitTest {

	public static class ISBN13形式_正しい形式の場合_trueが返却される {

		@Test
		public void チェックディジットの結果が0の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784274068850");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が1の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798119151");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が2の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784822211882");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が3の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784774153803");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が4の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798116174");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が5の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798122335");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が6の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798105536");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が7の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784822283667");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が8の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798125398");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が9の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784774143439");

			assertThat(sut.isValid(), is(true));
		}
	}

	public static class ISBN13形式_チェックディジットが不正な場合_falseが返却される {

		@Test
		public void チェックディジットの結果が0になるべき箇所で1の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784274068851");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が1になるべき箇所で0の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798119150");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が2になるべき箇所で3の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784822211883");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が3になるべき箇所で2の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784774153802");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が4になるべき箇所で5の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798116175");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が5になるべき箇所で4の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798122334");

			assertThat(sut.isValid(), is(false));

		}

		@Test
		public void チェックディジットの結果が6になるべき箇所で7の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798105537");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が7になるべき箇所で6の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784822283666");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が8になるべき箇所で7の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784798125399");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が9になるべき箇所で0の場合() {

			final Isbn13CheckDegit sut = new Isbn13CheckDegit("9784774143430");

			assertThat(sut.isValid(), is(false));
		}

	}

}
