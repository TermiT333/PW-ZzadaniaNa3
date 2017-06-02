package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

/**
 * Created by Michal on 2017-06-01.
 */
public class HTMLEditSample extends Application {
    @Override
    public void start(Stage stage){
        stage.setTitle("HTMLEdit Sample");
        stage.setWidth(500);
        stage.setHeight(400);
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        Scene sc = new Scene(htmlEditor);
        stage.setScene(sc);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

