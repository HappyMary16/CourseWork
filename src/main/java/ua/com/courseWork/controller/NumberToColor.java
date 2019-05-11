package ua.com.courseWork.controller;

import java.awt.*;

public class NumberToColor {

    private static final int MAX_VALUE_FOR_COLOR = 255;

    private double minTemperature;

    private double maxTemperature;

    public NumberToColor(double minTemperature, double maxTemperature) {
        setMinAndMax(minTemperature, maxTemperature);
    }

    public void setMinAndMax(double minTemperature, double maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        if(minTemperature < 0) {
            this.maxTemperature -= minTemperature;
        }

        System.out.println(this.minTemperature + "  " + this.maxTemperature);
    }

    public Color getColor(double temperature) {
        temperature -= minTemperature;
        return new Color(
                Math.abs((int) (temperature * MAX_VALUE_FOR_COLOR / maxTemperature)), 0,
                MAX_VALUE_FOR_COLOR - Math.abs((int) (temperature * MAX_VALUE_FOR_COLOR / maxTemperature)));
    }

    public String getTemperatureAsNumber(Color color) {
        double temperature = maxTemperature * color.getRed() / MAX_VALUE_FOR_COLOR;
        return String.format("%.2f", temperature + minTemperature);
    }
}
