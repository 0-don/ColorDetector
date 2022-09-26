package com.colordetector.controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.Getter;


@Getter
public class ColorDetectorController {
    @FXML
    private TextField x;

    @FXML
    private TextField y;

    @FXML
    private TextField red;

    @FXML
    private TextField green;

    @FXML
    private TextField blue;

    @FXML
    private TextField name;

    @FXML
    private TextField hex;

    @FXML
    private Pane color;

    @FXML
    protected void onCloseButtonClick() throws NativeHookException {
        GlobalScreen.unregisterNativeHook();
        Platform.exit();
        System.exit(0);
    }
}

