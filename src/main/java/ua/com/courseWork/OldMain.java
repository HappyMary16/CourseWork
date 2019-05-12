package ua.com.courseWork;


import org.jfree.ui.RefineryUtilities;
import ua.com.courseWork.userInterface.UserInterface;

import javax.swing.*;
import java.awt.*;

public class OldMain {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Finite element method");
        frame.setContentPane(new UserInterface().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setMinimumSize(new Dimension(750, 550));

        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }
}
