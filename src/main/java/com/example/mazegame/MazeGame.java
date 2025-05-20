package com.example.mazegame;

import com.example.mazegame.command.*;
import com.example.mazegame.facade.GameFacade;
import com.example.mazegame.factory.MazeElementFactory;
import com.example.mazegame.factory.MazeElement;
import com.example.mazegame.factory.Wall;
import com.example.mazegame.singleton.GameSettings;
import com.example.mazegame.state.*;
import com.example.mazegame.observer.Observer;
import com.example.mazegame.observer.Player;
import com.example.mazegame.strategy.MovementStrategy;
import com.example.mazegame.strategy.DeterministicMovementStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MazeGame {
    public static int playerX = 1;
    public static int playerY = 1;
    public static final int rows = 20;
    public static final int cols = 20;
    public static MazeElement[][] maze;
    private boolean gameWon = false;
    private int rewardX, rewardY;
    private int timeRemaining = 240;
    private Timer enemyTimer;
    private Timer clockTimer;
    private JPanel panel;
    private static MazeGame instance;
    private GameState state;
    private List<Observer> observers;
    private Command moveLeftCommand, moveRightCommand, moveUpCommand, moveDownCommand;
    private MovementStrategy moveStrategy;
    private Image jerryImage;
    private Image cheeseImage;
    private Image combinedImage;
    private Image enemyImage;
    private int enemyX = cols - 2;
    private int enemyY = 1;
    private boolean gameOver = false;

    public static MazeGame getInstance() {
        if (instance == null) {
            instance = new MazeGame();
        }
        return instance;
    }

    public MazeGame() {
        maze = new MazeElement[rows][cols];
        this.state = new PlayingState();
        this.observers = new ArrayList<>();
        this.observers.add(new Player());
        moveLeftCommand = new MoveLeftCommand();
        moveRightCommand = new MoveRightCommand();
        moveUpCommand = new MoveUpCommand();
        moveDownCommand = new MoveDownCommand();
        moveStrategy = new DeterministicMovementStrategy();

        try {
            jerryImage = ImageIO.read(new File("src/jerryimage.jpg"));
            cheeseImage = ImageIO.read(new File("src/cheese.jpeg"));
            combinedImage = ImageIO.read(new File("src/cheeslover.jpg"));
            enemyImage = ImageIO.read(new File("src/tomimage.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        enemyTimer = new Timer(100, e -> {
            if (!gameWon && timeRemaining > 0) {
                moveEnemy();
            }
            if (panel != null) {
                panel.repaint();
            }
        });

        clockTimer = new Timer(1000, e -> {
            if (!gameWon && timeRemaining > 0) {
                timeRemaining--;
            } else if (timeRemaining == 0 && !gameWon) {
                notifyTimeUp();
                endGame();
            }
        });
    }

    public void generateMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = MazeElementFactory.createWall();
            }
        }
        maze[1][1] = MazeElementFactory.createPath();
        generatePath(1, 1);
        rewardX = cols - 2;
        rewardY = rows - 2;
        maze[rewardY][rewardX] = MazeElementFactory.createPath();
    }

    private void generatePath(int row, int col) {
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2});
        directions.add(new int[]{0, -2});
        directions.add(new int[]{2, 0});
        directions.add(new int[]{-2, 0});
        Collections.shuffle(directions);
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (isInBounds(newRow, newCol) && maze[newRow][newCol] instanceof Wall) {
                maze[newRow][newCol] = MazeElementFactory.createPath();
                maze[row + direction[0] / 2][col + direction[1] / 2] = MazeElementFactory.createPath();
                generatePath(newRow, newCol);
            }
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void start() {
        GameFacade.startGame(this);
    }

    public void setState(GameState state) {
        this.state = state;
    }

    private void notifyGameWon() {
        for (Observer observer : observers) {
            observer.onGameWon();
        }
    }

    private void notifyTimeUp() {
        for (Observer observer : observers) {
            observer.onTimeUp();
        }
    }

    private void moveEnemy() {
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 1});
        directions.add(new int[]{1, 0});
        directions.add(new int[]{0, -1});
        directions.add(new int[]{-1, 0});
        Collections.shuffle(directions);
        for (int[] dir : directions) {
            int newX = enemyX + dir[0];
            int newY = enemyY + dir[1];
            if (isInBounds(newY, newX) && !(maze[newY][newX] instanceof Wall)) {
                enemyX = newX;
                enemyY = newY;
                break;
            }
        }
        if (playerX == enemyX && playerY == enemyY) {
            System.out.println("LOSER!");
            gameOver = true;
            endGame();
        }
    }

    public void endGame() {
        System.out.println("Игра окончена!");
        enemyTimer.stop();
        clockTimer.stop();
        this.state = new GameOverState();
        if (panel != null) {
            panel.repaint();
        }
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int size = 35;
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        if (maze[row][col] instanceof Wall) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(col * size, row * size, size, size);
                    }
                }
                g.drawImage(jerryImage, playerX * 35, playerY * 35, 35, 35, this);
                g.drawImage(cheeseImage, rewardX * 35, rewardY * 35, 35, 35, this);
                g.drawImage(enemyImage, enemyX * 35, enemyY * 35, 35, 35, this);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Time: " + timeRemaining + " sec", getWidth() - 120, 30);

                if (gameWon) {
                    int centerX = getWidth() / 2;
                    int centerY = getHeight() / 2;
                    g.drawImage(combinedImage, centerX - 120, centerY - 120, 360, 360, this);
                    enemyTimer.stop();
                    clockTimer.stop();
                    return;
                }

                if (timeRemaining == 0) {
                    int centerX = getWidth() / 2;
                    int centerY = getHeight() / 2;
                    g.setColor(Color.RED);
                    g.setFont(new Font("Arial", Font.BOLD, 50));
                    g.drawString("Time's Up!", centerX - 150, centerY);
                    enemyTimer.stop();
                    clockTimer.stop();
                    return;
                }
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (timeRemaining == 0 || gameWon) return;

                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                    moveStrategy.move(MazeGame.this, -1, 0);
                } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                    moveStrategy.move(MazeGame.this, 1, 0);
                } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                    moveStrategy.move(MazeGame.this, 0, -1);
                } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                    moveStrategy.move(MazeGame.this, 0, 1);
                }

                if (isPlayerNearReward()) {
                    gameWon = true;
                    notifyGameWon();
                }

                panel.repaint();
            }
        });

        frame.add(panel);
        frame.setVisible(true);

        enemyTimer.start();
        clockTimer.start();
    }

    private boolean isPlayerNearReward() {
        return (Math.abs(playerX - rewardX) <= 1 && playerY == rewardY) ||
                (Math.abs(playerY - rewardY) <= 1 && playerX == rewardX);
    }

    public static void main(String[] args) {
        GameSettings settings = GameSettings.getInstance();
        MazeGame game = MazeGame.getInstance();
        game.generateMaze();
        game.createAndShowGUI();
    }
}
