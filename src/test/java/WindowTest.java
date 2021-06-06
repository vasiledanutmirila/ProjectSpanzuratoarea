import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pa.project.windows.Window;

import static org.junit.jupiter.api.Assertions.*;

public class WindowTest {
    private Window window;

    @BeforeEach
    public void setUp() {
        window = new Window();
        window.initWordsRepository();
    }

    @RepeatedTest(100)
    @DisplayName("Variables initialization should work")
    public void testInitVariables() {
        window.initVariables();
        assertEquals(0, window.tries);
        assertTrue(window.word.length() != 0);
        assertTrue(window.domain.length() != 0);
    }

    @RepeatedTest(100)
    @DisplayName("Word initialization should work")
    public void testInitWord() {
        assertNotNull(window.initWord());
    }

    @RepeatedTest(100)
    @DisplayName("Count occurrences should work")
    public void testCheckOccurrences() {
        assertEquals(7, window.countOccurrences('_', "t_e_s_t_o_c_curren_ces"));
    }

    /*@RepeatedTest(10)
    @DisplayName("Logger should work")
    public void testLogger() {
        assertNotNull();
    }*/
}
