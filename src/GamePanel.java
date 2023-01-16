import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {


    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 600;
    static int UNIT_SIZE = 25;
    static int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static int SPEED = 100;
    int x[] = new int[GAME_UNITS];
    int y[] = new int[GAME_UNITS];
    int bodyParts = 3;
    int appleEaten;
    int appleX;
    int appleY;
    char directon = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    boolean menu = false;
    boolean grid = true;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        if(!menu) startGame();
        
    }
    
    private void menu(Graphics g) {
        
        
        // příprava pro menu hry (není v provozu)

        g.setColor(Color.white);
        g.setFont( new Font("Open Sans", Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Snake", (SCREEN_WIDTH - metrics1.stringWidth("Snake")) / 2, SCREEN_HEIGHT / 2 - 150);
        
        g.setColor(Color.white);
        g.setFont( new Font("Open Sans", Font.BOLD,12));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("By Iriidi", (SCREEN_WIDTH - metrics2.stringWidth("By Iriidi") - 40), SCREEN_HEIGHT - 20);
        
        g.setColor(Color.white);
        g.setFont( new Font("Open Sans", Font.BOLD, 20));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Velikost mapy:", (SCREEN_WIDTH - metrics3.stringWidth("Velikost mapy:")) / 2 - 28, SCREEN_HEIGHT / 2 - 80);

        // ––– buttony –––      
               
        JTextField screenWidth = new JTextField();
        screenWidth.setBounds(SCREEN_WIDTH/2 - 75, SCREEN_HEIGHT/2+70, 90, 50);
        JTextField screenHeight = new JTextField();
        screenHeight.setBounds(SCREEN_WIDTH/2 - 75, SCREEN_HEIGHT/2+70, 90, 50);
        
        this.add(screenWidth);
        this.add(screenHeight);
       
        


    }

    private void startGame() {
        newApple();
        running = true;
        timer = new Timer(SPEED, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);        
    }
    private void draw(Graphics g) {
        if (grid) g.setColor(Color.darkGray);
        if (menu) {
            menu(g);
        } else {
            if(running) {            
                // –––––– pomocný grid ––––––
                if (grid) {
                    for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                        g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                        g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
                    }
                }

                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for(int i = 0; i< bodyParts;i++) {
                    if(i == 0) {
                        g.setColor(Color.green);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(45, 180, 0));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
                // ––––––––– score tabulka –––––––––
                g.setColor(Color.white);
                g.setFont( new Font("Open Sans", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Skóre: " +appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Skóre: " +appleEaten))/2, g.getFont().getSize());
            } else {
                gameOver(g);
            }
        }
    }
    private void newApple() {
        appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }
    private void move() {
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(directon) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;            
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;            
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    private void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }
    private void checkCollisions() {
        // ––––––––– kolize s tělem –––––––––
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        // ––––––––– kolize s koncem mapy –––––––––
        if(x[0] < 0) {
            running = false;
        }
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        if(y[0] < 0) {
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        
        if(!running) {
            timer.stop();
        }
        
        
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.white);
        g.setFont( new Font("Open Sans", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Skóre: " +appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Skóre: " +appleEaten))/2, g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont( new Font("Open Sans",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game over!", (SCREEN_WIDTH - metrics2.stringWidth("Game over!"))/2, SCREEN_HEIGHT/2);

        JButton retry = new JButton();
        retry.setBounds(SCREEN_WIDTH/2 - 75, SCREEN_HEIGHT/2+70, 150, 60);
        retry.setText("Zkusit znovu");
        retry.addActionListener(e -> restartGame());
        this.add(retry);
    }

    private void restartGame() {        
        new GameFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
        
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                // ovládání šipkama
                case KeyEvent.VK_LEFT:
                    if(directon != 'R') {
                        directon = 'L';
                    } break;

                case KeyEvent.VK_RIGHT:
                    if(directon != 'L') {
                        directon = 'R';
                    } break;

                case KeyEvent.VK_UP:
                    if(directon != 'D') {
                        directon = 'U';
                    } break;

                case KeyEvent.VK_DOWN:
                    if(directon != 'U') {
                        directon = 'D';
                    } break;
                
                // ovládání WASD
                case KeyEvent.VK_A:
                    if(directon != 'R') {
                        directon = 'L';
                    } break;

                case KeyEvent.VK_D:
                    if(directon != 'L') {
                        directon = 'R';
                    } break;

                case KeyEvent.VK_W:
                    if(directon != 'D') {
                        directon = 'U';
                    } break;

                case KeyEvent.VK_S:
                    if(directon != 'U') {
                        directon = 'D';
                    } break;
            }
        }
    }
}
