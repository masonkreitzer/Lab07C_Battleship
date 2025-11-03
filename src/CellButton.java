import javax.swing.JButton;
import java.awt.Color;

public class CellButton extends JButton {

    private final int row;
    private final int col;
    private final Board board;

    public CellButton(int row, int col, Board board) {
        this.row = row;
        this.col = col;
        this.board = board;

        setText("~");
        setBackground(Color.CYAN);

        // Button click listener handled in Main or GUI class
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    // Update display based on current cell state
    public void updateDisplay() {
        CellState state = board.getCell(row, col);
        switch (state) {
            case BLANK -> {
                setText("~");
                setBackground(Color.CYAN);
                setForeground(Color.BLACK);
            }
            case MISS -> {
                setText("M");
                setBackground(Color.YELLOW);
                setForeground(Color.BLACK);
            }
            case HIT -> {
                setText("X");
                setBackground(Color.RED);
                setForeground(Color.BLACK);
            }
        }

        setOpaque(true);
        setBorderPainted(true);
    }
}