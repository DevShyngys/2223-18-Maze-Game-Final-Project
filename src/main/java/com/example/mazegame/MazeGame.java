package com.example.mazegame;

import com.example.mazegame.command.*;
import com.example.mazegame.factory.MazeElementFactory;
import com.example.mazegame.factory.MazeElement;
import com.example.mazegame.factory.Wall;
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
    private int timeRemaining = 20;
    private Timer timer;
    private JPanel panel;

    private GameState state;
    private List<Observer> observers;
    private Command moveLeftCommand, moveRightCommand, moveUpCommand, moveDownCommand;
    private MovementStrategy moveStrategy;  // Стратегия движения
    // Изображения для Джерри и сыра
    private Image jerryImage;
    private Image cheeseImage;
    private Image combinedImage;
    public MazeGame() {
        maze = new MazeElement[rows][cols];
        this.state = new PlayingState();
        this.observers = new ArrayList<>();
        this.observers.add(new Player());

        // Инициализация команд для движения
        moveLeftCommand = new MoveLeftCommand();
        moveRightCommand = new MoveRightCommand();
        moveUpCommand = new MoveUpCommand();
        moveDownCommand = new MoveDownCommand();

        // Используем фиксированную стратегию движения (например, детерминированную)
        moveStrategy = new DeterministicMovementStrategy();
        try {
            jerryImage = ImageIO.read(new File("src/jerryimage.jpg"));
            cheeseImage = ImageIO.read(new File("src/cheese.jpeg"));
            combinedImage = ImageIO.read(new File("src/cheeslover.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Таймер для отслеживания времени
        timer = new Timer(1000, e -> {
            if (!gameWon && timeRemaining > 0) {
                timeRemaining--;
            } else if (timeRemaining == 0 && !gameWon) {
                notifyTimeUp();
                endGame(); // Завершаем игру, если время истекло
            }
            panel.repaint();
        });
        timer.start();
    }

    public void generateMaze() {
        // Заполняем лабиринт стенами
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = MazeElementFactory.createWall();
            }
        }

        // Устанавливаем начальную точку (путь)
        maze[1][1] = MazeElementFactory.createPath();
        generatePath(1, 1);  // Генерация путей

        // Устанавливаем конечную точку (путь) в правый нижний угол лабиринта
        rewardX = cols - 2;
        rewardY = rows - 2;
        maze[rewardY][rewardX] = MazeElementFactory.createPath();
    }

    // Рекурсивная генерация путей в лабиринте
    private void generatePath(int row, int col) {
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2});  // Вниз
        directions.add(new int[]{0, -2}); // Вверх
        directions.add(new int[]{2, 0});  // Вправо
        directions.add(new int[]{-2, 0}); // Влево

        Collections.shuffle(directions);  // Перемешиваем направления для случайности

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isInBounds(newRow, newCol) && maze[newRow][newCol] instanceof Wall) {
                maze[newRow][newCol] = MazeElementFactory.createPath();
                maze[row + direction[0] / 2][col + direction[1] / 2] = MazeElementFactory.createPath(); // Удаляем стену между
                generatePath(newRow, newCol);  // Рекурсивно генерируем путь
            }
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void start() {
        System.out.println("game start!");
        this.state.handle(this); // Обработка текущего состояния игры
        generateMaze(); // Генерация лабиринта
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

    public void endGame() {
        System.out.println("Игра окончена!");
        timer.stop(); // Останавливаем таймер
        this.state = new GameOverState(); // Смена состояния игры на Game Over
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
                            g.setColor(Color.BLACK);  // Стены
                        } else {
                            g.setColor(Color.WHITE);  // Путь
                        }
                        g.fillRect(col * size, row * size, size, size);
                    }
                }
                g.drawImage(jerryImage, playerX * 35, playerY * 35, 35, 35, this);

                g.drawImage(cheeseImage, rewardX * 35, rewardY * 35, 35, 35, this);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Time: " + timeRemaining + " sec", getWidth() - 120, 30);

                // Когда игра окончена, не даем рисовать таймер или надпись "Time's Up!"
                if (gameWon) {
                    int centerX = getWidth() / 2;
                    int centerY = getHeight() / 2;

                    // Отображаем третью фотографию (например, Джерри и сыр вместе) при победе
                    g.drawImage(combinedImage, centerX - 120, centerY - 120, 360, 360, this); // Отображаем изображение в центре

                    timer.stop(); // Останавливаем таймер

                    return; // Возвращаемся, чтобы не рисовать текст, только изображение
                }

                // Если время вышло, показываем текст "Time's Up!"
                if (timeRemaining == 0) {
                    int centerX = getWidth() / 2;
                    int centerY = getHeight() / 2;

                    // Показываем текст "Time's Up!" в центре экрана
                    g.setColor(Color.RED);
                    g.setFont(new Font("Arial", Font.BOLD, 50));
                    g.drawString("Time's Up!", centerX - 150, centerY);

                    timer.stop(); // Останавливаем таймер
                    return; // Возвращаемся, не рисуем больше ничего
                }
            }
        };

        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (timeRemaining == 0 || gameWon) return;  // Если время истекло или игра закончена, не реагируем на нажатия

                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                    moveStrategy.move(MazeGame.this, -1, 0); // Двигаем влево
                } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                    moveStrategy.move(MazeGame.this, 1, 0); // Двигаем вправо
                } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                    moveStrategy.move(MazeGame.this, 0, -1); // Двигаем вверх
                } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                    moveStrategy.move(MazeGame.this, 0, 1); // Двигаем вниз
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
    }


    private boolean isPlayerNearReward() {
        return (Math.abs(playerX - rewardX) <= 1 && playerY == rewardY) ||
                (Math.abs(playerY - rewardY) <= 1 && playerX == rewardX);
    }

    public static void main(String[] args) {
        MazeGame game = new MazeGame();
        game.generateMaze();
        game.createAndShowGUI();
    }
}
