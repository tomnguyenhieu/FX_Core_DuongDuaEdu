package com.edu.duongdua.core.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Scene_DashboardExpense
{
    private LocalDate today = LocalDate.now();
    public Integer currentMonth = today.getMonthValue();
    public Integer currentYear = today.getYear();
    public String time = currentMonth.toString() + "/" + currentYear.toString();
    private int year = 0;

    private AnchorPane anchorPane;

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    Button uploadButton = new Button();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    Label totalTeachersSalary = new Label();
    Label totalStaffsSalary = new Label();
    Label totalExpenseValue = new Label();
    Label totalCSVC = new Label();

    LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    CategoryAxis xAxis2 = new CategoryAxis();
    NumberAxis yAxis2 = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis2, yAxis2);

    public void createScene()
    {
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1000, 720);

        VBox mainContainer = new VBox();
        mainContainer.setPrefSize(1000, 720);

        HBox topContainer = new HBox(8);
        topContainer.setPrefSize(1000, 370);

        lineChart.setPrefSize(700, 370);

        VBox sideContainer = new VBox(12);
        sideContainer.setPrefSize(300, 330);

        VBox teachersSalaryBox = new VBox();
        teachersSalaryBox.setPrefSize(300, 185);
        teachersSalaryBox.setStyle("-fx-background-color: #30475E; -fx-background-radius: 12;");
        teachersSalaryBox.setPadding(new Insets(2, 16, 2, 24));

        HBox teachersSalaryHeader = new HBox();
        Label teachersSalaryLabel = new Label("Lương giáo viên");
        teachersSalaryLabel.setPrefSize(128, 100);
        teachersSalaryLabel.setWrapText(true);
        teachersSalaryLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        teachersSalaryLabel.setFont(Font.font("System Bold", 25));

        FontIcon teachersSalaryIcon = new FontIcon("fas-money-check");
        teachersSalaryIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        teachersSalaryIcon.setIconSize(80);
        HBox.setMargin(teachersSalaryIcon, new Insets(8, 0, 0, 0));

        teachersSalaryHeader.getChildren().addAll(teachersSalaryLabel, teachersSalaryIcon);

        totalTeachersSalary.setText("0");
        totalTeachersSalary.setPrefSize(231, 50);
        totalTeachersSalary.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        totalTeachersSalary.setTextFill(javafx.scene.paint.Color.WHITE);
        totalTeachersSalary.setFont(Font.font("System Bold", 34));

        teachersSalaryBox.getChildren().addAll(teachersSalaryHeader, totalTeachersSalary);

        VBox staffsSalaryBox = new VBox();
        staffsSalaryBox.setPrefSize(300, 185);
        staffsSalaryBox.setStyle("-fx-background-color: #30475E; -fx-background-radius: 12;");
        staffsSalaryBox.setPadding(new Insets(2, 16, 2, 24));

        HBox staffsSalaryHeader = new HBox();
        Label staffsSalaryLabel = new Label("Lương nhân viên");
        staffsSalaryLabel.setPrefSize(130, 100);
        staffsSalaryLabel.setWrapText(true);
        staffsSalaryLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        staffsSalaryLabel.setFont(Font.font("System Bold", 25));

        FontIcon staffsSalaryIcon = new FontIcon("fas-money-check");
        staffsSalaryIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        staffsSalaryIcon.setIconSize(80);
        HBox.setMargin(staffsSalaryIcon, new Insets(8, 0, 0, 0));

        staffsSalaryHeader.getChildren().addAll(staffsSalaryLabel, staffsSalaryIcon);

        totalStaffsSalary.setText("0");
        totalStaffsSalary.setPrefSize(231, 50);
        totalStaffsSalary.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        totalStaffsSalary.setTextFill(javafx.scene.paint.Color.WHITE);
        totalStaffsSalary.setFont(Font.font("System Bold", 34));

        staffsSalaryBox.getChildren().addAll(staffsSalaryHeader, totalStaffsSalary);

        sideContainer.getChildren().addAll(teachersSalaryBox, staffsSalaryBox);
        HBox.setMargin(sideContainer, new Insets(0, 16, 24, 0));

        topContainer.getChildren().addAll(lineChart, sideContainer);
        VBox.setMargin(topContainer, new Insets(16, 16, 0, 16));

        HBox bottomContainer = new HBox();
        bottomContainer.setPrefSize(1000, 350);

        VBox bottomLeftContainer = new VBox();
        bottomLeftContainer.setSpacing(8);
        bottomLeftContainer.setPrefSize(350, 334);

        HBox totalExpenseBox = new HBox();
        totalExpenseBox.setAlignment(javafx.geometry.Pos.CENTER);
        totalExpenseBox.setPrefSize(200, 100);
        totalExpenseBox.setStyle("-fx-background-color: #30475E; -fx-background-radius: 8;");

        Label totalExpenseLabel = new Label("Tổng chi");
        totalExpenseLabel.setPrefSize(113, 79);
        totalExpenseLabel.setWrapText(true);
        totalExpenseLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        totalExpenseLabel.setFont(Font.font("System Bold", 28));

        totalExpenseValue.setText("0");
        totalExpenseValue.setPrefSize(200, 88);
        totalExpenseValue.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        totalExpenseValue.setTextFill(javafx.scene.paint.Color.WHITE);
        totalExpenseValue.setFont(Font.font("System Bold", 60));

        totalExpenseBox.getChildren().addAll(totalExpenseLabel, totalExpenseValue);

        VBox totalCSVCBox = new VBox();
        totalCSVCBox.setAlignment(javafx.geometry.Pos.CENTER);
        totalCSVCBox.setPrefSize(338, 163);
        totalCSVCBox.setStyle("-fx-background-color: #30475E; -fx-background-radius: 8 8 0 0;");
        totalCSVCBox.setPadding(new Insets(0, 0, 8, 0));

        Label totalCSVCLabel = new Label("Tổng chi CSVC");
        totalCSVCLabel.setAlignment(Pos.CENTER);
        totalCSVCLabel.setPrefSize(302, 79);
        totalCSVCLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        totalCSVCLabel.setFont(Font.font("System Bold", 28));

        HBox totalCSVCContent = new HBox(50);
        totalCSVCContent.setAlignment(javafx.geometry.Pos.CENTER);
        totalCSVCContent.setPadding(new Insets(0, 0, 0, 20));

        FontIcon totalCSVCIcon = new FontIcon("fas-money-bill");
        totalCSVCIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        totalCSVCIcon.setIconSize(100);

        VBox totalCSVCDetails = new VBox();
        totalCSVCDetails.setPrefSize(130, 119);

        totalCSVC.setText("0");
        totalCSVC.setPrefSize(122, 88);
        totalCSVC.setAlignment(javafx.geometry.Pos.CENTER);
        totalCSVC.setTextFill(javafx.scene.paint.Color.WHITE);
        totalCSVC.setFont(Font.font("System Bold", 34));

        Label totalCSVCCount = new Label("Số lượng");
        totalCSVCCount.setAlignment(Pos.CENTER);
        totalCSVCCount.setPrefSize(122, 31);
        totalCSVCCount.setTextFill(javafx.scene.paint.Color.WHITE);
        totalCSVCCount.setFont(Font.font(21));

        totalCSVCDetails.getChildren().addAll(totalCSVC, totalCSVCCount);
        totalCSVCContent.getChildren().addAll(totalCSVCIcon, totalCSVCDetails);
        totalCSVCBox.getChildren().addAll(totalCSVCLabel, totalCSVCContent);

        uploadButton.setText("Upload excel");
        uploadButton.setId("uploadBtn");
        uploadButton.setPrefSize(338, 54);
        uploadButton.setStyle("-fx-background-color: #F05454; -fx-background-radius: 0 0 8 8;");
        uploadButton.setTextFill(javafx.scene.paint.Color.WHITE);
        uploadButton.setFont(Font.font(20));
        uploadButton.setCursor(Cursor.HAND);
        FontIcon uploadIcon = new FontIcon("fas-upload");
        uploadIcon.setIconColor(Paint.valueOf("#ffffff"));
        uploadButton.setGraphic(uploadIcon);

        bottomLeftContainer.getChildren().addAll(totalExpenseBox, totalCSVCBox, uploadButton);

        barChart.setPrefSize(641, 334);

        bottomContainer.getChildren().addAll(bottomLeftContainer, barChart);
        VBox.setMargin(bottomContainer, new Insets(0, 16, 16, 16));

        mainContainer.getChildren().addAll(topContainer, bottomContainer);
        anchorPane.getChildren().add(mainContainer);
    }

    public void renderLineChart(ArrayList<List<String>> data, String dataName)
    {
        yAxis.setLabel("Tổng lương");
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        for (List<String> item : data)
        {
            dataSeries.getData().add(new XYChart.Data<>(item.getFirst(), Integer.parseInt(item.get(1))));
        }
        dataSeries.setName(dataName);
        lineChart.getData().add(dataSeries);
    }

    public void renderBarChart(ArrayList<List<String>> data, String dataName)
    {
        yAxis.setLabel("Số lượng");
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        for (List<String> item : data)
        {
            dataSeries.getData().add(new XYChart.Data<>(item.getFirst(), Integer.parseInt(item.get(1))));
        }

        dataSeries.setName(dataName);
        barChart.getData().clear();
        barChart.getData().add(dataSeries);
    }

    public void initDataBox(int teacherTotalSalary, int employeeTotalSalary, int CSVCExpense)
    {
        totalTeachersSalary.setText(Integer.toString(teacherTotalSalary));
        totalStaffsSalary.setText(Integer.toString(employeeTotalSalary));
        totalCSVC.setText(Integer.toString(CSVCExpense));

        int totalExpense = teacherTotalSalary + employeeTotalSalary + CSVCExpense;
        totalExpenseValue.setText(Integer.toString(totalExpense));
    }

    public void addEventListener(EventHandler<Event> eventHandler)
    {
        uploadButton.setOnMouseClicked(eventHandler);
    }
}
