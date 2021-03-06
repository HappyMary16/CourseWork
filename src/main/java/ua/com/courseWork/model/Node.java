package ua.com.courseWork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node extends Entity {

    private static final long serialVersionUID = -6244976905366614391L;

    private double x;

    private double y;

    private double z;

    private double temperature;

    public Node(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
