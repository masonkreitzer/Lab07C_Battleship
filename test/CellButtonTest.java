import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *Unit tests for the CellButton class.
 */
public class CellButtonTest {

    @Test
    void testCellButtonInitialization() {
        Board board = new Board();
        CellButton button = new CellButton(0, 0, board);

        assertEquals(0, button.getRow());
        assertEquals(0, button.getCol());
        assertEquals("~", button.getText());
    }

    @Test
    void testUpdateDisplayHitAndMiss() {
        Board board = new Board();
        CellButton button = new CellButton(0, 0, board);

        // Set a miss
        board.setCell(0, 0, CellState.MISS);
        button.updateDisplay();
        assertEquals("M", button.getText());

        // Set a hit
        board.setCell(0, 0, CellState.HIT);
        button.updateDisplay();
        assertEquals("X", button.getText());
    }
}