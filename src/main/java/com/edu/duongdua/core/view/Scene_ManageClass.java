package com.edu.duongdua.core.view;

import com.edu.duongdua.core.Main;
import com.edu.duongdua.core.controller.ManageClassController;
import com.edu.duongdua.core.controller.ManageDiaryController;
import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Classes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.xmlbeans.impl.store.Cur;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;


public class Scene_ManageClass
{
    private AnchorPane anchorPane;

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    private VBox vbox;
    private HBox hbox;
    private Button addClassBtn;
    private Button exportExcelBtn;
    private ScrollPane scrollPane;
    private TilePane tilePane;

    public TilePane getTilePane() {
        return tilePane;
    }

    public void setTilePane(TilePane tilePane) {
        this.tilePane = tilePane;
    }

    private StackPane stackPane;
    public ComboBox<String> comboBox;
    public Label classNameLabel;
    private AnchorPane trashIconContainer;
    private AnchorPane editIconContainer;
    public ComboBox<String> cbTeachersName = new ComboBox<>();
    private Button confirmButton = new Button();
    public TextField inputClass = new TextField();
    public Stage modalStage = new Stage();
    public String tmpClassName;
    private Button btnConfirm = new Button();
    public ComboBox<String> cbClasses = new ComboBox<>();

    public TableView<Account> studentTable = new TableView<>();
    public TableColumn<Account, String> nameCol = new TableColumn<>("Họ và tên");
    public TableColumn<Account, String> ageCol = new TableColumn<>("Tuổi");

    public Scene_ManageClass()
    {
        createScene();
    }

    public void createScene()
    {
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

        Label label = new Label("DANH SÁCH CÁC LỚP HỌC");
        label.setPrefSize(700, 84);
        label.setFont(Font.font("System", FontWeight.BOLD, 42));
        label.setTextFill(Paint.valueOf("#30475e"));
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: #F5F5F5;");

        addClassBtn = new Button("Thêm lớp");
        addClassBtn.setId("addClassBtn");
        addClassBtn.setPrefSize(278, 35);
        addClassBtn.setStyle("-fx-background-color:  #30475E");
        addClassBtn.setFont(Font.font("Roboto", 20));
        addClassBtn.setTextFill(Color.WHITE);
        addClassBtn.setAlignment(Pos.CENTER);
        addClassBtn.setCursor(Cursor.HAND);

        exportExcelBtn = new Button("Export excel");
        exportExcelBtn.setId("exportExcelBtn");
        exportExcelBtn.setPrefSize(278, 35);
        exportExcelBtn.setStyle("-fx-background-color:  #30475E");
        exportExcelBtn.setFont(Font.font("Roboto", 20));
        exportExcelBtn.setTextFill(Color.WHITE);
        exportExcelBtn.setAlignment(Pos.CENTER);
        exportExcelBtn.setCursor(Cursor.HAND);

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

        stackPane = new StackPane();
        stackPane.setPrefSize(200, 63);
        stackPane.setLayoutX(790);
        stackPane.setLayoutY(105);

        comboBox = new ComboBox<>();
        comboBox.setPrefWidth(150);
        comboBox.setCursor(Cursor.HAND);

        anchorPane.getChildren().addAll(vbox, stackPane);
        vbox.getChildren().addAll(hbox, scrollPane);
        hbox.getChildren().addAll(label, addClassBtn, exportExcelBtn);
        scrollPane.setContent(tilePane);
        stackPane.getChildren().add(comboBox);

        cbTeachersName.setPromptText("Giáo viên");
        cbClasses.setPromptText("Lớp học");
        confirmButton.setText("Xác nhận");
        btnConfirm.setText("Xác nhận");
    }

