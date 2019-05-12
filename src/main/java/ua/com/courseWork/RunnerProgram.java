package ua.com.courseWork;

import ua.com.courseWork.controller.MSE;
import ua.com.courseWork.database.DataStorage;
import ua.com.courseWork.model.Edge;
import ua.com.courseWork.model.SplitType;
import ua.com.courseWork.view.DrawRod;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RunnerProgram {

    public static void main(String[] args) {
        DataStorage dataStorage = new DataStorage();
        try {
            dataStorage.setPointMap("src/points");
            dataStorage.setEdgeDataMap("src/edges.txt");
            dataStorage.setTemperature("src/temperatures.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberEdges;
        List<Edge> edges = dataStorage.getEdges();

        for (Edge edge:
             edges) {
            numberEdges = dataStorage.numberEdges();

            edge.split(SplitType.HIGHT, dataStorage);

            LinkedList<Edge> splittedEdge = (LinkedList<Edge>) dataStorage.getEdgesFrom(numberEdges - 1);

            splittedEdge.addFirst(splittedEdge.removeLast());
            splittedEdge.addFirst(splittedEdge.removeLast());
            splittedEdge.addFirst(splittedEdge.remove(4));

            double[] temperatures = MSE.calculateResult(splittedEdge.stream().mapToDouble(Edge::getLength).boxed().collect(Collectors.toList()),
                    splittedEdge.getFirst().getFrom().getTemperature(),
                    splittedEdge.getLast().getTo().getTemperature());

            splittedEdge.removeFirst();
            Arrays.stream(temperatures).forEach((t) -> splittedEdge.removeFirst().getFrom().setTemperature(t));
        }

        DrawRod.drawRow(DrawRod.setLines(dataStorage), dataStorage);
    }
}
