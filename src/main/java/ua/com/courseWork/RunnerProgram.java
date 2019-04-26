package ua.com.courseWork;

import ua.com.courseWork.controller.DataStorage;
import ua.com.courseWork.view.DrawRod;

import java.io.IOException;

public class RunnerProgram {

    public static void main(String[] args) {
        DataStorage dataStorage = new DataStorage();
        try {
            dataStorage.setPointMap("src/points");
            dataStorage.setEdgeDataMap("src/edges");
            dataStorage.setTemperature("src/temperatures");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DrawRod.drawRow(DrawRod.setLines(dataStorage), dataStorage);
    }
}
