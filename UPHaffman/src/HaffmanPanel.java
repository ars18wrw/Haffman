import haffman.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Уладзімір Асіпчук on 14.03.15.
 */
public class HaffmanPanel extends JPanel {

    Node root;

    public HaffmanPanel() {
        setSize(100, 100);
        root = null;
    }


    public HaffmanPanel(Node aroot) {
        setSize(100, 100);
        root = aroot;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean[] arr = new boolean[100];
        if (null != root) {
            root.walkForArrays(1, arr);
            root.walkPaint(g, arr, 1);
        }
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
