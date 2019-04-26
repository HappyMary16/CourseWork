package ua.com.courseWork.userInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DrawResult extends JPanel{

    private BufferedImage image;
    private JTable table;
    private String[] namesCharts = null;
    private JScrollPane scrollPane;

    public DrawResult(HashMap<String, HashMap<Float, Double>> valuesMap) {

        table = new JTable(createResultTable(valuesMap), namesCharts);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        try {
            image = ImageIO.read(new File("LineChart.jpeg"));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        JLabel label = new JLabel(new ImageIcon(image));
        this.add(label);
        this.add(scrollPane);
    }

    private String[][] createResultTable(HashMap<String, HashMap<Float, Double>> valuesMap) {

        String[][] result;
        Set<Float> keys =  new TreeSet<>();
        namesCharts = valuesMap.keySet().toArray(new String[0]);

        for (String name:
                namesCharts) {
            keys.addAll(valuesMap.get(name).keySet());
        }

        result = new String[keys.size()][valuesMap.size() + 1];
        Float[] x = keys.toArray(new Float[0]);

        for (int i = 0; i < result.length; i++) {
            result[i][0] = String.format("%.4f", x[i]);

            for (int j = 0; j < namesCharts.length; j++) {
                Double number = valuesMap.get(namesCharts[j]).get(x[i]);

                result[i][j + 1] = number == null ? "" : String.format("%.4f", number);
            }
        }

        namesCharts = Arrays.copyOf(namesCharts, namesCharts.length + 1);
        System.arraycopy(namesCharts, 0, namesCharts,1, namesCharts.length - 1);
        namesCharts[0] = "x";

        return result;
    }
}
