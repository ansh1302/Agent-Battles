package three;

import javax.swing.*;
import java.awt.*;

public class FieldThree {
    public static void main(String[] args) {
    	
    	//create frame and specify its characteristics
        JFrame frame = new JFrame("Agent Battles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.add(new ScenarioThree());
        frame.setVisible(true);
    }
}