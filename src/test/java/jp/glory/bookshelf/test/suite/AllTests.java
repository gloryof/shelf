package jp.glory.bookshelf.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		CommonTests.class, DomainTests.class, ServiceTests.class, RuleTests.class
})
public class AllTests {

}
