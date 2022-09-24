package com.changenode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class BaseApplication extends Application {

    public static File outputFile;


    public static void main(String[] args) {
        /*
         * Route the debugging output for this application to a log file in your "default" directory.
         * */
//        try {
//            outputFile = File.createTempFile("debug", ".log", getFileSystemView().getDefaultDirectory());
//            PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)), true);
//            System.setOut(output);
//            System.setErr(output);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
