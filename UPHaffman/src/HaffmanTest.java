import javax.swing.*;
import java.awt.*;

/**
 * Created by Уладзімір Асіпчук on 15.12.14.
 */
public class HaffmanTest {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new HaffmanFrame();
                frame.setTitle("Haffman");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(700, 700);
                frame.setVisible(true);
            }
        });
    }
}
