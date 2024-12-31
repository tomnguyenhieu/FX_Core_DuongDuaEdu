package com.edu.duongdua.core.view;

import com.edu.duongdua.core.controller.SideBarController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Scene_SideBar {
    private static final double SIDEBAR_WIDTH = 280;
    private static final double SIDEBAR_HEIGHT = 720;
    private static final String SIDEBAR_BG_COLOR = "#30475E";

    public AnchorPane createSideBar(SideBarController controller) {
        AnchorPane anchorPane = new AnchorPane();
        VBox sideBar = new VBox();
        sideBar.setPrefSize(SIDEBAR_WIDTH, SIDEBAR_HEIGHT);
        sideBar.setStyle(" -fx-background-color: " + SIDEBAR_BG_COLOR + ";");

        HBox logoSection = createLogoSection();
        ScrollPane menuScrollPane = createMenu(controller);
        HBox userSection = createUserSection();

        sideBar.getChildren().addAll(logoSection, menuScrollPane, userSection);
        anchorPane.getChildren().add(sideBar);

        return anchorPane;
    }

    private HBox createLogoSection() {
        HBox hBox = new HBox(16);
        hBox.setPrefSize(SIDEBAR_WIDTH, 120);
        hBox.setPadding(new Insets(16, 0, 0, 16));

//        ImageView imageView = new ImageView(new Image(getClass().getResource("/GermLogo_resizee.png").toExternalForm()));
        ImageView imageView = new ImageView();
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        Label label = new Label("Germ \nEducation");
        label.setPrefSize(140, 75);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 28));

        hBox.getChildren().addAll(imageView, label);
        return hBox;
    }

    private ScrollPane createMenu(SideBarController controller) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(521);
        scrollPane.setPrefWidth(280);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: " + SIDEBAR_BG_COLOR + "; -fx-background-color: transparent;");

        VBox menuContainer = new VBox(20);
        menuContainer.setPrefWidth(SIDEBAR_WIDTH - 11);
        menuContainer.setPadding(new Insets(16, 16, 0, 16));
        menuContainer.setId("sbContainer");

        TitledPane statisticsPane = createTitledPane("Thống kê", controller, "tiledPaneContainer");
        menuContainer.getChildren().addAll(
                statisticsPane,
                createMenuItem("Lớp học", "classScene", controller),
                createMenuItem("Giáo viên", "teacherScene", controller),
                createMenuItem("Nhân viên", "employeeScene", controller),
                createMenuItem("Học viên", "studentScene", controller),
                createMenuItem("Lịch học & Phòng học", "scheduleScene", controller)
        );

        scrollPane.setContent(menuContainer);
        return scrollPane;
    }

    private TitledPane createTitledPane(String title, SideBarController controller, String containerId) {
        TitledPane titledPane = new TitledPane();
        titledPane.setAlignment(Pos.CENTER);
        titledPane.setExpanded(false);
        titledPane.setText(title);
        titledPane.setStyle("-fx-font-size: 24px;");
        titledPane.setCursor(Cursor.HAND);

        VBox content = new VBox(12);
        content.setStyle("-fx-background-color: " + SIDEBAR_BG_COLOR + ";");
//        content.setId("tiledPaneContainer");

        content.getChildren().addAll(
                createSubMenuItem("Giáo viên & Nhân viên", "scene1", controller),
                createSubMenuItem("Học viên", "scene2", controller),
                createSubMenuItem("Khoản thu", "scene3", controller),
                createSubMenuItem("Khoản chi", "scene4", controller)
        );

        titledPane.setContent(content);
        return titledPane;
    }

    private HBox createMenuItem(String text, String id, SideBarController controller) {
        HBox menuItem = new HBox();
        menuItem.setPrefSize(180, 55);
        menuItem.setCursor(Cursor.HAND);
        menuItem.setStyle("-fx-background-radius: 5;");
        menuItem.setId(id);
        if (controller != null) menuItem.setOnMouseClicked(controller);

        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Roboto", FontWeight.NORMAL, 22));
        menuItem.getChildren().add(label);

        return menuItem;
    }

    private HBox createSubMenuItem(String text, String id, SideBarController controller) {
        return createMenuItem(text, id, controller);
    }

    private HBox createUserSection() {
        HBox hBox = new HBox(16);
        hBox.setLayoutX(10);
        hBox.setLayoutY(10);
        hBox.setPrefSize(SIDEBAR_WIDTH, 86);
        hBox.setPadding(new Insets(16, 0, 0, 16));

//        ImageView userImage = new ImageView(new Image(getClass().getResource("/User.jpg").toExternalForm()));
        ImageView userImage = new ImageView();
        userImage.setFitWidth(56);
        userImage.setFitHeight(56);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);

        Label label = new Label("ADMIN");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Roboto", FontWeight.BOLD, 24));

        hBox.getChildren().addAll(userImage, label);
        return hBox;
    }
}

