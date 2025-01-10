package com.edu.duongdua.core.view;

import com.edu.duongdua.core.controller.ManageScheduleController;
import com.edu.duongdua.core.model.Schedule;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;

public class Scene_ManageSchedule {

    private AnchorPane anchorPane;
    private VBox vbox;
    private HBox hbox;
    private Button addScheduleBtn;
    private Button deleteScheduleBtn;
    private ScrollPane scrollPane;
    private TilePane tilePane;
    public ComboBox<String> cbDay = new ComboBox<>();
    public ComboBox<String> cbClassName = new ComboBox<>();
    public ComboBox<String> comboBoxDay = new ComboBox<>();
    public ComboBox<String> comboBoxClassName = new ComboBox<>();
    public TextField inputClassRoom = new TextField();
    public TextField inputTime = new TextField();
    public Label lblRoom= new Label ();
    public Label lblTime = new Label();
    public Stage modalStage = new Stage();
    public Label headerLabel = new Label();

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public TilePane getTilePane() {
        return tilePane;
    }

    public void setTilePane(TilePane tilePane) {
        this.tilePane = tilePane;
    }

    public void createManageSchedule(List<Schedule> scheduleList, EventHandler<ActionEvent> eventHandler) {
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1000, 720);
        anchorPane.setStyle("-fx-background-color: #FFFFFF;");

        vbox = new VBox();
        vbox.setPrefSize(1000, 720);

        hbox = new HBox();
        hbox.setPrefSize(1000, 120);
        hbox.setPadding(new Insets(0, 16, 16, 16));
        hbox.setSpacing(16);
        hbox.setAlignment(Pos.BOTTOM_CENTER);