    public void createModalStage(String className)
    {
        VBox root = new VBox();
        root.setPrefHeight(331);
        root.setPrefWidth(474);
        root.setSpacing(20);
        root.setStyle("-fx-background-color: FFFFFF;");

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.centerOnScreen();
        modalStage.show();

        Label label = new Label("Vui lòng nhập thông tin");
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(80);
        label.setPrefWidth(474);
        label.setStyle("-fx-background-color: #CE4848;");
        label.setTextFill(javafx.scene.paint.Color.web("#fffafa"));
        label.setFont(new Font("System Bold", 36));

        VBox inputBox = new VBox();
        inputBox.setPrefHeight(133);
        inputBox.setPrefWidth(474);
        inputBox.setSpacing(10);

        inputClass.setText(className);
        inputClass.setPrefHeight(65);
        inputClass.setPrefWidth(474);
        inputClass.setPromptText("Nhập tên lớp");
        inputClass.setStyle("-fx-background-radius: 4;");
        VBox.setMargin(inputClass, new Insets(0, 20, 0, 20));
        inputClass.setFont(new Font(18));

        cbTeachersName.setPrefHeight(65);
        cbTeachersName.setPrefWidth(474);
        cbTeachersName.setPromptText("Tên giáo viên");
        VBox.setMargin(cbTeachersName, new Insets(0, 20, 0, 20));

        inputBox.getChildren().addAll(inputClass, cbTeachersName);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPrefHeight(54);
        buttonBox.setPrefWidth(474);
        buttonBox.setSpacing(15);
        buttonBox.setPadding(new Insets(0, 20, 0, 20));

        Button cancelButton = new Button("Hủy");
        cancelButton.setPrefHeight(47);
        cancelButton.setPrefWidth(128);
        cancelButton.setStyle("-fx-background-color: #30475E; -fx-font-style: #F5F5F5; -fx-background-radius: 10px;");
        cancelButton.setTextFill(javafx.scene.paint.Color.WHITE);
        cancelButton.setFont(new Font(20));
        cancelButton.setCursor(javafx.scene.Cursor.HAND);
        cancelButton.setOnMouseClicked(event -> {
            modalStage.close();
        });

        confirmButton.setId("confirmBtn");
        confirmButton.setPrefHeight(47);
        confirmButton.setPrefWidth(128);
        confirmButton.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        confirmButton.setTextFill(javafx.scene.paint.Color.WHITE);
        confirmButton.setFont(new Font(20));
        confirmButton.setCursor(javafx.scene.Cursor.HAND);

        buttonBox.getChildren().addAll(cancelButton, confirmButton);

        root.getChildren().addAll(label, inputBox, buttonBox);
    }

    public void createExportModalStage()
    {
        Label headerLabel = new Label("Vui lòng chọn lớp");
        headerLabel.setPrefSize(474, 80);
        headerLabel.setStyle("-fx-background-color: #CE4848; -fx-text-fill: #fffafa;");
        headerLabel.setFont(Font.font("System Bold", 36));
        headerLabel.setAlignment(Pos.CENTER);

        cbClasses.setPrefSize(474, 65);
        cbClasses.setPromptText("Lớp");
        VBox.setMargin(cbClasses, new Insets(0, 20, 0, 20));

        Button btnCancel = new Button("Hủy");
        btnCancel.setCursor(Cursor.HAND);
        btnCancel.setPrefSize(128, 47);
        btnCancel.setStyle("-fx-background-color: #30475E; -fx-font-style: #F5F5F5; -fx-background-radius: 10px;");
        btnCancel.setTextFill(javafx.scene.paint.Color.WHITE);
        btnCancel.setFont(Font.font(20));
        btnCancel.setOnMouseClicked(event -> {
            modalStage.close();
        });

        btnConfirm.setId("excelConfirm");
        btnConfirm.setCursor(Cursor.HAND);
        btnConfirm.setPrefSize(128, 47);
        btnConfirm.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        btnConfirm.setTextFill(javafx.scene.paint.Color.WHITE);
        btnConfirm.setFont(Font.font(20));

        HBox buttonContainer = new HBox(15, btnCancel, btnConfirm);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.setPrefSize(474, 54);
        buttonContainer.setPadding(new Insets(0, 20, 0, 20));

        VBox root = new VBox(20, headerLabel, cbClasses, buttonContainer);
        root.setPrefSize(474, 250);
        root.setStyle("-fx-background-color: #FFFFFF;");

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setTitle("Export Form Excel");
        modalStage.show();
    }

