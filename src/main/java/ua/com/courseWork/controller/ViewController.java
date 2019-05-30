package ua.com.courseWork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import lombok.val;
import ua.com.courseWork.database.DataStorage;
import ua.com.courseWork.model.Edge;
import ua.com.courseWork.model.SplitType;
import ua.com.courseWork.view.DrawRod;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewController {

    private final FileChooser fileChooser;

    private static DataStorage dataStorage = new DataStorage();

    @FXML
    BorderPane mainPane;

    @FXML
    private Button chooseFileNode;

    @FXML
    private TextField fileNodePath;

    @FXML
    private Button chooseFileTemperature;

    @FXML
    private TextField fileTemperaturePath;
    @FXML
    private Button chooseFileEdge;

    @FXML
    private TextField fileEdgePath;

    @FXML
    private ImageView resultImage;

    @FXML
    private TextField temperatureOnCursor;

    @FXML
    private ComboBox<String> splitType;

    @FXML
    private Button calculate;

    @FXML
    private TableView<Pair<String, String>> colorTable;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ViewController() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt"));

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        initChooseTypeDivision();
        chooseFileNode.setOnAction(e -> fileNodeButtonListener(fileNodePath));
        chooseFileTemperature.setOnAction(e -> fileNodeButtonListener(fileTemperaturePath));
        chooseFileEdge.setOnAction(e -> fileNodeButtonListener(fileEdgePath));
        calculate.setOnAction(e -> calculateButtonListener());
//        try {
//            resultImage.setImage(new Image(new FileInputStream("image.png")));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void fileNodeButtonListener(TextField textPath) {
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            textPath.setText(selectedFile.getAbsolutePath());
        }
    }

    private void initChooseTypeDivision() {
        ObservableList<String> splitType =
                FXCollections.observableArrayList(
                        SplitType.LOW.toString(),
                        SplitType.MEDIUM.toString(),
                        SplitType.HIGHT.toString(),
                        SplitType.HUGE.toString());
        this.splitType.setValue(SplitType.MEDIUM.toString());
        this.splitType.setItems(splitType);
    }

    /**
     * Set colors and temperatures to table.
     */
    private void setValuesToTable() {
        val columns = colorTable.getColumns();

        columns.get(0).setCellValueFactory(new PropertyValueFactory<>("key"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<>("value"));

        ObservableList<Pair<String, String>> list = getDataForTable();
        colorTable.setItems(list);
    }

    private ObservableList<Pair<String, String>> getDataForTable() {
        double minTemp = dataStorage.getMinTemperature();
        double diffTemp = (dataStorage.getMaxTemperature() - minTemp) / 10;
        int i = 0;
        NumberToColor numberToColor = new NumberToColor(minTemp, dataStorage.getMaxTemperature());
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line1 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line2 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line3 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line4 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line5 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line6 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line7 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line8 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(minTemp + i * diffTemp));
        Pair<String, String> line9 = new Pair<>("", String.valueOf(Math.round((minTemp + i++ * diffTemp) * 100) / 100.));
        System.out.println(numberToColor.getColor(dataStorage.getMaxTemperature()));
        Pair<String, String> line10 = new Pair<>("", String.valueOf(dataStorage.getMaxTemperature()));


        return FXCollections.observableArrayList(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10);
    }

    private void calculateButtonListener() {
        try {
            dataStorage.setNodeMap(fileNodePath.getText());
            dataStorage.setEdgeDataMap(fileEdgePath.getText());
            dataStorage.setTemperature(fileTemperaturePath.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberEdges;
        List<Edge> edges = dataStorage.getEdges();

        for (Edge edge:
                edges) {
            numberEdges = dataStorage.numberEdges();

            edge.split(SplitType.HIGHT, dataStorage);

            LinkedList<Edge> splitEdge = (LinkedList<Edge>) dataStorage.getEdgesFrom(numberEdges - 1);

            splitEdge.addFirst(splitEdge.removeLast());
            splitEdge.addFirst(splitEdge.removeLast());
            splitEdge.addFirst(splitEdge.remove(4));

            double[] temperatures = MSE.calculateResult(splitEdge.stream().mapToDouble(Edge::getLength).boxed().collect(Collectors.toList()),
                    splitEdge.getFirst().getFrom().getTemperature(),
                    splitEdge.getLast().getTo().getTemperature());

            splitEdge.removeFirst();
            Arrays.stream(temperatures).forEach((t) -> splitEdge.removeFirst().getFrom().setTemperature(t));
        }

        setValuesToTable();

        DrawRod.drawRow(DrawRod.setLines(dataStorage), dataStorage);

        //next not correct
        //set image
//        try {
//            resultImage.setImage(new Image(new FileInputStream("image.png")));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        resultImage.setPreserveRatio(true);


        new Thread(() -> {
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
                    temperatureOnCursor.setText(temperature);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