        Label label = new Label("QUẢN LÝ LỊCH HỌC VÀ PHÒNG HỌC");
        label.setPrefSize(850, 84);
        label.setStyle("-fx-background-color:  #F5F5F5; -fx-text-fill: #30475e;");
        label.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 37));
        label.setTextFill(Color.BLACK);
        label.setAlignment(Pos.CENTER);

        addScheduleBtn = new Button("Thêm");
        addScheduleBtn.setPrefSize(278, 35);
        addScheduleBtn.setStyle("-fx-background-color:  #30475E");
        addScheduleBtn.setFont(Font.font("Roboto", 20));
        addScheduleBtn.setTextFill(Color.WHITE);
        addScheduleBtn.setAlignment(Pos.CENTER);
        addScheduleBtn.setCursor(Cursor.HAND);
        addScheduleBtn.setOnAction(event -> {
            cbDay.getItems().clear();
            createAddModalStage(eventHandler);
            modalStage.show();
        });

        deleteScheduleBtn = new Button("Xóa");
        deleteScheduleBtn.setPrefSize(278, 35);
        deleteScheduleBtn.setStyle("-fx-background-color:  #30475E");
        deleteScheduleBtn.setFont(Font.font("Roboto", 20));
        deleteScheduleBtn.setTextFill(Color.WHITE);
        deleteScheduleBtn.setAlignment(Pos.CENTER);
        deleteScheduleBtn.setCursor(Cursor.HAND);
        deleteScheduleBtn.setOnAction(event -> {
            comboBoxDay.getItems().clear();
            createDeleteModalStage(eventHandler);
            modalStage.show();
        });

        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(200, 600);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setMargin(scrollPane, new Insets(0, 16, 16, 16));

        tilePane = new TilePane();
        tilePane.setPrefWidth(980);
        tilePane.setAlignment(Pos.TOP_LEFT);
        tilePane.setHgap(50);
        tilePane.setVgap(20);
        tilePane.setPadding(new Insets(50, 12, 12, 50));

        anchorPane.getChildren().addAll(vbox);
        vbox.getChildren().addAll(hbox, scrollPane);
        hbox.getChildren().addAll(label, addScheduleBtn, deleteScheduleBtn);
        scrollPane.setContent(tilePane);
    }


    public void createComboBox (int day, Set<String> className, EventHandler<ActionEvent> eventHandler) {
        ComboBox<String> classNameComboBox = new ComboBox<>();
        classNameComboBox.setId("classNameCB");
        classNameComboBox.getItems().addAll(className);

        classNameComboBox.addEventHandler(ActionEvent.ACTION, eventHandler);

        VBox vb = initScheduleBox(String.valueOf(day), classNameComboBox);
        tilePane.getChildren().add(vb);
    }

    public List<String> getAvailableDays() {
        List<String> allDays = new ArrayList<>();
        for (int i = 2; i <= 7; i++) {
            allDays.add("Thứ " + String.valueOf(i));
        }
        return allDays;
    }

    public void refreshData (Set<String> availableClasses) {
        cbClassName.getItems().clear();
        cbClassName.getItems().addAll(availableClasses);
    }

    public void loadData (Set<String> classNames) {
        comboBoxClassName.getItems().clear();
        comboBoxClassName.getItems().addAll(classNames);
    }

    public void createAddModalStage(EventHandler<ActionEvent> eventHandler) {
        VBox vBox = new VBox();
        vBox.setPrefHeight(500.0);
        vBox.setPrefWidth(474.0);
        vBox.setSpacing(20.0);

        headerLabel.setText("Vui lòng nhập thông tin");
        headerLabel.setPrefHeight(80.0);
        headerLabel.setPrefWidth(474.0);
        headerLabel.setStyle("-fx-background-color: #CE4848;");
        headerLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        headerLabel.setFont(new Font("System Bold", 36.0));
        headerLabel.setAlignment(javafx.geometry.Pos.CENTER);

        VBox inputVBox = new VBox();
        inputVBox.setPrefHeight(305.0);
        inputVBox.setPrefWidth(474.0);
        inputVBox.setSpacing(10.0);

        cbDay.setId("dayCB");
        cbDay.setPrefHeight(65.0);
        cbDay.setPrefWidth(434.0);
        cbDay.setPromptText("Nhập thứ");
        cbDay.setStyle("-fx-background-radius: 4;");
        VBox.setMargin(cbDay, new Insets(0, 20, 0, 20));

        List<String> days = getAvailableDays();
        cbDay.getItems().addAll(days);

        cbClassName.setPrefHeight(65.0);
        cbClassName.setPrefWidth(434.0);
        cbClassName.setPromptText("Nhập tên lớp ");
        cbClassName.setStyle("-fx-background-radius: 4;");
        VBox.setMargin(cbClassName, new Insets(0, 20, 0, 20));

        cbDay.addEventHandler(ActionEvent.ACTION, eventHandler);

        inputClassRoom.setPrefHeight(65.0);
        inputClassRoom.setPrefWidth(474.0);
        inputClassRoom.setPromptText("Nhập phòng học");
        inputClassRoom.setStyle("-fx-background-radius: 4;");
        inputClassRoom.setFont(new Font(18.0));
        VBox.setMargin(inputClassRoom, new Insets(0, 20, 0, 20));

        inputTime.setPrefHeight(65.0);
        inputTime.setPrefWidth(474.0);
        inputTime.setPromptText("Nhập giờ học ");
        inputTime.setStyle("-fx-background-radius: 4;");
        inputTime.setFont(new Font(18.0));
        VBox.setMargin(inputTime, new Insets(0, 20, 0, 20));

        inputVBox.getChildren().addAll(cbDay, cbClassName, inputClassRoom, inputTime);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(javafx.geometry.Pos.CENTER);
        buttonHBox.setPrefHeight(54.0);
        buttonHBox.setPrefWidth(474.0);
        buttonHBox.setSpacing(15.0);

        Button btnConfirm = new Button("Xác nhận");
        btnConfirm.setId("confirmAddBtn");
        btnConfirm.setPrefHeight(47.0);
        btnConfirm.setPrefWidth(128.0);
        btnConfirm.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        btnConfirm.setTextFill(javafx.scene.paint.Color.WHITE);
        btnConfirm.setFont(new Font(20.0));
        btnConfirm.setCursor(Cursor.HAND);

        btnConfirm.addEventHandler(ActionEvent.ACTION, eventHandler);

        Button btnCancel = new Button("Hủy");
        btnCancel.setPrefHeight(47.0);
        btnCancel.setPrefWidth(128.0);
        btnCancel.setStyle("-fx-background-color: #CE4848; -fx-background-radius: 10px;");
        btnCancel.setTextFill(javafx.scene.paint.Color.WHITE);
        btnCancel.setFont(new Font(20.0));
        btnCancel.setCursor(Cursor.HAND);
        btnCancel.setOnAction(event -> {
            modalStage.close();
        });

        buttonHBox.getChildren().addAll(btnCancel, btnConfirm);

        vBox.getChildren().addAll(headerLabel, inputVBox, buttonHBox);

        modalStage.setScene(new javafx.scene.Scene(vBox));
        modalStage.setTitle("Thêm lịch học");
    }

    public void createDeleteModalStage(EventHandler<ActionEvent> eventHandler) {
        headerLabel.setText("Vui lòng nhập thông tin");
        headerLabel.setStyle("-fx-background-color: #CE4848;");
        headerLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        headerLabel.setFont(new Font("System Bold", 36));
        headerLabel.setPrefHeight(80);
        headerLabel.setPrefWidth(474);
        headerLabel.setAlignment(javafx.geometry.Pos.CENTER);

        comboBoxDay.setId("dayComboBox");
        comboBoxDay.setPrefHeight(40);
        comboBoxDay.setPrefWidth(150);
        comboBoxDay.setPromptText("Ngày học");
        comboBoxDay.setStyle("-fx-background-radius: 4;");
        comboBoxDay.setPadding(new Insets(0, 20, 0, 20));

        List<String> days = getAvailableDays();
        comboBoxDay.getItems().addAll(days);

        comboBoxClassName.setId("classNameComboBox");
        comboBoxClassName.setPrefHeight(40);
        comboBoxClassName.setPrefWidth(150);
        comboBoxClassName.setPromptText("Tên lớp");
        comboBoxClassName.setStyle("-fx-background-radius: 4;");
        comboBoxClassName.setPadding(new Insets(0, 20, 0, 20));

        comboBoxDay.addEventHandler(ActionEvent.ACTION, eventHandler);

        lblRoom.setText("Phòng học:");
        lblTime.setText("Giờ học:");

        VBox vBox1 = new VBox(10, comboBoxDay, comboBoxClassName);
        VBox vBox2 = new VBox(10, lblRoom, lblTime);

        comboBoxClassName.addEventHandler(ActionEvent.ACTION, eventHandler);

        HBox hBox = new HBox(20, vBox1, vBox2);
        hBox.setPrefHeight(100);
        hBox.setPrefWidth(200);

        Button btnCancel = new Button("Hủy");
        btnCancel.setPrefHeight(47);
        btnCancel.setPrefWidth(128);
        btnCancel.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        btnCancel.setTextFill(javafx.scene.paint.Color.WHITE);
        btnCancel.setFont(new Font(20));
        btnCancel.setCursor(Cursor.HAND);
        btnCancel.setOnAction(event -> {
            modalStage.close();
        });

        Button btnConfirm = new Button("Xác nhận");
        btnConfirm.setId("confirmDeleteBtn");
        btnConfirm.setPrefHeight(47);
        btnConfirm.setPrefWidth(128);
        btnConfirm.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        btnConfirm.setTextFill(javafx.scene.paint.Color.WHITE);
        btnConfirm.setFont(new Font(20));
        btnConfirm.setCursor(Cursor.HAND);

        btnConfirm.addEventHandler(ActionEvent.ACTION, eventHandler);

        HBox hBoxButtons = new HBox(15, btnCancel, btnConfirm);
        hBoxButtons.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        hBoxButtons.setPrefHeight(54);
        hBoxButtons.setPrefWidth(474);
        hBoxButtons.setPadding(new Insets(0, 20, 0, 20));

        VBox vBoxMain = new VBox(20, headerLabel, hBox, hBoxButtons);
        vBoxMain.setPrefHeight(300);
        vBoxMain.setPrefWidth(474);
        vBoxMain.setPadding(new Insets(20, 0, 0, 0));

        modalStage.setScene(new javafx.scene.Scene(vBoxMain));
        modalStage.setTitle("Xóa lịch học");
    }

    public void showAlert(String message, boolean closeWindow) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK && closeWindow) {
            Scene scene = headerLabel.getScene();
            if (scene != null) {
                Stage stage = (Stage) scene.getWindow();
                stage.close();
            }
        }
    }

    public VBox initScheduleBox(String day, ComboBox classNameComboBox) {
        VBox vBox = new VBox();
        vBox.setMinSize(240, 200);
        vBox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 8");

        Label label = new Label("Thứ " + day);
        label.setMinSize(241, 46);
        label.setFont(Font.font("Roboto Bold", 24));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        label.setCursor(Cursor.HAND);
        label.setStyle("-fx-font-weight: bold; -fx-background-color:  #F05454; -fx-background-radius: 8 8 0 0;");

        VBox content = new VBox(8);
        content.setMinSize(200, 100);
        content.setPadding(new Insets(10));

        HBox classSelection = new HBox(5);
        classNameComboBox.setPrefWidth(220);
        classNameComboBox.setPromptText("Chọn lớp");

        classSelection.getChildren().addAll(classNameComboBox);

        VBox details = new VBox(5);
        details.setMinSize(200, 100);
        details.setPadding(new Insets(12, 0, 0, 12));
        details.setStyle("-fx-background-color:  #ffffff; -fx-background-radius: 6");
        HBox.setMargin(details, new Insets(10, 0, 10, 10));
        Label roomLabel = new Label("Phòng học: ");
        roomLabel.setFont(Font.font("Roboto Bold", 16));
        Label timeLabel = new Label("Giờ học: ");
        timeLabel.setFont(Font.font("Roboto Bold", 16));

        details.getChildren().addAll(roomLabel, timeLabel);
        content.getChildren().addAll(classSelection, details);
        vBox.getChildren().addAll(label, content);
        return vBox;
    }

    public void updateClassDetails(String classRoom, String time, int day) {
        for (Node node : tilePane.getChildren()) {
            if (node instanceof VBox) {
                VBox vBox = (VBox) node;
                Label dayLabel = (Label) vBox.getChildren().get(0);
                VBox content = (VBox) vBox.getChildren().get(1);

                if (dayLabel.getText().contains("Thứ " + day)) {
                    VBox details = (VBox) content.getChildren().get(1);
                    Label roomLabel = (Label) details.getChildren().get(0);
                    Label timeLabel = (Label) details.getChildren().get(1);

                    roomLabel.setText("Phòng học: " + classRoom);
                    timeLabel.setText("Giờ học: " + time);
                    break;
                }
            }
        }
    }

    public void resetClassDetails() {
        for (Node node : tilePane.getChildren()) {
            if (node instanceof VBox) {
                VBox vBox = (VBox) node;
                VBox content = (VBox) vBox.getChildren().get(1);
                VBox details = (VBox) content.getChildren().get(1);

                Label roomLabel = (Label) details.getChildren().get(0);
                Label timeLabel = (Label) details.getChildren().get(1);

                roomLabel.setText("Phòng học: ");
                timeLabel.setText("Giờ học: ");
            }
        }
    }

    public boolean isValidTimeFormat(String time) {
        String timePattern = "([01]?\\d|2[0-3])h[0-5]\\d-([01]?\\d|2[0-3])h[0-5]\\d";
        return time.matches(timePattern);
    }
}