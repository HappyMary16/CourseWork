package ua.com.courseWork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.courseWork.controller.DataStorage;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge extends Entity{

    private static final long serialVersionUID = -1118000839097499331L;

    private Point from;

    private Point to;

    private double length;

    private Color color;

    public Edge(EdgeData data, DataStorage storage) {
        id = data.id;
        from = storage.getPoint(data.getFromId());
        to = storage.getPoint(data.getToId());
        length = Math.pow( Math.pow(from.getX() - to.getX(), 2)
                + Math.pow(from.getY() - to.getY(), 2)
                + Math.pow(from.getZ() - to.getZ(), 2), 0.5);
        color = storage
                .getNumberToColor()
                .getColor((from.getTemperature() + to.getTemperature()) / 2);
    }
}