    public void createClassInfoModalStage(String strClassName, String strTeacherName)
    {
        Label className = new Label("Lớp học: " + strClassName);
        className.setPrefSize(600, 80);
        className.setStyle("-fx-background-color: #CE4848;");
        className.setTextFill(javafx.scene.paint.Color.valueOf("#fffafa"));
        className.setFont(Font.font("System Bold", 36));
        className.setAlignment(Pos.CENTER);

        Label teacherName = new Label("Giáo viên: " + strTeacherName);
        teacherName.setPrefSize(472, 76);
        teacherName.setStyle("-fx-background-color: #ffffff;");
        teacherName.setFont(Font.font(20));
        teacherName.setPadding(new Insets(0, 0, 0, 16));

        studentTable.setPrefSize(200, 200);
        studentTable.setFixedCellSize(35);
        nameCol.setPrefWidth(360);
        ageCol.setPrefWidth(102.4);
        studentTable.getColumns().clear();
        studentTable.getColumns().addAll(nameCol, ageCol);

        VBox vboxContent = new VBox(20, teacherName, studentTable);
        vboxContent.setPrefSize(472, 286);
        VBox.setMargin(vboxContent, new Insets(0, 16, 0, 16));

        Button btnClose = new Button("Đóng");
        btnClose.setPrefSize(128, 47);
        btnClose.setStyle("-fx-background-color: #30475E; -fx-background-radius: 10px;");
        btnClose.setTextFill(javafx.scene.paint.Color.WHITE);
        btnClose.setFont(Font.font(20));
        btnClose.setCursor(Cursor.HAND);
        btnClose.setOnMouseClicked(event -> {
            modalStage.close();
        });
        VBox.setMargin(btnClose, new Insets(0, 16, 0, 0));

        VBox root = new VBox(12, className, vboxContent, btnClose);
        root.setPrefSize(504, 453);
        root.setAlignment(Pos.TOP_RIGHT);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setTitle("Class Info");
        modalStage.show();
    }

