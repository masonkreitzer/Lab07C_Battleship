import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final int size;
    private final List<int[]> coordinates;
    private int hits;

    public Ship(int size) {
        this.size = size;
        this.coordinates = new ArrayList<>();
        this.hits = 0;
    }

    public int getSize() {
        return size;
    }

    public void addCoordinate(int row, int col) {
        coordinates.add(new int[]{row, col});
    }

    public List<int[]> getCoordinates() {
        return coordinates;
    }

    public void registerHit() {
        hits++;
    }

    public boolean isSunk() {
        return hits >= size;
    }
}
