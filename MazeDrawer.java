import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeDrawer extends JComponent implements KeyListener {
    private int[][] maze;
    private int playerRow;
    private int playerCol;
    private int currentLevel = 0; // Current level
    private int[][][] levels; // Define multiple maze levels

    private int moveCount = 0;
    private int[] moveRecord = new int[9];
    private boolean hasWon = false;
    private int totalMoves = 0;

    public MazeDrawer(int[][][] levels, int playerRow, int playerCol) {
        this.levels = levels; // Initialize the levels
        this.maze = levels[currentLevel]; // Set the current maze to the first level
        this.playerRow = playerRow;
        this.playerCol = playerCol;
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.translate(50, 50);
        super.paintComponent(g);

        int cellSize = 20;

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                Color color;
                switch (maze[row][col]) {
                    case 1:
                        color = Color.BLACK;
                        break;
                    case 9:
                        color = Color.RED;
                        break;
                    default:
                        color = Color.PINK;
                        break;
                }
                g.setColor(color);
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }

        g.setColor(Color.GREEN);
        int playerSize = cellSize / 2;
        int x = playerCol * cellSize + (cellSize / 2 - playerSize / 2);
        int y = playerRow * cellSize + (cellSize / 2 - playerSize / 2);
        g.fillOval(x, y, playerSize, playerSize);

        g.setColor(Color.BLACK);

        if(moveCount > 0) {
            g.drawString("Number of moves: " + (moveCount), 400, 20);
        }

        if(moveCount == 0) {
            g.drawString("You are the green circle.", 400, 50);
            g.drawString("Use arrow keys to move.", 400, 70);
            g.drawString("Reach the red square to advance.", 400, 90);
            g.setFont(new Font("Arial", Font.ITALIC, 18));
            g.drawString("Level " + (currentLevel + 1), 400, 20);
        }

        if (hasWon) {            
            g.setColor(Color.BLUE);
            for(int i = 0; i < moveRecord.length; i++)
            {
                totalMoves += moveRecord[i];
            }
            g.drawString("Total Moves: " + totalMoves, getWidth() / 2 - 40, getHeight() / 2 + 20);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("You Win!", getWidth() / 2 - 50, getHeight() / 2);
        }
    }

    public void movePlayer(int newRow, int newCol) {
        if (!hasWon) {
            if (isValidMove(newRow, newCol)) {
                playerRow = newRow;
                playerCol = newCol;
    
                if (maze[playerRow][playerCol] == 9) {
                    if (currentLevel < levels.length - 1) {   
                        moveRecord[currentLevel] = moveCount;                     
                        currentLevel++; // Transition to the next level
                        maze = levels[currentLevel];
                        // Reset player position
                        playerRow = 1;
                        playerCol = 1; 
                        moveCount = -1;
                        hasWon = false; // Clear the win flag
                    } else {
                        hasWon = true; // Indicate the player has won all levels
                    }
                }
                moveCount++;
                repaint();
            }
        }
    }
    

    private boolean isValidMove(int newRow, int newCol) {
        return newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length && maze[newRow][newCol] != 1;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but required to implement KeyListener
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int newRow = playerRow;
        int newCol = playerCol;

        if (keyCode == KeyEvent.VK_UP) {
            newRow--;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            newRow++;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            newCol--;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            newCol++;
        }

        movePlayer(newRow, newCol);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used, but required to implement KeyListener
    }

    public static void main(String[] args) {
        int[][] level1 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 9, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        
        int[][] level2 = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 9, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
        };
        
        int[][] level3 = {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 9, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        };

        int[][] level4 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 9, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };        

        int[][] level5 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int[][] level6 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 1, 9, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        
        int[][] level7 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 9, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        
        int[][] level8 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 9, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };        

        int [][] level9 = { 
            {1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,1,0,1,0,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,1,0,1,1,1,0,1},
            {1,0,0,0,1,1,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,9,0,0,1},
            {1,0,1,0,1,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,1,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        int[][][] levels = { level1, level2, level3, level4, level5, level6, level7, level8, level9 };

        JFrame frame = new JFrame("EXO Maze");
        MazeDrawer mazeDrawer = new MazeDrawer(levels, 1, 1);
        frame.add(mazeDrawer);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
