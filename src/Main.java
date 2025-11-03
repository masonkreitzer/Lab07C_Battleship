import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This program implements a single-player version of the Battleship game using Java Swing.
 * The player clicks on a 10x10 grid to fire at hidden ships. The game tracks hits, misses,
 * strikes, and total hits/misses, shows messages when ships are sunk, and handles winning,
 * losing, and restarting the game.
 */
public class Main {

    private final JFrame frame;
    private final Board board;
    private final JLabel missCounterLabel;
    private final JLabel strikeCounterLabel;
    private final JLabel totalMissLabel;
    private final JLabel totalHitLabel;
    private int missCounter = 0;
    private int strikeCounter = 0;
    private int totalMiss = 0;
    private int totalHit = 0;

    public Main() {
        board = new Board();
        int[] SHIP_SIZES = {5, 4, 3, 3, 2};
        board.placeShips(SHIP_SIZES);

        frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = getJPanel();

        JPanel statusPanel = new JPanel(new GridLayout(2, 2));
        missCounterLabel = new JLabel("MISS: 0");
        strikeCounterLabel = new JLabel("STRIKE: 0");
        totalMissLabel = new JLabel("TOTAL MISS: 0");
        totalHitLabel = new JLabel("TOTAL HIT: 0");

        statusPanel.add(missCounterLabel);
        statusPanel.add(strikeCounterLabel);
        statusPanel.add(totalMissLabel);
        statusPanel.add(totalHitLabel);

        JPanel buttonPanel = new JPanel();
        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> playAgain());
        quitButton.addActionListener(e -> quitGame());

        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(600, 700);
        frame.setVisible(true);
    }

    private JPanel getJPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(Board.SIZE, Board.SIZE));
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                CellButton btn = new CellButton(i, j, board);
                boardPanel.add(btn);

                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleClick(btn);
                    }
                });
            }
        }
        return boardPanel;
    }

    private void handleClick(CellButton btn) {
        int row = btn.getRow();
        int col = btn.getCol();

        // Ignore clicks on already fired cells
        if (board.getCell(row, col) != CellState.BLANK) {
            return;
        }

        if (board.fire(row, col)) { // hit
            totalHit++;
            missCounter = 0;
            btn.updateDisplay();

            // Check if any ship has just been sunk
            for (Ship s : board.getShips()) {
                if (s.isSunk() && !s.isSunkAnnounced()) {
                    s.setSunkAnnounced();
                    JOptionPane.showMessageDialog(frame, "A ship has been sunk!");

                    boolean allSunk = board.getShips().stream().allMatch(Ship::isSunk);
                    if (allSunk) {
                        int choice = JOptionPane.showOptionDialog(
                                frame,
                                "You won! Do you want to play again?",
                                "Victory!",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                null,
                                null
                        );
                        if (choice == JOptionPane.YES_OPTION) {
                            restartGame();
                        } else {
                            System.exit(0);
                        }
                    }
                    break;
                }
            }

        } else { // miss
            missCounter++;
            totalMiss++;
            btn.updateDisplay();

            if (missCounter >= 5) {
                strikeCounter++;
                missCounter = 0;
                JOptionPane.showMessageDialog(frame, "Strike " + strikeCounter + "!");
                if (strikeCounter >= 3) {
                    int choice = JOptionPane.showOptionDialog(
                            frame,
                            "You lost! Do you want to play again?",
                            "Game Over",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            null,
                            null
                    );
                    if (choice == JOptionPane.YES_OPTION) {
                        restartGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
        updateStatus();
    }

    private void updateStatus() {
        missCounterLabel.setText("MISS: " + missCounter);
        strikeCounterLabel.setText("STRIKE: " + strikeCounter);
        totalMissLabel.setText("TOTAL MISS: " + totalMiss);
        totalHitLabel.setText("TOTAL HIT: " + totalHit);
    }

    private void playAgain() {
        int choice = JOptionPane.showOptionDialog(
                frame,
                "Are you sure you want to start a new game?",
                "Play Again",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
            new Main(); // restart game
        }
    }

    private void restartGame() {
        frame.dispose();
        new Main(); // start a new game immediately
    }

    private void quitGame() {
        int choice = JOptionPane.showOptionDialog(
                frame,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}