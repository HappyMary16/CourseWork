package ua.com.courseWork.model;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.courseWork.database.DataStorage;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge extends Entity{

    private static final long serialVersionUID = -1118000839097499331L;

    private Node from;

    private Node to;

    private double length;

    private Color color;

    public Edge(EdgeData data, DataStorage storage) {
        this(storage.getPoint(data.getFromId()), storage.getPoint(data.getToId()));
        this.id = data.id;
        this.color = storage
                .getNumberToColor()
                .getColor((this.from.getTemperature() + this.to.getTemperature()) / 2);
    }

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.length = Math.pow( Math.pow(this.from.getX() - this.to.getX(), 2)
                + Math.pow(this.from.getY() - this.to.getY(), 2)
                + Math.pow(this.from.getZ() - this.to.getZ(), 2), 0.5);
    }

    public void split(SplitType type, DataStorage storage) {
        Pair<Edge, Edge> pair = split(this, storage);

        split(pair.getKey(), type.getNumberElements(), storage);
        split(pair.getValue(), - type.getNumberElements(), storage);
    }

    private static void split(Edge edge, int type, DataStorage storage) {
        Pair<Edge, Edge> pair = split(edge, storage);

        if (type >= -1 && type <= 1) {
            if (type != 0) {
                split(pair.getValue(), storage);
                split(pair.getKey(), storage);
            }
            return;
        }

        split(pair.getKey(), type > 0 ? type - 1 : type + 2, storage);
        split(pair.getValue(), type > 0 ? type - 2 : type + 1, storage);
    }

    private static Pair<Edge, Edge> split(Edge edge, DataStorage storage) {
        Node point = new Node(
                (edge.from.getX() + edge.to.getX()) / 2,
                (edge.from.getY() + edge.to.getY()) / 2,
                (edge.from.getZ() + edge.to.getZ()) / 2);
        Edge edge1 = new Edge(edge.from, point);
        Edge edge2 = new Edge(point, edge.to);

        storage.removeEdge(edge.id);
        storage.addPoint(point);

        edge1.setId(storage.addEdge(new EdgeData(edge1)));
        edge2.setId(storage.addEdge(new EdgeData(edge2)));

        return new Pair<>(edge1, edge2);
    }
}
