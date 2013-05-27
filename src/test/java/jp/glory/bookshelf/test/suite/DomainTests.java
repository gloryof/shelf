package jp.glory.bookshelf.test.suite;

import jp.glory.bookshelf.domain.book.degit.Isbn10CheckDegitTest;
import jp.glory.bookshelf.domain.book.degit.Isbn13CheckDegitTest;
import jp.glory.bookshelf.domain.book.degit.IsbnCheckDegitTest;
import jp.glory.bookshelf.domain.book.entity.BookTest;
import jp.glory.bookshelf.domain.book.value.BookIdTest;
import jp.glory.bookshelf.domain.book.value.IsbnCodeTest;
import jp.glory.bookshelf.domain.book.value.PriceTest;
import jp.glory.bookshelf.domain.book.value.TitleTest;
import jp.glory.bookshelf.domain.shelf.entity.ShelfTest;
import jp.glory.bookshelf.domain.shelf.value.NameTest;
import jp.glory.bookshelf.domain.shelf.value.ShelfIdTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TitleTest.class,
		IsbnCodeTest.class,
		IsbnCheckDegitTest.class,
		Isbn10CheckDegitTest.class,
		Isbn13CheckDegitTest.class,
		PriceTest.class,
		BookTest.class,
		BookIdTest.class,
		ShelfTest.class,
		TitleTest.class,
		ShelfIdTest.class,
		NameTest.class
})
public class DomainTests {

}
