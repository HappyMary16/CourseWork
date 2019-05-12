package ua.com.courseWork.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import ua.com.courseWork.database.DataStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewController {

    private final FileChooser fileChooser;

    DataStorage dataStorage = new DataStorage();

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
        chooseFileNode.setOnAction(e -> fileNodeButtonListener(fileNodePath));
        chooseFileTemperature.setOnAction(e -> fileNodeButtonListener(fileTemperaturePath));
        chooseFileEdge.setOnAction(e -> fileNodeButtonListener(fileEdgePath));

        // file:///
        try {
            resultImage.setImage(new Image(new FileInputStream("image.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //resultImage.setVisible(true);
        resultImage.setPreserveRatio(true);
    }

    private void fileNodeButtonListener(TextField textPath) {
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            textPath.setText(selectedFile.getAbsolutePath());
        }
    }
}
