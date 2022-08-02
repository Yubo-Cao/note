package com.yubo.dog_cat;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class DogCatMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dog_cat_view.fxml")));
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("base.css")).toExternalForm());
        stage.setTitle("DogCat");
        stage.setScene(scene);
        stage.show();
    }
}
