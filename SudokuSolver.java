import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver extends JFrame {
    private final JTextField[][] grid = new JTextField[9][9];

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(9, 9));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Initialize the Sudoku grid with text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new JTextField();
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                grid[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                grid[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                if ((i / 3 + j / 3) % 2 == 0) {
                    grid[i][j].setBackground(new Color(204, 255, 255));
                } else {
                    grid[i][j].setBackground(Color.WHITE);
                }

                panel.add(grid[i][j]);
            }
        }

        JButton solveButton = new JButton("Solve");
        solveButton.setFont(new Font("Arial", Font.BOLD, 20));
        solveButton.setBackground(new Color(18, 215, 222));
        solveButton.setForeground(Color.WHITE);
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });

        add(panel, BorderLayout.CENTER);
        add(solveButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void solveSudoku() {
        int[][] board = new int[9][9];

        // Retrieve numbers from the text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = grid[i][j].getText();
                if (!text.isEmpty()) {
                    try {
                        board[i][j] = Integer.parseInt(text);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input at (" + (i + 1) + ", " + (j + 1) + "). Please enter numbers between 1 and 9.");
                        return;
                    }
                } else {
                    board[i][j] = 0;
                }
            }
        }

        if (solveBoard(board)) {
            displayBoard(board);
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists for the provided Sudoku.");
        }
    }

    private boolean solveBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveBoard(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private void displayBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].setText(Integer.toString(board[i][j]));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolver::new);
    }
}
