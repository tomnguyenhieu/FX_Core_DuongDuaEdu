package com.edu.duongdua.core.view;

import com.edu.duongdua.core.model.Classes;
import com.edu.duongdua.core.model.Comment;
import com.edu.duongdua.core.model.Lesson;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Scene_ManageDiary
{
    private AnchorPane anchorPane;

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public Scene_ManageDiary()
    {
        createScene();
    }

    public Stage modalStage = new Stage();
    public VBox lessonsContainer = new VBox(15);
    private Button exportButton = new Button();
    private Button uploadButton = new Button();
    private Button confirmButton = new Button();

    public TableView<Lesson> tblComment1 = new TableView<>();
    public TableColumn<Lesson, String> tblCol1Title = new TableColumn<>("Thời gian");
    public TableColumn<Lesson, String> tblCol1Content = new TableColumn<>("Nội dung");
    public TableColumn<Lesson, String> tblCol1Class = new TableColumn<>("Lớp");
    public TableColumn<Lesson, String> tblCol1Teacher = new TableColumn<>("Giáo viên");

    public TableView<Comment> tblComment2 = new TableView<>();
    public TableColumn<Comment, String> tblCol2StudentName = new TableColumn<>("Họ và tên");
    public TableColumn<Comment, String> tblCol2StudentComment = new TableColumn<>("Nhận xét");
    public TableColumn<Comment, String> tblCol2StudentScore = new TableColumn<>("Điểm số");

    public void createScene()
    {
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1000, 720);

        VBox mainContainer = new VBox();
        mainContainer.setPrefSize(1000, 720);
        mainContainer.setStyle("-fx-background-color: #ffffff;");

        // Header Section
        HBox header = new HBox(16);
        header.setPrefSize(1000, 120);
        header.setAlignment(Pos.BOTTOM_CENTER);
        header.setPadding(new Insets(0, 16, 16, 16));

        Label headerLabel = new Label("Bảng nhận xét");
        headerLabel.setPrefSize(700, 84);
        headerLabel.setStyle("-fx-background-color: #F5F5F5; -fx-text-fill: #30475e;");
        headerLabel.setFont(new javafx.scene.text.Font("Roboto Bold", 43));
        headerLabel.setAlignment(Pos.CENTER);

        uploadButton.setText("Đăng tải nhật kí");
        uploadButton.setId("uploadBtn");
        uploadButton.setCursor(Cursor.HAND);
        uploadButton.setPrefSize(278, 35);
        uploadButton.setStyle("-fx-background-color: #30475E; -fx-background-radius: 8; -fx-text-fill: WHITE;");
        uploadButton.setFont(new javafx.scene.text.Font(20));
        FontIcon uploadIcon = new FontIcon("fas-upload");
        uploadIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        uploadButton.setGraphic(uploadIcon);

        header.getChildren().addAll(headerLabel, uploadButton);
        mainContainer.getChildren().add(header);

        // Content Section
        HBox content = new HBox();
        content.setPrefSize(1000, 600);
        content.setPadding(new Insets(0, 16, 16, 16));
        content.setSpacing(16);

        // Lessons ScrollPane
        ScrollPane lessonsScrollPane = new ScrollPane();
        lessonsScrollPane.setPrefSize(370, 600);
        lessonsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        lessonsContainer.setPrefSize(280, 546);
        lessonsContainer.setStyle("-fx-background-color: #F5F5F5;");
        lessonsContainer.setPadding(new Insets(12, 12, 12, 12));

        lessonsScrollPane.setContent(lessonsContainer);
        content.getChildren().add(lessonsScrollPane);

        // Tables Section
        VBox tablesContainer = new VBox(16);
        tablesContainer.setPrefSize(720, 600);

        tblComment1.setPrefSize(700, 124);
        tblComment1.setFixedCellSize(65);
        tblComment1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tblComment1.getColumns().addAll(tblCol1Title, tblCol1Content, tblCol1Class, tblCol1Teacher);

        tblComment2.setPrefSize(390, 500);
        tblComment2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tblComment2.getColumns().addAll(tblCol2StudentName, tblCol2StudentComment, tblCol2StudentScore);

        tablesContainer.getChildren().addAll(tblComment1, tblComment2);
        content.getChildren().add(tablesContainer);
        mainContainer.getChildren().add(content);

        // Export Button
        StackPane exportButtonContainer = new StackPane();
        exportButtonContainer.setPrefSize(100, 100);
        exportButtonContainer.setLayoutX(850);
        exportButtonContainer.setLayoutY(580);

        exportButton.setId("exportBtn");
        exportButton.setCursor(Cursor.HAND);
        exportButton.setPrefSize(70, 70);
        exportButton.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10;");
        FontIcon exportIcon = new FontIcon("fas-download");
        exportIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        exportIcon.setIconSize(20);
        exportButton.setGraphic(exportIcon);

        exportButtonContainer.getChildren().add(exportButton);
        anchorPane.getChildren().addAll(mainContainer, exportButtonContainer);

        confirmButton.setText("Xác nhận");
    }

    public void createConfirmModal(File file)
    {
        Label nameLabel = new Label("Tên file" + file.getName());
        nameLabel.setPrefSize(474, 100);
        nameLabel.setWrapText(true);
        nameLabel.setFont(new Font(20));

        // Cancel Button
        Button cancelButton = new Button("Hủy");
        cancelButton.setPrefSize(128, 47);
        cancelButton.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        cancelButton.setTextFill(Color.WHITE);
        cancelButton.setFont(new Font(20));
        cancelButton.setCursor(Cursor.HAND);
        cancelButton.setOnMouseClicked(event -> {
            modalStage.close();
        });

        // Confirm Button
        confirmButton.setId("confirmBtn");
        confirmButton.setPrefSize(128, 47);
        confirmButton.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        confirmButton.setTextFill(Color.WHITE);
        confirmButton.setFont(new Font(20));
        confirmButton.setCursor(Cursor.HAND);

        // HBox for buttons
        HBox buttonBox = new HBox(15, cancelButton, confirmButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPrefSize(474, 54);
        buttonBox.setPadding(new Insets(0, 0, 0, 20));

        // VBox container
        VBox root = new VBox(40, nameLabel, buttonBox);
        root.setPrefSize(423, 208);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: #FFFFFF;");

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.centerOnScreen();
        modalStage.show();
    }

    public void initLessonBtn(int count, EventHandler<Event> eventHandler)
    {
        String lessonName = "Lesson";
        Button lessonBtn = new Button(lessonName + " " + count);
        lessonBtn.setId("lessonBtn");
        lessonBtn.setMinSize(260, 55);
        lessonBtn.setPrefSize(200, 55);
        lessonBtn.setCursor(Cursor.HAND);
        lessonBtn.setStyle("-fx-background-color: #D9D9D9; -fx-font-size: 20");
        lessonBtn.setTextFill(Color.BLACK);
        lessonBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        lessonsContainer.getChildren().add(lessonBtn);
    }

    public void displayLessons(int count, EventHandler<Event> eventHandler)
    {
        for (int i = 1; i <= count; i++)
        {
            initLessonBtn(i, eventHandler);
        }
    }

    public void resetAllBtn(int count)
    {
        for (int i = 0; i < count; i++)
        {
            Button btn = (Button) lessonsContainer.getChildren().get(i);
            btn.setTextFill(Color.BLACK);
            btn.setStyle("-fx-background-color: #D9D9D9; -fx-font-size: 20");
        }
        if (!lessonsContainer.getChildren().isEmpty())
        {
            Button btn = (Button) lessonsContainer.getChildren().getLast();
            btn.setTextFill(Color.BLACK);
            btn.setStyle("-fx-background-color: #D9D9D9; -fx-font-size: 20");
        }
    }

    public void setActive(Button button)
    {
        button.setStyle("-fx-background-color: #F05454; -fx-font-size: 20");
        button.setTextFill(Color.WHITE);
    }

    public void loadTable1Comment(ObservableList<Lesson> data)
    {
        tblCol1Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblCol1Content.setCellValueFactory(new PropertyValueFactory<>("content"));
        tblCol1Class.setCellValueFactory(new PropertyValueFactory<>("className"));
        tblCol1Teacher.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        tblComment1.setItems(data);
    }

    public void loadTable2Comment(ObservableList<Comment> data)
    {
        tblCol2StudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tblCol2StudentComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        tblCol2StudentScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        tblComment2.setItems(data);
    }

    public void addEventListener(EventHandler<Event> eventHandler)
    {
        confirmButton.setOnMouseClicked(eventHandler);
        uploadButton.setOnMouseClicked(eventHandler);
        exportButton.setOnMouseClicked(eventHandler);
    }
}
