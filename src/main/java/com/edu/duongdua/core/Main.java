package com.edu.duongdua.core;

import com.edu.duongdua.core.view.Scene_SideBar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Scene_SideBar sceneSideBar = new Scene_SideBar(root);
        root.setLeft(sceneSideBar.createSideBar());

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Germ Education");
        primaryStage.show();
    }
}