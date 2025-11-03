import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int SIZE = 10;
    private final CellState[][] grid;
    private final List<Ship> ships;
    private final Random random;

    public Board() {
        grid = new CellState[SIZE][SIZE];
        ships = new ArrayList<>();
        random = new Random();

        // Initialize all cells to BLANK
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = CellState.BLANK;
            }
        }
    }

    public CellState getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, CellState state) {
        grid[row][col] = state;
    }

    public List<Ship> getShips() {
        return ships;
    }

    // Randomly place ships of given sizes
    public void placeShips(int[] shipSizes) {
        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = random.nextBoolean();
                int row = random.nextInt(SIZE);
                int col = random.nextInt(SIZE);

                // Check if ship can fit
                if (canPlaceShip(row, col, size, horizontal)) {
                    Ship ship = new Ship(size);
                    for (int i = 0; i < size; i++) {
                        int r = row + (horizontal ? 0 : i);
                        int c = col + (horizontal ? i : 0);
                        ship.addCoordinate(r, c);
                    }
                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal && col + size > SIZE) return false;
        if (!horizontal && row + size > SIZE) return false;

        for (int i = 0; i < size; i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);

            // Ensure no overlapping
            for (Ship s : ships) {
                for (int[] coord : s.getCoordinates()) {
                    if (coord[0] == r && coord[1] == c) return false;
                }
            }
        }
        return true;
    }

    // Process a shot: returns true if hit, false if it misses
    public boolean fire(int row, int col) {
        for (Ship s : ships) {
            for (int[] coord : s.getCoordinates()) {
                if (coord[0] == row && coord[1] == col) {
                    s.registerHit();
                    grid[row][col] = CellState.HIT;
                    return true;
                }
            }
        }
        grid[row][col] = CellState.MISS;
        return false;
    }
}