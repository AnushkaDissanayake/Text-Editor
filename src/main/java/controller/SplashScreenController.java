package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class SplashScreenController {
    public Label lblLoading;
    public Rectangle recContainer;
    public Rectangle recProgressBar;

    public void initialize(){
        Timeline timeline = new Timeline();
        var keyFrame1 = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Initializing the UI..!");
                recProgressBar.setWidth(recContainer.getWidth()/10*1);
            }
        });
        var keyFrame2 = new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Initializing the UI....!");
                recProgressBar.setWidth(recContainer.getWidth()/10*2);
            }
        });
        var keyFrame3 = new KeyFrame(Duration.millis(900), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Initializing the UI......!");
                recProgressBar.setWidth(recContainer.getWidth()/10*3);
            }
        });
        var keyFrame4 = new KeyFrame(Duration.millis(1200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Loading Plugins..!");
                recProgressBar.setWidth(recContainer.getWidth()/10*4);
            }
        });
        var keyFrame5 = new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Loading Plugins....!");
                recProgressBar.setWidth(recContainer.getWidth()/10*5);
            }
        });
        var keyFrame6 = new KeyFrame(Duration.millis(1800), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Loading Plugins......!");
                recProgressBar.setWidth(recContainer.getWidth()/10*6);
            }
        });
        var keyFrame7 = new KeyFrame(Duration.millis(2100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Loading Plugins........!");
                recProgressBar.setWidth(recContainer.getWidth()/10*7);
            }
        });
        var keyFrame8 = new KeyFrame(Duration.millis(2400), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Setup the UI Logic..!");
                recProgressBar.setWidth(recContainer.getWidth()/10*8);
            }
        });
        var keyFrame9 = new KeyFrame(Duration.millis(2700), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Setup the UI Logic....!");
                recProgressBar.setWidth(recContainer.getWidth()/10*9);
            }
        });
        var keyFrame10 = new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblLoading.setText("Setup the UI Logic....!");
                recProgressBar.setWidth(recContainer.getWidth());
            }
        });
        var keyFrame11 = new KeyFrame(Duration.millis(700), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    URL resource = this.getClass().getResource("/view/TextEditorForm.fxml");
                    Parent container = FXMLLoader.load(resource);
                    Scene textEditorScene = new Scene(container);
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(textEditorScene );
                    primaryStage.setTitle("Text Editor");
                    primaryStage.show();
                    primaryStage.centerOnScreen();
                    lblLoading.getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        timeline.getKeyFrames().addAll(keyFrame1/*, keyFrame2, keyFrame3, keyFrame4,keyFrame5, keyFrame6, keyFrame7, keyFrame8,keyFrame9, keyFrame10*/, keyFrame11);
        timeline.playFromStart();

    }
}
