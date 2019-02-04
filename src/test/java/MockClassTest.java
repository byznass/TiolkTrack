import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockClassTest {

    private MockClass mockClass;

    @Before
    public void setUp() {

        mockClass = new MockClass();
    }

    @Test
    public void checkThatSeedIsIncreasedByFive() {

        int seed = 12;

        int result = mockClass.doSomething(seed);

        assertEquals(5, result - seed);
    }
}