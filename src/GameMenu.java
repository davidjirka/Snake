import java.awt.*;
import javax.swing.JFrame;

public class GameMenu extends JFrame {

    GameMenu() {
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


}
