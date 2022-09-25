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

import java.io.*;

import static javax.swing.filechooser.FileSystemView.getFileSystemView;

public class BaseApplication extends Application {

    public static void main(String[] args) {

        try {
            File outputFile = File.createTempFile("debug", ".log", getFileSystemView().getDefaultDirectory());
            PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)), true);
            System.setOut(output);
            System.setErr(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);

    }

    @Override
    public void start(Stage stage) throws IOException, NativeHookException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view.fxml"));
        Parent root = fxmlLoader.load();
        ColorDetectorController controller = fxmlLoader.getController();


        Scene scene = new Scene(root);
        stage.setTitle("Color Detector");
        stage.setScene(scene);
        stage.show();


        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(controller));
    }


}
