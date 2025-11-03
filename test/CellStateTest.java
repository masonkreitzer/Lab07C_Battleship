import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *Unit test for the CellState class.
 */
public class CellStateTest {

    @Test
    void testEnumValues() {

        // Check that the enum values exist and are correct
        assertEquals(CellState.BLANK, CellState.valueOf("BLANK"));
        assertEquals(CellState.MISS, CellState.valueOf("MISS"));
        assertEquals(CellState.HIT, CellState.valueOf("HIT"));

        // Check total number of enum values
        assertEquals(3, CellState.values().length);
    }
}