package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ClientCommunicatorTest.class, ClientHelperTest.class, CurrentUserTest.class })
public class AllTests {

}
