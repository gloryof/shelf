package jp.glory.bookshelf.test.suite;

import jp.glory.bookshelf.domain.book.service.BookServiceTest;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceServiceTest;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfUpdateServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		ShelfReferenceServiceTest.class, ShelfUpdateServiceTest.class, BookServiceTest.class
})
public class ServiceTests {

}
