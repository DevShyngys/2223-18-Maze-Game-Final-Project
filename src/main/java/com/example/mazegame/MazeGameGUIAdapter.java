package com.example.mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeGameGUIAdapter {
    private int playerX = 1;
    private int playerY = 1;
    private final int rows = 20;
    private final int cols = 20;
    private int[][] maze;
    private boolean gameWon = false;
    private int rewardX, rewardY;
    private int timeRemaining = 20;
    private Timer timer;
    private JPanel panel;

    public MazeGameGUIAdapter() {
        maze = new int[rows][cols];
        generateMaze();
        rewardX = cols - 2;
        rewardY = rows - 2;

        timer = new Timer(1000, e -> {
            if (!gameWon && timeRemaining > 0) {
                timeRemaining--;
            } else if (timeRemaining == 0 && !gameWon) {

                gameWon = true;
            }
            panel.repaint();
        });
        timer.start();
    }

    public void generateMaze() {
        // Заполняем лабиринт стенами
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = 1;  // Стены
            }
        }

        // Выбираем начальную точку
        maze[1][1] = 0;  // Начальная точка

        generatePath(1, 1);

        // Устанавливаем конечную точку как путь
        maze[rewardY][rewardX] = 0;
    }
    private void generatePath(int row, int col) {
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2});  // Вниз
        directions.add(new int[]{0, -2}); // Вверх
        directions.add(new int[]{2, 0});  // Вправо
        directions.add(new int[]{-2, 0}); // Влево

        Collections.shuffle(directions);

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isInBounds(newRow, newCol) && maze[newRow][newCol] == 1) {
                maze[newRow][newCol] = 0;
                maze[row + direction[0] / 2][col + direction[1] / 2] = 0;
                generatePath(newRow, newCol);
            }
        }
    }
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private boolean isPlayerNearReward() {
        return (Math.abs(playerX - rewardX) <= 1 && playerY == rewardY) ||
                (Math.abs(playerY - rewardY) <= 1 && playerX == rewardX);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int size = 30;
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        if (maze[row][col] == 1) {
                            g.setColor(Color.BLACK);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(col * size, row * size, size, size);
                    }
                }
                g.setColor(Color.RED);
                g.fillRect(playerX * 30, playerY * 30, 30, 30);

                g.setColor(Color.GREEN);
                g.fillRect(rewardX * 30, rewardY * 30, 30, 30);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 20));  // Размер шрифта
                g.drawString("Time: " + timeRemaining + " sec", getWidth() - 120, 30);  // Время в правом верхнем углу

                if (gameWon) {
                    g.setColor(Color.BLUE);
                    g.setFont(new Font("Arial", Font.BOLD, 50));
                    if (timeRemaining == 0) {
                        g.drawString("Time's Up!", 250, 350);
                    } else {
                        g.drawString("You Win!", 250, 350);
                    }
                    timer.stop();
                }
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (gameWon) return;
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                    if (playerX > 0 && maze[playerY][playerX - 1] == 0) playerX--;
                } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                    if (playerX < cols - 1 && maze[playerY][playerX + 1] == 0) playerX++;
                } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                    if (playerY > 0 && maze[playerY - 1][playerX] == 0) playerY--;
                } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                    if (playerY < rows - 1 && maze[playerY + 1][playerX] == 0) playerY++;
                }
                if (isPlayerNearReward()) {
                    gameWon = true;
                }
                panel.repaint();  // Перерисовываем панель
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MazeGameGUIAdapter adapter = new MazeGameGUIAdapter();
        adapter.createAndShowGUI();  //отображаем игру
    }
}

