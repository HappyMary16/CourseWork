package ua.com.courseWork.userInterface;

import javax.swing.*;

public class IncorrectData extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public IncorrectData() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK() {
        dispose();
    }
}
