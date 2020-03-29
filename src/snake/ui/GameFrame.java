package snake.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pojo.SnakeCell;

/**
 *
 * @author GodOfHammers
 */
public class GameFrame extends JFrame implements KeyListener {

    char[] clrs = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f'};
    private SnakeCell head;
    private int direction = 1;
    private ArrayList<SnakeCell> snake;
    private int score = 0;
    private JLabel scoreLabel;
    private JPanel food;

    public GameFrame() {

        setSize(900, 500);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        initComponents();
        addKeyListener(GameFrame.this);
        play();
    }

    private void play() {

        Thread gamePlay;
        gamePlay = new Thread(() -> {
            while (true) {
                try {
                    Queue<Point> locs = new LinkedList();

                    moveHead();
                    Thread.sleep(100);
                    locs.add(head.getBody().getLocation());
                    for (int i = 1; i < snake.size(); i++) {
                        locs.add(snake.get(i).getBody().getLocation());
                        snake.get(i).getBody().setLocation(locs.poll());

                    }

                    for (int i = 1; i < snake.size(); i++) {
                        if (inRange(head.getBody().getX(), head.getBody().getY(), snake.get(i).getBody().getX(), snake.get(i).getBody().getY()) && i > 3) {

                            playAgain();
                            break;
                        }
                    }
                    if (inRange(head.getBody().getX(), head.getBody().getY(), food.getX(), food.getY())) {
                        System.out.println(food.getX() + " " + food.getY());
                        scoreLabel.setText("Score " + (++score));
                        addBodyCell();
                        locateFood();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        gamePlay.start();

    }

    @Override
    public void keyTyped(KeyEvent ke) {

        switch (ke.getKeyChar()) {
            case 'w':
            case 'W': {
                if (direction != 4) {
                    direction = 2;
                } else {
                    ke.consume();
                }
                break;
            }
            case 'a':
            case 'A':
                if (direction != 1) {
                    direction = 3;
                } else {
                    ke.consume();
                }
                break;
            case 's':
            case 'S':
                if (direction != 2) {
                    direction = 4;
                } else {
                    ke.consume();
                }
                break;

            case 'd':
            case 'D':
                if (direction != 3) {
                    direction = 1;
                } else {
                    ke.consume();
                }
                break;

        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    private void initComponents() {
        scoreLabel = new JLabel("Score " + score);
        scoreLabel.setLocation(0, 0);
        scoreLabel.setPreferredSize(new Dimension(100, 20));
        scoreLabel.setSize(new Dimension(100, 20));
        scoreLabel.setForeground(Color.yellow);
        add(scoreLabel);
        head = new SnakeCell(0);
        snake = new ArrayList<>();
        snake.add(head);
        head.getBody().setBackground(Color.RED);
        head.getBody().setLocation(350, 200);
        add(head.getBody());
        food = new JPanel();
        food.setSize(25, 25);
        food.setPreferredSize(new Dimension(25, 25));
        food.setBackground(Color.yellow);
        locateFood();
        add(food);
    }

    private void locateFood() {
        int x = ((int) (Math.random() * 1000) % 800);
        int y = ((int) (Math.random() * 1000) % 400);
        food.setLocation(x, y);
        revalidate();
    }

    private boolean inRange(int x1, int y1, int x2, int y2) {
        return ((x1 >= x2 && x1 <= x2 + 25) && (y1 >= y2 && y1 <= y2 + 25));
    }

    private void addBodyCell() {
        SnakeCell newCell = new SnakeCell(score);
        String clr = "";
        for (int i = 0; i < 6; i++) {
            clr += clrs[(int) ((Math.random() * 20) % 15)];
        }
        newCell.getBody().setBackground(Color.decode("#" + clr));
        Point cellLocation = new Point(-25,-25);

        newCell.getBody().setLocation(cellLocation);
        snake.add(newCell);
        add(newCell.getBody());
    }

    private void moveHead() {
        Point newLocation = new Point();
        switch (direction) {

            case 1: {
                newLocation = new Point((head.getBody().getX() + 25), (head.getBody().getY()));
                break;
            }
            case 2: {
                newLocation = new Point((head.getBody().getX()), (head.getBody().getY() - 25));
                break;

            }
            case 3: {
                newLocation = new Point((head.getBody().getX() - 25), (head.getBody().getY()));
                break;

            }
            case 4: {
                newLocation = new Point((head.getBody().getX()), (head.getBody().getY() + 25));
                break;

            }

        }

        head.getBody().setLocation(newLocation);
        if (head.getBody().getLocation().x < 0 || head.getBody().getLocation().y < 0 || head.getBody().getLocation().x > 875 || head.getBody().getLocation().y > 475) {
            playAgain();
        }

    }

    private void playAgain() {
        JOptionPane.showMessageDialog(this, "Game Over");
        snake.clear();
        locateFood();
        getContentPane().removeAll();
        score = 0;
        scoreLabel.setText("Score " + 0);
        add(head.getBody());
        add(scoreLabel);
        add(food);
        snake.add(head);
        repaint();
        revalidate();
        head.getBody().setLocation(350, 200);
        direction = 1;

    }
}
