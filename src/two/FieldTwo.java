package two;

import javax.swing.*;
import java.awt.*;

public class FieldTwo {
    public static void main(String[] args) {
    	
    	//create frame and specify its characteristics
        JFrame frame = new JFrame("Agent Battles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.add(new ScenarioTwo());
        frame.setVisible(true);
    }
}