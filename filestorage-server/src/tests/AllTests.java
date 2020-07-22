package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ServerCommunicatorTest.class, ServerHelperTest.class, AllUsersTest.class })
public class AllTests {

}
