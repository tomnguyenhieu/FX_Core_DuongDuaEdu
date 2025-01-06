package com.edu.duongdua.core.view;

import com.edu.duongdua.core.controller.ManageScheduleController;
import com.edu.duongdua.core.model.Schedule;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Scene_ManageSchedule {

    private AnchorPane anchorPane;
    private VBox vbox;
    private HBox hbox;
    private Button addScheduleBtn;
    private Button deleteScheduleBtn;
    private ScrollPane scrollPane;
    private TilePane tilePane;
    private ComboBox<String> cbDay = new ComboBox<>();
    private ComboBox<String> cbClassName = new ComboBox<>();
    private ComboBox<String> comboBoxDay = new ComboBox<>();
    private ComboBox<String> comboBoxClassName = new ComboBox<>();

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public Stage modalStage = new Stage();

    public TilePane getTilePane() {
        return tilePane;
    }

    public void setTilePane(TilePane tilePane) {
        this.tilePane = tilePane;
    }

    private ManageScheduleController manageScheduleController;

    public Scene_ManageSchedule(ManageScheduleController controller) {
        this.manageScheduleController = controller;
    }

    public void createManageSchedule(List<Schedule> scheduleList) {
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
            createAddModalStage();
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
            createDeleteModalStage();
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

        for (int i = 2; i <= 7; i++) {
            ComboBox<String> classNameComboBox = new ComboBox<>();
            Set<String> classNames = manageScheduleController.getClassesByDay(i);
            classNameComboBox.getItems().addAll(classNames);

            int day = i;
            classNameComboBox.setOnAction(event -> handleClassSelection(classNameComboBox, day));

            VBox vb = initScheduleBox(String.valueOf(day), classNameComboBox);
            tilePane.getChildren().add(vb);
        }

        anchorPane.getChildren().addAll(vbox);
        vbox.getChildren().addAll(hbox, scrollPane);
        hbox.getChildren().addAll(label, addScheduleBtn, deleteScheduleBtn);
        scrollPane.setContent(tilePane);
    }

    public void createAddModalStage() {
        // Create main VBox
        VBox vBox = new VBox();
        vBox.setPrefHeight(500.0);
        vBox.setPrefWidth(474.0);
        vBox.setSpacing(20.0);

        Label headerLabel = new Label("Vui lòng nhập thông tin");
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

        cbDay.setPrefHeight(65.0);
        cbDay.setPrefWidth(434.0);
        cbDay.setPromptText("Nhập thứ");
        cbDay.setStyle("-fx-background-radius: 4;");
        VBox.setMargin(cbDay, new Insets(0, 20, 0, 20));

        Set<String> days = manageScheduleController.getAvailableDays();
        cbDay.getItems().addAll(days);

        ComboBox<String> cbClassName = new ComboBox<>();
        cbClassName.setPrefHeight(65.0);
        cbClassName.setPrefWidth(434.0);
        cbClassName.setPromptText("Nhập tên lớp ");
        cbClassName.setStyle("-fx-background-radius: 4;");
        VBox.setMargin(cbClassName, new Insets(0, 20, 0, 20));

        cbDay.setOnAction(event -> {
            String selectedDay = cbDay.getValue();
            if (selectedDay != null) {
                Set<String> availableClasses = manageScheduleController.getClassName(selectedDay);
                cbClassName.getItems().clear();
                cbClassName.getItems().addAll(availableClasses);
            }
        });

        TextField inputClassRoom = new TextField();
        inputClassRoom.setPrefHeight(65.0);
        inputClassRoom.setPrefWidth(474.0);
        inputClassRoom.setPromptText("Nhập phòng học");
        inputClassRoom.setStyle("-fx-background-radius: 4;");
        inputClassRoom.setFont(new Font(18.0));
        VBox.setMargin(inputClassRoom, new Insets(0, 20, 0, 20));

        TextField inputTime = new TextField();
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
        btnConfirm.setPrefHeight(47.0);
        btnConfirm.setPrefWidth(128.0);
        btnConfirm.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        btnConfirm.setTextFill(javafx.scene.paint.Color.WHITE);
        btnConfirm.setFont(new Font(20.0));
        btnConfirm.setCursor(Cursor.HAND);
        btnConfirm.setOnAction(event -> {
            String day = cbDay.getValue();
            String className = cbClassName.getValue();
            String classRoom = inputClassRoom.getText();
            String time = inputTime.getText();

            if (day == null || className == null || classRoom == null || time == null) {
                showAlert("Kiểm tra lại thông tin!", false);
            } else {
                if (!isValidTimeFormat(time)){
                    showAlert("Thời gian không hợp lệ! Vui lòng nhập theo định dạng: 14h00-17h00.", false);
                } else {
                    boolean success = manageScheduleController.addSchedule(day, className, classRoom, time);
                    if (success) {
                        showAlert( "Thêm lịch học thành công!", true);
                        modalStage.close();
                    } else {
                        showAlert("Thêm lịch học thất bại!", false);
                    }
                }
            }
        });

        Button btnCancel = new Button("Hủy");
        btnCancel.setPrefHeight(47.0);
        btnCancel.setPrefWidth(128.0);
        btnCancel.setStyle("-fx-background-color: #CE4848; -fx-background-radius: 10px;");
        btnCancel.setTextFill(javafx.scene.paint.Color.WHITE);
        btnCancel.setFont(new Font(20.0));
        btnCancel.setCursor(Cursor.HAND);
        btnCancel.setOnAction(event -> modalStage.close());

        buttonHBox.getChildren().addAll(btnConfirm, btnCancel);

        vBox.getChildren().addAll(headerLabel, inputVBox, buttonHBox);

        modalStage.setScene(new javafx.scene.Scene(vBox));
        modalStage.setTitle("Thêm lịch học");
    }

    public void createDeleteModalStage() {
        Label headerLabel = new Label("Vui lòng nhập thông tin");
        headerLabel.setStyle("-fx-background-color: #CE4848;");
        headerLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        headerLabel.setFont(new Font("System Bold", 36));
        headerLabel.setPrefHeight(80);
        headerLabel.setPrefWidth(474);
        headerLabel.setAlignment(javafx.geometry.Pos.CENTER);

        ComboBox<String> comboBoxDay = new ComboBox<>();
        comboBoxDay.setPrefHeight(40);
        comboBoxDay.setPrefWidth(150);
        comboBoxDay.setPromptText("Ngày học");
        comboBoxDay.setStyle("-fx-background-radius: 4;");
        comboBoxDay.setPadding(new Insets(0, 20, 0, 20));

        Set<String> days = manageScheduleController.getAvailableDays();
        comboBoxDay.getItems().addAll(days);

        ComboBox<String> comboBoxClassName = new ComboBox<>();
        comboBoxClassName.setPrefHeight(40);
        comboBoxClassName.setPrefWidth(150);
        comboBoxClassName.setPromptText("Tên lớp");
        comboBoxClassName.setStyle("-fx-background-radius: 4;");
        comboBoxClassName.setPadding(new Insets(0, 20, 0, 20));

        comboBoxDay.setOnAction(event -> {
            String selectedDay = comboBoxDay.getValue().substring(4);
            if (selectedDay != null) {
                Set<String> classNames = manageScheduleController.getClassesByDay(Integer.parseInt(selectedDay));
                comboBoxClassName.getItems().clear();
                comboBoxClassName.getItems().addAll(classNames);
            }
        });

        Label lblRoom= new Label("Phòng học:");
        Label lblTime = new Label("Giờ học:");

        VBox vBox1 = new VBox(10, comboBoxDay, comboBoxClassName);
        VBox vBox2 = new VBox(10, lblRoom, lblTime);

        comboBoxClassName.setOnAction(event -> {
            String selectedClass = comboBoxClassName.getValue();
            String selectedDay = comboBoxDay.getValue().substring(4);
            if (selectedClass != null && selectedDay != null) {
                Schedule schedule = manageScheduleController.getScheduleByClassNameAndDay(selectedClass, Integer.parseInt(selectedDay));
                if (schedule != null) {
                    lblRoom.setText("Phòng học: " + schedule.getClassRoom());
                    lblTime.setText("Giờ học: " + schedule.getTime());
                } else {
                    lblRoom.setText("Phòng học: ");
                    lblTime.setText("Giờ học: ");
                }
            }
        });

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
        btnCancel.setOnAction(event -> modalStage.close());

        Button btnConfirm = new Button("Xác nhận");
        btnConfirm.setPrefHeight(47);
        btnConfirm.setPrefWidth(128);
        btnConfirm.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        btnConfirm.setTextFill(javafx.scene.paint.Color.WHITE);
        btnConfirm.setFont(new Font(20));
        btnConfirm.setCursor(Cursor.HAND);
        btnConfirm.setOnAction(event -> {
            String day = comboBoxDay.getValue();
            String classRoom = lblRoom.getText().substring(11);
            String time = lblTime.getText().substring(9);

            if (day == null || classRoom == null || time == null) {
                showAlert("Kiểm tra lại thông tin!", false);
            } else {
                boolean success = manageScheduleController.deleteSchedule(day, classRoom, time);
                if (success) {
                    showAlert( "Xóa lịch học thành công!", true);
                    modalStage.close();
                } else {
                    showAlert("Xóa lịch học thất bại!", false);
                }
            }
        });

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

    private void showAlert(String message, boolean closeWindow) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK && closeWindow) {
            Scene scene = cbDay.getScene();
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

    private void handleClassSelection(ComboBox<String> classComboBox, int day) {
        String selectedClass = classComboBox.getValue();

        if (selectedClass != null) {
            Schedule schedule = manageScheduleController.getScheduleByClassNameAndDay(selectedClass, day);
            if (schedule != null) {
                updateClassDetails(schedule.getClassRoom(), schedule.getTime(), day);
            } else {
                resetClassDetails();
            }
        } else {
            resetClassDetails();
        }
    }


    private void updateClassDetails(String classRoom, String time, int day) {
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

    private void resetClassDetails() {
        for (Node node : tilePane.getChildren()) {
            if (node instanceof VBox) {
                VBox vBox = (VBox) node;
                VBox content = (VBox) vBox.getChildren().get(1);
                VBox details = (VBox) content.getChildren().get(1);

                // Đặt lại chi tiết
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
