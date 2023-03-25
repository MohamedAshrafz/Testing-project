package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    FileChooser fileChooser;
    String xml, xmlOut;
    File input, output;

    @FXML
    TextArea originalTA = new TextArea();
    @FXML
    TextArea resultTA = new TextArea();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File("src\\sample"));

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilter2);
    }

    public void onLoadFile() {

        input = fileChooser.showOpenDialog(new Stage());

        if (input != null) {
            InputStream orgInStream = System.in;
            try {
                System.setIn(new FileInputStream(input));
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }
            xml = CustomStdIn.readString();

            originalTA.setText(xml);
            CustomStdIn.close();
            System.setIn(orgInStream);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("You must provide a file");
            alert.setContentText("You didn't provide a file");
            alert.showAndWait();
        }
    }

    public void onSaveFile() {
        xml = originalTA.getText();
        output = fileChooser.showSaveDialog(new Stage());

        if (output != null) {
            PrintStream orgOutStream = System.out;
            try {
                System.setOut(new PrintStream(output));
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }
            CustomStdOut.write(xmlOut);

            CustomStdOut.close();
            System.setOut(orgOutStream);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("You must provide a path");
            alert.setContentText("You didn't provide a path to save the file");
            alert.showAndWait();
        }
    }

    public void onCalculateGPA() {
        xml = originalTA.getText();
        if (checkIfEmpty(xml))
            return;

        // ArrayList<String> linesInArrList = new ArrayList<>(Arrays.asList(xml.split("[\n|,]+")));

        // Every line of the input in a string in the linesInArrList
        // can be used to parse it into first line of the course info
        // other n lines (after the second one) for the students in this course
        ArrayList<String> linesInArrList = new ArrayList<>(Arrays.asList(xml.split("[\n|\r]+")));


        // used to collect the output string to the gui
        StringBuilder sb = new StringBuilder();
        for (String element: linesInArrList){
            sb.append(element).append("\n\n");
        }

        // show your method result in xmlOut
        xmlOut = sb.toString();
        resultTA.setText(xmlOut);
    }

    private static boolean checkIfEmpty(String xml) {
        if (xml.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No XML");
            alert.setHeaderText("You must provide an XML text or file");
            alert.setContentText("Provide an XML");
            alert.showAndWait();
            return true;
        }
        return false;
    }
}
