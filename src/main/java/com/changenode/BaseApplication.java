package com.changenode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class BaseApplication extends Application {

    public static void main(String[] args) {

//        try {
//            File outputFile = File.createTempFile("debug", ".log", getFileSystemView().getDefaultDirectory());
//            PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)), true);
//            System.setOut(output);
//            System.setErr(output);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        launch(args);

    }

    @Override
    public void start(Stage stage) throws IOException, AWTException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

//        Robot robot = new Robot();
//
//        while (true) {
//            Point cord = MouseInfo.getPointerInfo().getLocation();
//            Color color = robot.getPixelColor((int) cord.getX(), (int) cord.getY());
//            System.out.println("X: " + cord.getX() + " Y: " + cord.getY() + " Color: " + color);
//            robot.delay(1000);
//        }
    }
}
