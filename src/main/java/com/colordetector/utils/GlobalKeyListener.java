package com.colordetector.utils;

import com.colordetector.controller.ColorDetectorController;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@RequiredArgsConstructor
public class GlobalKeyListener implements NativeKeyListener {
    private final ColorDetectorController controller;

    private boolean isRunning = true;

    public void nativeKeyPressed(NativeKeyEvent e) {

//        GlobalScreen.unregisterNativeHook();
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            isRunning = !isRunning;
            if (isRunning) startDetecting();
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_F1) {
            StringSelection selection = new StringSelection(controller.getHex().getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_F2) {
            String rgb = "RGB (" + controller.getRed().getText() + ", " + controller.getGreen().getText() + ", " + controller.getBlue().getText() + ")";
            StringSelection selection = new StringSelection(rgb);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }


    }

    public void startDetecting() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Robot robot = new Robot();
                ColorUtils colorUtils = new ColorUtils();
                while (isRunning) {
                    Point cord = MouseInfo.getPointerInfo().getLocation();
                    Color color = robot.getPixelColor((int) cord.getX(), (int) cord.getY());
                    String hex = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();

                    Platform.runLater(() -> {
                        controller.getX().setText(String.valueOf((int) cord.getX()));
                        controller.getY().setText(String.valueOf((int) cord.getY()));
                        controller.getRed().setText(String.valueOf(color.getRed()));
                        controller.getGreen().setText(String.valueOf(color.getGreen()));
                        controller.getBlue().setText(String.valueOf(color.getBlue()));
                        controller.getHex().setText(hex);
                        controller.getName().setText(colorUtils.getColorNameFromColor(color));
                        controller.getColor().setStyle("-fx-background-color: " + hex + "; " + "-fx-text-fill: #ffffff; -fx-border-color: #ffffff; -fx-border-style: solid; -fx-border-width: 0.5px; -fx-border-radius: 5px;");
                    });
                    robot.delay(10);

                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}