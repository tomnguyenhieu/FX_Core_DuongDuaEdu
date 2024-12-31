package com.edu.duongdua.core;

import com.edu.duongdua.core.controller.SideBarController;
import com.edu.duongdua.core.view.Scene_SideBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("SideBar");

        BorderPane root = new BorderPane();
        VBox menuContainer = new VBox();
        VBox content = new VBox();
        root.setPrefSize(1280, 720);

        SideBarController sidebarController = new SideBarController(root, menuContainer, content);
        Scene_SideBar sideBar = new Scene_SideBar();

        AnchorPane anchorPane = sideBar.createSideBar(sidebarController);
        root.setLeft(anchorPane);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
