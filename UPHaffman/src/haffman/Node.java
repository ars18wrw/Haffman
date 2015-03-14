package haffman;

import java.awt.*;
import java.util.BitSet;
import java.util.Map;

public class Node implements Comparable{

    private Node left;
    private Node right;
    private Node p;
    private int key;
    private BitSet bs;
    private char c;

    public Node(int key, char ch) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.p = null;
        bs = null;
        c = ch;
    }

    private void equ(Node node) {
        this.key = node.key;
        this.left = node.left;
        this.right = node.right;
        this.p = node.p;
    }

    public static Node max(Node node) {
        if (node == null)
            return null;
        while (node.right != null)
            node = node.right;
        return node;
    }

    public void insert(Node node) {
        Node y = null;
        Node x = this;
        while (x != null) {
            y = x;
            if (node.key < x.key)
                x = x.left;
            else x = x.right;
        }
        node.p = y;
        if (y == null)
            this.equ(node);
        else if (node.key < y.key)
            y.left = node;
        else y.right = node;
    }

    // u != null
    private void transplant(Node u, Node v) {
        if (u.p == null)
            this.equ(v);
        else if (u == u.p.left)
            u.p.left = v;
        else u.p.right = v;
        if (v != null)
            v.p = u.p;
    }

    public void delete(Node node) {
        Node y = null;
        if (node.left == null)
            this.transplant(node, node.right);
        else if (node.right == null)
            this.transplant(node, node.left);
        else {
            y = max(node.left);
            node.key = y.key;
            this.delete(y);
        }
    }

    public void walk() {
        if (this != null) {
        //    System.out.println(this.key + " " + this.c + " " + this.bs);
            if (this.left != null)
                this.left.walk();
            if (this.right != null)
                this.right.walk();
            if (this.isLeaf()) {
            //    System.out.println(this.key + " " + this.c + " " + this.bs);
            }
        }
    }

    public void walkSmart(StringBuffer s) {
        if (this != null) {
            if (this.left != null)
                this.left.walkSmart(s.append("0"));
            if (this.right != null)
                this.right.walkSmart(s.append("1"));
            if (this.isLeaf()) {
                System.out.println(this.key);
                bs = new BitSet(s.length()+1);
                for (int i = 0; i < s.length(); i++)
                    if (s.charAt(i) == '0')
                        bs.set(i, false);
                    else bs.set(i, true);
                bs.set(s.length(), true);
//                bs = fromString(s.toString());
                s.deleteCharAt(s.length() - 1);
            }
        }
    }
    public void walkSmartEnd(Map<Character, BitSet> map) {
        if (this != null) {
            if (this.left != null)
                this.left.walkSmartEnd(map);
            if (this.right != null)
                this.right.walkSmartEnd(map);
            if (this.isLeaf()) {
                map.put(this.c, this.bs);
            }
        }
    }


    public boolean isLeaf() {
        if (this.left == null && this.right == null)
            return true;
        else return false;
    }

    private static BitSet fromString(final String s) {
        return BitSet.valueOf(new long[] { Long.parseLong(s, 2) });
    }

    @Override
    public int compareTo(Object o) {
        Node t = (Node) o;
        return Integer.compare(this.key, ((Node) o).key);
    }

    public int getKey() {
        return key;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setP(Node p) {
        this.p = p;
    }

    public void walkPaint(Graphics g, boolean[] arr, int i) {

        if (this != null) {
            int log = (int) Math.log(2*i);
            int pow = (int) Math.pow(2, log-1);
            g.drawOval(300+(i-pow)*30 - 15, 300+30*log - 15, 30, 30);
            g.setColor(Color.BLACK);
            g.fillOval(300+(i-pow)*30 - 15, 300+30*log - 15, 30, 30);
            if (this.isLeaf()) {
                g.drawString(Integer.toString(this.getKey()), 300 + (i - pow) * 30 - 15, 300 + 30 * log - 15);
            }
            if (i%2 == 0)
                g.setColor(Color.YELLOW);
            else g.setColor(Color.BLUE);

            if (i != 1)
                g.drawLine(300+30*(((int)i/2)-((int)pow/2)), 300+30*(log-1), 300+(i-pow)*30, 300+30*log);
            if (this.left != null  && 2*i < arr.length)
                this.left.walkPaint(g, arr, 2*i);
            if (this.right != null && 2*i+1 < arr.length)
                this.right.walkPaint(g, arr, 2*i+1);

        }
    }

    public void walkForArrays(int i, boolean[] arr) {
        arr[i] = true;
        if (this.left != null && 2*i < arr.length)
            this.left.walkForArrays(2*i, arr);
        if (this.right != null && 2*i+1 < arr.length)
            this.right.walkForArrays(2*i+1, arr);
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public BitSet getBs() {
        return bs;
    }

    public Node getP() {
        return p;
    }

    public char getC() {
        return c;
    }
}





