package ua.com.courseWork.database;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ua.com.courseWork.controller.NumberToColor;
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

    private long lastPointId;

    private long lastEdgeId;

    public void setPointMap(String fileName) throws IOException {

        FileReader in = new FileReader(fileName);
        BufferedReader br = new BufferedReader(in);
        pointMap = new TreeMap<>();
        String str;

        while ((str = br.readLine()) != null) {
            String[] dataArr = str.split(" +");
            Point point = new Point();

            if (dataArr.length == 3) {
                point.setId(lastPointId++);
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
        edgeDataMap = new TreeMap<>();
        String str;

        while ((str = br.readLine()) != null) {
            String[] dataArr = str.split(" +");
            EdgeData edgeData = new EdgeData();

            if (dataArr.length == 2) {
                edgeData.setId(lastEdgeId++);
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

        temperature = new TreeMap<>();
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

    public void addPoint(Point point) {
        point.setId(lastPointId++);
        pointMap.put(point.getId(), point);
    }

    public Edge getEdge(long id) {
        if (edgeDataMap == null || !edgeDataMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return new Edge(edgeDataMap.get(id), this);
    }

    public List<Edge> getEdges() {
        return getEdgesFrom(0);
    }

    public List<Edge> getEdgesFrom(long id) {
        List<Edge> edges = new LinkedList<>();
        edgeDataMap.values().stream().skip(id).forEach(e -> edges.add(new Edge(e, this)));
        return edges;
    }

    public Long addEdge(EdgeData edgeData) {
        edgeData.setId(lastEdgeId++);
        edgeDataMap.put(edgeData.getId(), edgeData);
        return edgeData.getId();
    }

    public Edge removeEdge(Long id) {
        return new Edge(edgeDataMap.remove(id), this);
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

    public long getLastPointId() {
        return lastPointId;
    }

    public long getLastEdgeId() {
        return lastEdgeId;
    }

    public void addTemperature(long id, double pointTemperature) {
        temperature.put(id, pointTemperature);
    }
}
