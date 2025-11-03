import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *Unit test for the CellState class.
 */
public class CellStateTest {

    @Test
    void testEnumValues() {
        assertEquals(CellState.BLANK, CellState.valueOf("BLANK"));
        assertEquals(CellState.MISS, CellState.valueOf("MISS"));
        assertEquals(CellState.HIT, CellState.valueOf("HIT"));
        assertEquals(3, CellState.values().length);
    }
}
