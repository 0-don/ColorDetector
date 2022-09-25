package com.colordetector;

import com.colordetector.controller.ColorDetectorController;
import com.colordetector.utils.ColorUtils;
import com.colordetector.utils.GlobalKeyListener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    public void start(Stage stage) throws IOException, NativeHookException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view.fxml"));
        Parent root = fxmlLoader.load();
        ColorDetectorController controller = fxmlLoader.getController();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Robot robot = new Robot();
                ColorUtils colorUtils = new ColorUtils();
                while (true) {
                    Point cord = MouseInfo.getPointerInfo().getLocation();
                    Color color = robot.getPixelColor((int) cord.getX(), (int) cord.getY());
                    String hex = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();

                    Platform.runLater(() -> {
                        controller.getX().setText(String.valueOf((int) cord.getX()));
                        controller.getY().setText(String.valueOf((int) cord.getY()));
                        controller.getRed().setText(String.valueOf(color.getRed()));
                        controller.getBlue().setText(String.valueOf(color.getBlue()));
                        controller.getGreen().setText(String.valueOf(color.getGreen()));
                        controller.getHex().setText(hex);
                        controller.getName().setText(colorUtils.getColorNameFromColor(color));
                        controller.getColor().setStyle("-fx-background-color: " + hex + "; " + "-fx-text-fill: #ffffff; -fx-border-color: #ffffff; -fx-border-style: solid; -fx-border-width: 0.5px; -fx-border-radius: 5px;");
                    });
                    robot.delay(10);
                }
            }
        };
        new Thread(task).start();


        Scene scene = new Scene(root);
        stage.setTitle("Color Detector");
        stage.setScene(scene);
        stage.show();


        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }


}