    public void initClassBox(Classes classObj, EventHandler<Event> eventHandler)
    {
        VBox vbox = new VBox();
        vbox.setMinSize(240, 150);
        vbox.setPrefSize(240, 150);
        vbox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 8");

        classNameLabel = new Label();
        classNameLabel.setText(classObj.getClassName());
        classNameLabel.setId("classNameBtn");
        classNameLabel.setMinSize(240, 46);
        classNameLabel.setPrefSize(240, 46);
        classNameLabel.setFont(new Font("Roboto", 24));
        classNameLabel.setTextFill(Color.WHITE);
        classNameLabel.setAlignment(Pos.CENTER);
        classNameLabel.setCursor(Cursor.HAND);
        classNameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        classNameLabel.setStyle("-fx-font-weight: bold; -fx-background-color:  #F05454; -fx-background-radius: 8 8 0 0");

        HBox hbox = new HBox();
        hbox.setMinSize(200, 100);
        hbox.setPrefSize(200, 100);

        VBox vboxText = new VBox();
        vboxText.setId("classInfoBtn");
        vboxText.setMinSize(190, 82);
        vboxText.setPrefSize(190, 82);
        if (classObj.getClassDeleted() == 2) {
            vboxText.setMinSize(218, 82);
            vboxText.setPrefSize(218, 82);
        }
        vboxText.setPadding(new Insets(0, 0, 0, 12));
        vboxText.setStyle("-fx-background-color:  #ffffff; -fx-background-radius: 6");
        HBox.setMargin(vboxText, new Insets(10, 0, 10, 10));
        if (classObj.getClassDeleted() == 1)
        {
            vboxText.setCursor(Cursor.HAND);
            vboxText.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        }

        Label teacherNameLabel = new Label();
        teacherNameLabel.setText("Giáo viên: " + classObj.getClassTeacherName());
        teacherNameLabel.setMinSize(190, 41);
        teacherNameLabel.setFont(new Font(14));

        Label totalStudentsLabel = new Label();
        totalStudentsLabel.setText("Học sinh: " + classObj.getClassTotalStudents() + " em");
        totalStudentsLabel.setMinSize(190, 41);
        totalStudentsLabel.setFont(new Font(14));

        vboxText.getChildren().addAll(teacherNameLabel, totalStudentsLabel);

        VBox vboxIcon = new VBox();
        editIconContainer = new AnchorPane();
        editIconContainer.setId("editBtn");
        if (classObj.getClassDeleted() == 1) {
            editIconContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            editIconContainer.setMinSize(40, 52);
            editIconContainer.setCursor(Cursor.HAND);

            FontIcon editIcon = new FontIcon();
            editIcon.setIconLiteral("fas-pen");
            editIcon.setLayoutX(10);
            editIcon.setLayoutY(35);
            editIcon.setIconColor(Color.WHITE);
            editIcon.setIconSize(20);
            editIconContainer.getChildren().add(editIcon);
        }

        trashIconContainer = new AnchorPane();
        trashIconContainer.setId("trashBtn");
        if (classObj.getClassDeleted() == 1) {
            trashIconContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            trashIconContainer.setMinSize(40, 52);
            trashIconContainer.setCursor(Cursor.HAND);

            FontIcon trashIcon = new FontIcon();
            trashIcon.setIconLiteral("fas-trash-alt");
            trashIcon.setLayoutX(10);
            trashIcon.setLayoutY(30);
            trashIcon.setIconColor(Color.WHITE);
            trashIcon.setIconSize(20);
            trashIconContainer.getChildren().add(trashIcon);
        }

        vboxIcon.getChildren().addAll(editIconContainer, trashIconContainer);
        hbox.getChildren().addAll(vboxText, vboxIcon);
        vbox.getChildren().addAll(classNameLabel, hbox);
        tilePane.getChildren().add(vbox);
    }

    public void displayClass(List<Classes> classesInfo, EventHandler<Event> eventHandler)
    {
        for (Classes _class : classesInfo)
        {
            initClassBox(_class, eventHandler);
        }
    }

    public void initComboBoxStatus()
    {
        comboBox.getItems().add("Đang hoạt động");
        comboBox.getItems().add("Dừng hoạt động");

        comboBox.setValue("Đang hoạt động");
    }

    public void initComboBoxTeachersName(List<Account> accountList)
    {
        cbTeachersName.getItems().clear();
        for (Account teacher : accountList)
        {
            cbTeachersName.getItems().add(teacher.getName());
        }
    }

    public void initClassesComboBox(List<Classes> classesList)
    {
        cbClasses.getItems().clear();
        for (Classes _class : classesList)
        {
            cbClasses.getItems().add(_class.getClassName());
        }
    }

    public void renderTblStudents(ObservableList<Account> studentList)
    {
        studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        studentTable.setItems(studentList);
    }

    public void addEventListener(EventHandler<Event> eventHandler)
    {
        addClassBtn.setOnMouseClicked(eventHandler);
        exportExcelBtn.setOnMouseClicked(eventHandler);
        confirmButton.setOnMouseClicked(eventHandler);
        btnConfirm.setOnMouseClicked(eventHandler);
    }

    public void addOnActionListener(EventHandler<ActionEvent> eventHandler)
    {
        comboBox.setOnAction(eventHandler);
    }
}
