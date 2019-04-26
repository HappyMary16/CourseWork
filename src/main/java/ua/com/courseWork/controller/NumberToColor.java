package ua.com.courseWork.controller;

import com.sun.javafx.binding.StringFormatter;

import java.awt.*;

public class NumberToColor {

    private static final int MAX_VALUE_FOR_COLOR = 255;

    private double minTemperature;

    private double maxTemperature;

    public NumberToColor(double minTemperature, double maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public void setMinAndMax(double minTemperature, double maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public Color getColor(double temperature) {
        return new Color(
                Math.abs((int) (temperature * MAX_VALUE_FOR_COLOR / maxTemperature)), 0,
                MAX_VALUE_FOR_COLOR - Math.abs((int) (temperature * MAX_VALUE_FOR_COLOR / maxTemperature)));
    }

    public String getTemperatureAsNumber(Color color) {
        return String.format("%.2f", maxTemperature * color.getRed() / MAX_VALUE_FOR_COLOR);
    }
}
