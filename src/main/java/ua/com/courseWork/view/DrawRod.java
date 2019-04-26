package ua.com.courseWork.view;

import java.awt.*;
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
import org.jzy3d.plot3d.text.drawable.DrawableTextBitmap;
import ua.com.courseWork.controller.DataStorage;
import ua.com.courseWork.model.Edge;
import ua.com.courseWork.model.Point;

public class DrawRod {

    public static List<ConcurrentLineStrip> setLines(DataStorage storage) {
        List<ConcurrentLineStrip> lines = new LinkedList<>();

        for (int i = 0; i < storage.numberEdges(); i++) {
            Edge edge = storage.getEdge(i);
            List<Coord3d> coord3ds = new LinkedList<>();

            Point p = edge.getFrom();
            coord3ds.add(new Coord3d(p.getX(), p.getY(), p.getZ()));
            p = edge.getTo();
            coord3ds.add(new Coord3d(p.getX(), p.getY(), p.getZ()));

            ConcurrentLineStrip scatter = new ConcurrentLineStrip(coord3ds);
            scatter.setWidth(3);
            scatter.setWireframeColor(new Color(edge.getColor().getRed(),
                    edge.getColor().getGreen(),
                    edge.getColor().getBlue()));
            lines.add(scatter);
        }

        return lines;
    }

    public static void drawRow(List<ConcurrentLineStrip> lines, DataStorage dataStorage) {

// Create a drawable scatter with a colormap
//        ConcurrentLineStrip scatter = new ConcurrentLineStrip(coord3ds);

        Chart chart = new AWTChart(Quality.Advanced);

        for (ConcurrentLineStrip scatter :
                lines) {
            chart.add(scatter);
        }
// Create a chart and add the surface
        chart.open("Jzy3d Demo", 600, 600);
        File image = new File("image.png");
        try {
            chart.screenshot(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Robot r = null;
            try {
                r = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }

            PointerInfo a = MouseInfo.getPointerInfo();
            int x = (int) a.getLocation().getX();
            int y = (int) a.getLocation().getY();
            java.awt.Color color = r.getPixelColor(x, y);

            if (dataStorage.getEdges().stream().anyMatch(e -> e.getColor().equals(color))) {
                String temperature = dataStorage.getNumberToColor().getTemperatureAsNumber(color);
                DrawableTextBitmap textBitmap
                        = new DrawableTextBitmap(temperature,
                        new Coord3d(0, 0, 30), Color.BLACK);
                System.out.println(temperature);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
