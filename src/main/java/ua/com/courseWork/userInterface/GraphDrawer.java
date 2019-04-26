package ua.com.courseWork.userInterface;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;
import ua.com.courseWork.MSE;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class GraphDrawer{

    private final XYSeriesCollection dataSet = new XYSeriesCollection( );

    /**
     * Draw the all charts
     */
    public void draw() {
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                "",
                "x","fi",
                dataSet,
                PlotOrientation.VERTICAL,
                true,false,false);

        final XYPlot plot = xyLineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        plot.setRenderer(renderer);

        int width = 640;    /* Width of the image */
        int height = 480;   /* Height of the image */
        File lineChart = new File( "LineChart.jpeg" );

        try {
            ChartUtilities.saveChartAsJPEG(lineChart ,xyLineChart, width ,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the data set for the chart and HashMap for the table
     *
     * @param firstFi the value the function in the first point
     * @param lastFi the value the function in the last point
     * @param lengthElements the list with the elements length
     * @return the HashMap with the data for table
     */
    public HashMap<Float, Double> createDataSet(double firstFi, double lastFi, List<Double> lengthElements) {
        final XYSeries graph = new XYSeries(lengthElements.size());
        HashMap<Float, Double> result = new HashMap<>();
        double[] fiValue = new double[lengthElements.size() + 1];

        fiValue[0] = firstFi;
        System.arraycopy(MSE.calculateResult(lengthElements, firstFi, lastFi),
                0, fiValue, 1, fiValue.length - 2);
        fiValue[lengthElements.size()] = lastFi;

        for (double i = 0, k = 0; i < fiValue.length; i++) {
            result.put((float) k, fiValue[(int)i]);
            graph.add(k, fiValue[(int)i]);
            k += i != fiValue.length - 1 ? lengthElements.get((int)i) : 0;
        }

        dataSet.addSeries(graph);
        return result;
    }
}
