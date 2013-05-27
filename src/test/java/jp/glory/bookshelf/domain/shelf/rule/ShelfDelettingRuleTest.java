package jp.glory.bookshelf.domain.shelf.rule;

import static jp.glory.bookshelf.test.matcher.ValidateErrorMatcher.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.common.error.ErrorMessage;
import jp.glory.bookshelf.common.validate.ErrorInfo;
import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfDelettingRuleTest {

	public static class リスト件数が0件の場合 {

		@Test
		public void エラーになる() {

			final List<ShelfId> shelfIdList = new ArrayList<>();
			final ShelfDelettingRule sut = new ShelfDelettingRule(shelfIdList);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = actualError.toList();

			assertThat(actualErrorList, is(not(nullValue())));
			assertThat(actualErrorList.size(), is(1));

			final ErrorInfo actualErrorInfo = actualErrorList.get(0);

			assertThat(actualErrorInfo, is(not(nullValue())));
			assertThat(actualErrorInfo, is(invalidBy(ErrorMessage.SHELF_DELETE_ERROR)));
		}
	}

	public static class リスト件数がNullの場合 {

		@Test
		public void エラーになる() {

			final ShelfDelettingRule sut = new ShelfDelettingRule(null);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = actualError.toList();

			assertThat(actualErrorList, is(not(nullValue())));
			assertThat(actualErrorList.size(), is(1));

			final ErrorInfo actualErrorInfo = actualErrorList.get(0);

			assertThat(actualErrorInfo, is(not(nullValue())));
			assertThat(actualErrorInfo, is(invalidBy(ErrorMessage.SHELF_DELETE_ERROR)));
		}
	}

	public static class リスト件数が1件の場合 {

		@Test
		public void 採番された本棚IDを指定した場合_エラーにならない() {

			final List<ShelfId> shelfIdList = new ArrayList<>();
			shelfIdList.add(new ShelfId(123456L));

			final ShelfDelettingRule sut = new ShelfDelettingRule(shelfIdList);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(false));
		}

		@Test
		public void 採番されていない本棚IDを指定した場合_エラーになる() {

			final List<ShelfId> shelfIdList = new ArrayList<>();
			shelfIdList.add(new ShelfId());

			final ShelfDelettingRule sut = new ShelfDelettingRule(shelfIdList);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = actualError.toList();

			assertThat(actualErrorList, is(not(nullValue())));
			assertThat(actualErrorList.size(), is(1));

			final ErrorInfo actualErrorInfo = actualErrorList.get(0);

			assertThat(actualErrorInfo, is(not(nullValue())));
			assertThat(actualErrorInfo, is(invalidBy(ErrorMessage.SHELF_DELETE_ERROR)));
		}

		@Test
		public void IDにNullを指定した場合_エラーになる() {

			final List<ShelfId> shelfIdList = new ArrayList<>();
			shelfIdList.add(null);

			final ShelfDelettingRule sut = new ShelfDelettingRule(shelfIdList);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = actualError.toList();

			assertThat(actualErrorList, is(not(nullValue())));
			assertThat(actualErrorList.size(), is(1));

			final ErrorInfo actualErrorInfo = actualErrorList.get(0);

			assertThat(actualErrorInfo, is(not(nullValue())));
			assertThat(actualErrorInfo, is(invalidBy(ErrorMessage.SHELF_DELETE_ERROR)));
		}
	}

	public static class リスト件数が3件の場合 {

		@Test
		public void 全てが採番されたIDの場合() {

			final List<ShelfId> shelfIdList = new ArrayList<>();
			shelfIdList.add(new ShelfId(1001L));
			shelfIdList.add(new ShelfId(1002L));
			shelfIdList.add(new ShelfId(1003L));

			final ShelfDelettingRule sut = new ShelfDelettingRule(shelfIdList);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(false));
		}

		@Test
		public void 採番されていない本棚IDを含む場合_エラーになる() {

			final List<ShelfId> shelfIdList = new ArrayList<>();
			shelfIdList.add(new ShelfId(1001L));
			shelfIdList.add(new ShelfId());
			shelfIdList.add(new ShelfId(1003L));

			final ShelfDelettingRule sut = new ShelfDelettingRule(shelfIdList);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = actualError.toList();

			assertThat(actualErrorList, is(not(nullValue())));
			assertThat(actualErrorList.size(), is(1));

			final ErrorInfo actualErrorInfo = actualErrorList.get(0);

			assertThat(actualErrorInfo, is(not(nullValue())));
			assertThat(actualErrorInfo, is(invalidBy(ErrorMessage.SHELF_DELETE_ERROR)));
		}

		@Test
		public void Nullを含む場合_エラーになる() {

			final List<ShelfId> shelfIdList = new ArrayList<>();
			shelfIdList.add(new ShelfId(1001L));
			shelfIdList.add(null);
			shelfIdList.add(new ShelfId(1003L));

			final ShelfDelettingRule sut = new ShelfDelettingRule(shelfIdList);

			final ValidateErrors actualError = sut.validate();

			assertThat(actualError, is(not(nullValue())));
			assertThat(actualError.hasError(), is(true));

			final List<ErrorInfo> actualErrorList = actualError.toList();

			assertThat(actualErrorList, is(not(nullValue())));
			assertThat(actualErrorList.size(), is(1));

			final ErrorInfo actualErrorInfo = actualErrorList.get(0);

			assertThat(actualErrorInfo, is(not(nullValue())));
			assertThat(actualErrorInfo, is(invalidBy(ErrorMessage.SHELF_DELETE_ERROR)));
		}
	}
}
