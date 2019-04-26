package ua.com.courseWork.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ua.com.courseWork.model.Edge;
import ua.com.courseWork.model.EdgeData;
import ua.com.courseWork.model.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public class DataStorage {

    private Map<Long, Point> pointMap = null;

    private Map<Long, EdgeData> edgeDataMap = null;

    private Map<Long, Double> temperature = null;

    private NumberToColor numberToColor = null;

    public void setPointMap(String fileName) throws IOException {

        FileReader in = new FileReader(fileName);
        BufferedReader br = new BufferedReader(in);
        pointMap = new HashMap<>();
        String str;
        long id = 0;

        while ((str = br.readLine()) != null) {
            String[] dataArr = str.split(" +");
            Point point = new Point();

            if (dataArr.length == 3) {
                point.setId(id++);
                point.setX(Double.parseDouble(dataArr[0]));
                point.setY(Double.parseDouble(dataArr[1]));
                point.setZ(Double.parseDouble(dataArr[2]));
            } else {
                throw new IllegalStateException("File should have 3 number in each line!");
            }

            pointMap.put(point.getId(), point);
        }
    }

    public void setEdgeDataMap(String fileName) throws IOException {

        FileReader in = new FileReader(fileName);
        BufferedReader br = new BufferedReader(in);
        edgeDataMap = new HashMap<>();
        String str;
        long id = 0;

        while ((str = br.readLine()) != null) {
            String[] dataArr = str.split(" +");
            EdgeData edgeData = new EdgeData();

            if (dataArr.length == 2) {
                edgeData.setId(id++);
                edgeData.setFromId(Long.valueOf(dataArr[0]));
                edgeData.setToId(Long.valueOf(dataArr[1]));
            } else {
                throw new IllegalStateException("File should have 2 number in each line!");
            }

            edgeDataMap.put(edgeData.getId(), edgeData);
        }
    }

    public void setTemperature(String fileName) throws IOException {

        FileReader in = new FileReader(fileName);
        BufferedReader br = new BufferedReader(in);

        temperature = new HashMap<>();
        String str;

        while ((str = br.readLine()) != null) {
            String[] dataArr = str.split(" +");

            if (dataArr.length == 2) {
                temperature.put(Long.valueOf(dataArr[0]), Double.valueOf(dataArr[1]));
            } else {
                throw new IllegalStateException("File should have 2 number in each line!");
            }
        }

        for (long id :
                temperature.keySet()) {
            pointMap.get(id).setTemperature(temperature.get(id));
        }

        numberToColor = new NumberToColor(getMinTemperature(), getMaxTemperature());
    }

    public NumberToColor getNumberToColor() {
        return numberToColor;
    }

    public Point getPoint(long id) {

        if (pointMap == null || !pointMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return pointMap.get(id);
    }

    public Edge getEdge(long id) {
        if (edgeDataMap == null || !edgeDataMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return new Edge(edgeDataMap.get(id), this);
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new LinkedList<>();
        edgeDataMap.values().stream().forEach(e -> edges.add(new Edge(e, this)));
        return edges;
    }

    public int numberPoints() {
        return pointMap.size();
    }

    public int numberEdges() {
        return edgeDataMap.size();
    }

    public double getMaxTemperature() {
        return temperature.values().stream().max(Comparator.naturalOrder()).get();
    }

    public double getMinTemperature() {
        return temperature.values().stream().min(Comparator.naturalOrder()).get();
    }
}
