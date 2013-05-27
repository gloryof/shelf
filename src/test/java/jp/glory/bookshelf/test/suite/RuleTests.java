package jp.glory.bookshelf.test.suite;

import jp.glory.bookshelf.domain.book.rule.BookCreatingRuleTest;
import jp.glory.bookshelf.domain.book.rule.BookListDeletingRuleTest;
import jp.glory.bookshelf.domain.book.rule.BookUpdatingRuleTest;
import jp.glory.bookshelf.domain.shelf.rule.ShelfCreatingRuleTest;
import jp.glory.bookshelf.domain.shelf.rule.ShelfDelettingRuleTest;
import jp.glory.bookshelf.domain.shelf.rule.ShelfMovingBookRuleTest;
import jp.glory.bookshelf.domain.shelf.rule.ShelfUpdatingRuleTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		BookCreatingRuleTest.class,
		BookListDeletingRuleTest.class,
		BookUpdatingRuleTest.class,
		ShelfCreatingRuleTest.class,
		ShelfUpdatingRuleTest.class,
		ShelfMovingBookRuleTest.class,
		ShelfDelettingRuleTest.class

})
public class RuleTests {

}
