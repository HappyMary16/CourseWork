package ua.com.courseWork.view;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.ConcurrentLineStrip;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import ua.com.courseWork.database.DataStorage;
import ua.com.courseWork.model.Edge;
import ua.com.courseWork.model.Node;

public class DrawRod {

    public static List<ConcurrentLineStrip> setLines(DataStorage storage) {
        List<ConcurrentLineStrip> lines = new LinkedList<>();

        for (Edge edge :
                storage.getEdges()) {
            List<Coord3d> coord3ds = new LinkedList<>();

            Node p = edge.getFrom();
            coord3ds.add(new Coord3d(p.getX(), p.getY(), p.getZ()));
            p = edge.getTo();
            coord3ds.add(new Coord3d(p.getX(), p.getY(), p.getZ()));

            ConcurrentLineStrip scatter = new ConcurrentLineStrip(coord3ds);
            scatter.setWidth(4);
            scatter.setWireframeColor(new Color(edge.getColor().getRed(),
                    edge.getColor().getGreen(),
                    edge.getColor().getBlue()));
            lines.add(scatter);
        }

        return lines;
    }

    public static void drawRow(List<ConcurrentLineStrip> lines, DataStorage dataStorage) {

        Chart chart = new AWTChart(Quality.Advanced);

        for (ConcurrentLineStrip scatter :
                lines) {
            chart.add(scatter);
        }

        chart.open("Jzy3d Demo", 600, 600);
        File image = new File("image.png");
        try {
            chart.screenshot(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
