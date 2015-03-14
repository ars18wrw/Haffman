import haffman.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class HaffmanFrame extends JFrame {

    Map<Character, BitSet> map;

    JTextField text;
    JTextArea trans;
    JTextField check;

    JLabel textLabel;
    JLabel transLabel;
    JLabel checkLabel;

    JButton done;

    JPanel upPanel;
    JPanel downPanel;
    JPanel centerPanel;

    JTextField stat;

    JList<String> table;



    public HaffmanFrame() {

        setLayout(new BorderLayout());

        upPanel = new JPanel();
        textLabel = new JLabel("Try your word here.");
        text = new JTextField(20);
      //  text.setText("abcde01234");
        upPanel.add(textLabel, BorderLayout.NORTH);
        upPanel.add(text, BorderLayout.CENTER);
        add(upPanel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        transLabel = new JLabel("Find your answer here.");
        trans = new JTextArea(4, 20);

        centerPanel.add(transLabel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(trans), BorderLayout.CENTER);
        // we will add centralPanel some strings later

        downPanel = new JPanel();
        stat = new JTextField(10);
        JLabel statLabel = new JLabel("Stat");
        downPanel.add(statLabel);
        downPanel.add(stat);
        add(downPanel, BorderLayout.SOUTH);

        final HaffmanPanel hfp = new HaffmanPanel();

        final DefaultListModel<String> listModel = new DefaultListModel<String>();
        listModel.addElement("Empty");
        table = new JList<String>(listModel);
        add(new JScrollPane(table), BorderLayout.WEST);

        done = new JButton("Try");
        pack();
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = text.getText();
                if (s.equals("")) {
                    JOptionPane.showMessageDialog(upPanel, "Please, write your text.");
                    return;
                }
                Map<Character, Integer> freq = Haffman.findFreq(s);
                PriorityQueue<Node> queue = new PriorityQueue<>();
                for (Map.Entry<Character, Integer> pair : freq.entrySet()) {
                    Node temp = new Node(pair.getValue(), pair.getKey());
                    queue.add(temp);
                }
                // bad case
                if (queue.size() == 1) {
                    Node left = queue.poll();
                    Node right = null;
                    Node temp = new Node(left.getKey(), ' ');
                    temp.setLeft(left);
                    temp.setRight(right);
                    left.setP(temp);
                    queue.add(temp);
                }
                // queue is working here
                while (queue.size() > 1) {
                    Node left = queue.poll();
                    Node right = queue.poll();
                    Node temp = new Node(left.getKey() + right.getKey(), ' ');
                    temp.setLeft(left);
                    temp.setRight(right);
                    left.setP(temp);
                    right.setP(temp);
                    queue.add(temp);
                }
                Node root = queue.peek();
                root.walkSmart(new StringBuffer(""));

                map = new HashMap<>();
                queue.peek().walkSmartEnd(map);

                // print
                StringBuffer buf = new StringBuffer();
                BitSet bs = new BitSet();
                for (int i = 0; i < s.length(); i++) {
                    bs = map.get(s.charAt(i));
                    int y = bs.length();
                    int z = bs.size();
                    for (int k = 0; k < bs.length()-1; k++) {
                        if (bs.get(k))
                            buf.append("1");
                        else
                            buf.append("0");
                    }
//                    buf.append(" ");
                }
                trans.setText(buf.toString());
                stat.setText(8 * text.getText().length() + " - " + buf.length());

                // list
                listModel.clear();
                for (Map.Entry<Character, BitSet> entry: map.entrySet()) {
                    buf.delete(0, buf.length());
                    bs = entry.getValue();
                    for (int k = 0; k < bs.length()-1; k++) {
                        if (bs.get(k))
                            buf.append("1");
                        else
                            buf.append("0");
                    }
                    listModel.addElement(entry.getKey().toString() + " - " + buf.toString());
                    hfp.setRoot(root);
                }

            }
        });
        add(done, BorderLayout.EAST);

        centerPanel.add(hfp, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);










    }

    class MyPanel extends JPanel {
        Node root;
        boolean[] arr;

        public MyPanel(Node r, boolean[] a) {
            root = r;
            arr = a;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            walkPaint(g, arr, 1, root);
        }

        public void setRoot(Node root) {
            this.root = root;
        }

        public void setArr(boolean[] arr) {
            this.arr = arr;
        }

        public void walkPaint(Graphics g, boolean[] arr, int i, Node r) {

            if (r != null) {
                int log = (int) Math.log(2*i);
                int pow = (int) Math.pow(2, log-1);
                g.drawOval(300+(i-pow)*30 - 15, 300+30*log - 15, 30, 30);
                g.setColor(Color.BLACK);
                g.fillOval(300+(i-pow)*30 - 15, 300+30*log - 15, 30, 30);
                if (r.isLeaf()) {
                    g.drawString(Integer.toString(r.getKey()), 300 + (i - pow) * 30 - 15, 300 + 30 * log - 15);
                }
                if (i%2 == 0)
                    g.setColor(Color.YELLOW);
                else g.setColor(Color.BLUE);

                if (i != 1)
                    g.drawLine(300+30*(((int)i/2)-((int)pow/2)), 300+30*(log-1), 300+(i-pow)*30, 300+30*log);
                if (r.getLeft() != null  && 2*i < arr.length)
                    r.getLeft().walkPaint(g, arr, 2 * i);
                if (r.getRight() != null && 2*i+1 < arr.length)
                    r.getRight().walkPaint(g, arr, 2 * i + 1);

            }
        }

    }

}
