package ua.com.courseWork.userInterface;

import lombok.Data;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class UserInterface extends JPanel{

    private JPanel panel;
    private JTextField lengthTextField;
    private JTextField numberTextField;
    private JTextField firstFiTextField;
    private JTextField endFiTextField;
    private JTextPane list;
    private JButton calculateWithLengths;
    private JButton runButton;
    private JButton clearAll;

    private List<Double> lengthElements = new ArrayList<>();
    private double fullLength;
    private double firstFi;
    private double endFi;

    private GraphDrawer graphDrawer;
    private HashMap<String, HashMap<Float, Double>> valuesMap;

    private final String regExDouble = "^\\d+\\.?\\d*$";
    private final String regExInteger = "^\\d+$";
    private final String regExListNumbers = "^[\\d+\\.?\\d*\\s+]+$";


    public UserInterface() {
        valuesMap  = new HashMap<>();
        graphDrawer = new GraphDrawer();
        runButton.addActionListener(e -> runButtonActionListener());
        calculateWithLengths.addActionListener(e -> calculateWithLengthsActionListener());
        clearAll.addActionListener(e -> clearAllActionListener());
    }

    /**
     * Create the data set for the chart and the map with the data for the table
     */
    public void calculate() {
        int i = -1;
        while (valuesMap.containsKey(lengthElements.size() + "(" + ++i + ")"));
        valuesMap.put(lengthElements.size() + "(" + i + ")", graphDrawer.createDataSet(firstFi, endFi, lengthElements));
    }

    /**
     * The action listener for button <code>calculateWithLengths</code>
     */
    private void calculateWithLengthsActionListener() {

        if (checkDataFormat()) {

            if (checkData()) {

                if (lengthTextField.isEnabled()) {
                    fullLength = Double.valueOf(lengthTextField.getText());
                    firstFi = Double.valueOf(firstFiTextField.getText());
                    endFi = Double.valueOf(endFiTextField.getText());

                    lengthTextField.setEnabled(false);
                    firstFiTextField.setEnabled(false);
                    endFiTextField.setEnabled(false);
                }

                if (list.getText().isEmpty()) {
                    addDefaultElementsLength();
                }

                calculate();

            } else {

                NotFatalIncorrectData dialog = new NotFatalIncorrectData();
                dialog.pack();
                RefineryUtilities.centerFrameOnScreen(dialog);
                dialog.setVisible(true);

                if (dialog.isCalculate()) {
                    addDefaultElementsLength();
                    calculate();
                }
            }

        } else {

            IncorrectData dialog = new IncorrectData();
            dialog.pack();
            RefineryUtilities.centerFrameOnScreen(dialog);
            dialog.setVisible(true);
        }

        lengthElements.clear();
    }

    /**
     * Create the list with elements length when known the number elements
     */
    private void addDefaultElementsLength() {
        lengthElements.clear();
        int number = Integer.valueOf(numberTextField.getText());

        for (int i = 0; i < number; i++) {
            lengthElements.add(fullLength / number);
        }
    }

    /**
     * Check all input data
     *
     * @return TRUE if all data are correct, FALSE else
     */
    private boolean checkData() {
        if (!list.getText().isEmpty()) {
            for (String d :
                    list.getText().split("\\s+")) {
                lengthElements.add(Double.valueOf(d));
            }

            double length = lengthElements
                    .stream()
                    .reduce((e1, e2) -> e1 + e2).orElse(0.);
            fullLength = Double.valueOf(lengthTextField.getText());

            if (length != fullLength) {

                if (!numberTextField.getText().isEmpty()
                        && (!numberTextField.getText().equals("0")
                        || !numberTextField.getText().equals("1"))) {
                    return false;
                }
            }
        }

        if (!numberTextField.getText().isEmpty()
                && (!numberTextField.getText().equals("0")
                || !numberTextField.getText().equals("1"))) {

            if (!list.getText().isEmpty()
                    && lengthElements.size() != Integer.valueOf(numberTextField.getText())) {
                    return false;
            }
        }

        return true;
    }

    /**
     * Check all data format
     *
     * @return TRUE if data format is correct, FALSE else
     */
    private boolean checkDataFormat() {
        if (lengthTextField.getText().matches(regExDouble)
                && endFiTextField.getText().matches(regExDouble)
                && firstFiTextField.getText().matches(regExDouble)
                && (numberTextField.getText().matches(regExInteger) || list.getText().matches(regExListNumbers))) {

            if (list.getText().isEmpty()
                    && (numberTextField.getText().equals("0")
                    || numberTextField.getText().equals("1"))) {
                return false;
            }
            return true;
        }

        return false;
    }

    /**
     * The action listener for <code>runButton</code>
     * Draw all charts and the table with result data
     * Create new window with result (the charts and the table)
     */
    private void runButtonActionListener() {
        graphDrawer.draw();

        JFrame resultFrame = new JFrame("Result");
        resultFrame.setContentPane(new DrawResult(valuesMap));
        resultFrame.pack();
        RefineryUtilities.centerFrameOnScreen(resultFrame);
        resultFrame.setVisible(true);
    }

    /**
     * The action listener for <code>clearAll</code> button.
     * Clear all fields and set them enable
     */
    private void clearAllActionListener() {
        graphDrawer.getDataSet().removeAllSeries();
        valuesMap = new HashMap<>();

        lengthTextField.setEnabled(true);
        firstFiTextField.setEnabled(true);
        endFiTextField.setEnabled(true);

        lengthTextField.setText("");
        firstFiTextField.setText("");
        endFiTextField.setText("");
        numberTextField.setText("");
        list.setText("");
    }
}
