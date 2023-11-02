package byog.lab6;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestMemoryGame {
    static MemoryGame testGame = new MemoryGame(40, 40, 123);
    @Test
    public void testGenerateRandomString() {
        String actualString = testGame.generateRandomString(5);
        assertEquals(5, actualString.length());
    }
}
