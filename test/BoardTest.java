import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *Unit tests for the Board class.
 */
public class BoardTest {

    @Test
    void testBoardInitialization() {
        Board board = new Board();
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                assertEquals(CellState.BLANK, board.getCell(i, j));
            }
        }
    }

    @Test
    void testPlaceShipsAndFire() {
        Board board = new Board();
        int[] sizes = {2, 3};
        board.placeShips(sizes);
        assertEquals(2, board.getShips().size());

        // Fire at each ship coordinate
        for (Ship s : board.getShips()) {
            for (int[] coord : s.getCoordinates()) {
                boolean result = board.fire(coord[0], coord[1]);
                assertTrue(result);
                assertEquals(CellState.HIT, board.getCell(coord[0], coord[1]));
            }
            assertTrue(s.isSunk());
        }

        // Fire at a blank cell
        boolean miss = board.fire(9, 9);
        assertFalse(miss);
        assertEquals(CellState.MISS, board.getCell(9, 9));
    }
}