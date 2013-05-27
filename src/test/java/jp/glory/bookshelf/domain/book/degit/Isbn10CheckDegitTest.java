package jp.glory.bookshelf.domain.book.degit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class Isbn10CheckDegitTest {

	public static class ISBN10形式_正しい形式の場合_trueが返却される {

		@Test
		public void チェックディジットの結果が0になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4822229920");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が1になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4774151041");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が2になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4873114292");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が3になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4774153893");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が4になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4873113954");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が5になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4798122335");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が6になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4822283666");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が7になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4798121967");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が8になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4795297258");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果が9になる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4822284239");

			assertThat(sut.isValid(), is(true));
		}

		@Test
		public void チェックディジットの結果がXになる場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("477415377X");

			assertThat(sut.isValid(), is(true));
		}
	}

	public static class ISBN10形式_チェックディジットが不正な場合_falseが返却される {

		@Test
		public void チェックディジットの結果が0になるべき箇所で1の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4822229921");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が1になるべき箇所で0の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4774151040");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が2になるべき箇所で3の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4873114293");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が3になるべき箇所で2の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4774153892");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が4になるべき箇所で5の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4873113955");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が5になるべき箇所で4の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4798122334");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が6になるべき箇所で7の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4822283667");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が7になるべき箇所で6の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4798121966");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が8になるべき箇所で9の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4795297259");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果が9になるべき箇所で8の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4822284238");

			assertThat(sut.isValid(), is(false));
		}

		@Test
		public void チェックディジットの結果がXになるべき箇所で9の場合() {

			final Isbn10CheckDegit sut = new Isbn10CheckDegit("4774153779");

			assertThat(sut.isValid(), is(false));
		}
	}

}
