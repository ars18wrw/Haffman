package haffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.BitSet;

/**
 * Created by Уладзімір Асіпчук on 17.02.15.
 */
public class Haffman {
    public static Map<Character, Integer> findFreq(String s) {
        Map<Character, Integer> freq = new TreeMap<Character, Integer>();

        // frequency cycle
        for (int i = 0; i < s.length(); i++) {
            if (freq.containsKey(s.charAt(i)))
                freq.put(s.charAt(i), freq.get(s.charAt(i))+1);
            else freq.put(s.charAt(i),1);
        }
//        for (Integer i : freq.values()) {
//            System.out.println(i);
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println();

        return freq;
    }


    public static void main(String[] args) {
        String s = "aabbcd";
        Map<Character, Integer> freq = findFreq(s);
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
        queue.peek().walkSmart(new StringBuffer(""));

        Map<Character, BitSet> map = new HashMap<>();
        queue.peek().walkSmartEnd(map);

        for (int i = 0; i < s.length(); i++) {
            System.out.println(map.get(s.charAt(i)));
        }
    }



}

