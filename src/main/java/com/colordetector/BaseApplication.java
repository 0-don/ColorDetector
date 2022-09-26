package com.colordetector;

import com.colordetector.controller.ColorDetectorController;
import com.colordetector.utils.GlobalKeyListener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class BaseApplication extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

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

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("view.css")).toExternalForm());
        stage.setTitle("Color Detector");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();


        GlobalScreen.registerNativeHook();
        GlobalKeyListener globalKeyListener = new GlobalKeyListener(controller);
        GlobalScreen.addNativeKeyListener(globalKeyListener);
        globalKeyListener.startDetecting();
    }


}
