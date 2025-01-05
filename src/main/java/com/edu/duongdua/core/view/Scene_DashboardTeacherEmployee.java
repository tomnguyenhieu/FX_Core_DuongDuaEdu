package com.edu.duongdua.core.view;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Bill;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Scene_DashboardTeacherEmployee
{
    private LocalDate today = LocalDate.now();
    private Integer currentMonth = today.getMonthValue();
    public Integer currentYear = today.getYear();
    private String time = currentMonth.toString() + "/" + currentYear.toString();

    private AnchorPane anchorPane;

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private Label teacherTotalLabel = new Label("0");
    private Label teacherAverageLabel = new Label("0");
    private Label staffTotalLabel = new Label("0");
    private Label staffAverageLabel = new Label("0");
    public ComboBox<Integer> yearComboBox = new ComboBox<>();
    public TableView<Account> teacherTableView = new TableView<>();
    private TableColumn<Account, String> idColumn = new TableColumn<>("Id");
    private TableColumn<Account, String> nameColumn = new TableColumn<>("Tên giáo viên");
    private CategoryAxis xAxis2 = new CategoryAxis();
    private NumberAxis yAxis2 = new NumberAxis();
    private BarChart<String, Number> barChart = new BarChart<>(xAxis2, yAxis2);

    public Scene_DashboardTeacherEmployee()
    {
        createScene();
    }

    public void createScene()
    {
        anchorPane = new AnchorPane();

        // Main layout
        VBox mainVBox = new VBox();
        mainVBox.setPrefSize(1000, 720);
        mainVBox.setStyle("-fx-padding: 20; -fx-spacing: 16;");

        // Upper section
        HBox upperHBox = new HBox(8);
        upperHBox.setPrefSize(1000, 370);

        // Line chart
        lineChart.setPrefSize(700, 370);

        // Right VBox
        VBox rightVBox = new VBox(12);
        rightVBox.setPrefSize(300, 330);

        // Teacher Stats Box
        VBox teacherStatsBox = new VBox();
        teacherStatsBox.setStyle("-fx-background-color: #30475E; -fx-background-radius: 12; -fx-padding: 16;");
        teacherStatsBox.setPrefSize(300, 185);

        Label teacherTitle = new Label("Giáo viên");
        teacherTitle.setStyle("-fx-text-fill: white;");
        teacherTitle.setFont(new javafx.scene.text.Font("System Bold", 26));

        HBox teacherDetails = new HBox();
        teacherDetails.setStyle("-fx-spacing: 16;");

        VBox teacherTotalBox = new VBox();
        teacherTotalBox.setStyle("-fx-spacing: 4;");
        teacherTotalLabel.setFont(new javafx.scene.text.Font(45));
        teacherTotalLabel.setStyle("-fx-text-fill: white;");
        Label teacherTotalText = new Label("Tổng giáo viên");
        teacherTotalText.setFont(new javafx.scene.text.Font(16));
        teacherTotalText.setStyle("-fx-text-fill: white;");
        teacherTotalBox.getChildren().addAll(teacherTotalLabel, teacherTotalText);

        VBox teacherAverageBox = new VBox();
        teacherAverageBox.setStyle("-fx-spacing: 4;");
        teacherAverageLabel.setFont(new javafx.scene.text.Font(45));
        teacherAverageLabel.setStyle("-fx-text-fill: white;");
        Label teacherAverageText = new Label("Tuổi trung bình");
        teacherAverageText.setFont(new javafx.scene.text.Font(16));
        teacherAverageText.setStyle("-fx-text-fill: white;");
        teacherAverageBox.getChildren().addAll(teacherAverageLabel, teacherAverageText);

        teacherDetails.getChildren().addAll(teacherTotalBox, teacherAverageBox);
        teacherStatsBox.getChildren().addAll(teacherTitle, teacherDetails);

        // Staff Stats Box
        VBox staffStatsBox = new VBox();
        staffStatsBox.setStyle("-fx-background-color: #30475E; -fx-background-radius: 12; -fx-padding: 16;");
        staffStatsBox.setPrefSize(300, 185);

        Label staffTitle = new Label("Nhân viên");
        staffTitle.setStyle("-fx-text-fill: white;");
        staffTitle.setFont(new javafx.scene.text.Font("System Bold", 26));

        HBox staffDetails = new HBox();
        staffDetails.setStyle("-fx-spacing: 16;");

        VBox staffTotalBox = new VBox();
        staffTotalBox.setStyle("-fx-spacing: 4;");

        staffTotalLabel.setFont(new javafx.scene.text.Font(45));
        staffTotalLabel.setStyle("-fx-text-fill: white;");
        Label staffTotalText = new Label("Tổng nhân viên");
        staffTotalText.setFont(new javafx.scene.text.Font(16));
        staffTotalText.setStyle("-fx-text-fill: white;");
        staffTotalBox.getChildren().addAll(staffTotalLabel, staffTotalText);

        VBox staffAverageBox = new VBox();
        staffAverageBox.setStyle("-fx-spacing: 4;");
        staffAverageLabel.setFont(new javafx.scene.text.Font(45));
        staffAverageLabel.setStyle("-fx-text-fill: white;");
        Label staffAverageText = new Label("Tuổi trung bình");
        staffAverageText.setFont(new javafx.scene.text.Font(16));
        staffAverageText.setStyle("-fx-text-fill: white;");
        staffAverageBox.getChildren().addAll(staffAverageLabel, staffAverageText);

        staffDetails.getChildren().addAll(staffTotalBox, staffAverageBox);
        staffStatsBox.getChildren().addAll(staffTitle, staffDetails);

        rightVBox.getChildren().addAll(teacherStatsBox, staffStatsBox);

        upperHBox.getChildren().addAll(lineChart, rightVBox);

        // Lower section
        HBox lowerHBox = new HBox();
        lowerHBox.setPrefSize(1000, 350);
        lowerHBox.setStyle("-fx-spacing: 20;");

        VBox leftVBox = new VBox(12);
        leftVBox.setPrefSize(360, 334);
        leftVBox.setStyle("-fx-spacing: 16;");

        yearComboBox.setPromptText("Chọn năm");
        yearComboBox.setPrefSize(145, 42);

        teacherTableView.setId("teacherTable");
        teacherTableView.setPrefSize(333, 241);
        idColumn.setPrefWidth(75);
        nameColumn.setPrefWidth(267);

        teacherTableView.getColumns().addAll(idColumn, nameColumn);
        leftVBox.getChildren().addAll(yearComboBox, teacherTableView);

        barChart.setPrefSize(641, 334);

        lowerHBox.getChildren().addAll(leftVBox, barChart);

        mainVBox.getChildren().addAll(upperHBox, lowerHBox);
        anchorPane.getChildren().add(mainVBox);
    }

    public void renderLineChart(List<Bill> teacherStatistical, List<Bill> employeeStatistical)
    {
        lineChart.getData().clear();
        yAxis.setLabel("Số lượng");
        // Giáo viên
        XYChart.Series<String, Number> dataSeriesTeachers = new XYChart.Series<>();
        for (Bill bill : teacherStatistical)
        {
            dataSeriesTeachers.getData().add(new XYChart.Data<>(bill.getTime(), bill.getCountMembers()));
        }
        dataSeriesTeachers.setName("Giáo viên");
        lineChart.getData().add(dataSeriesTeachers);

        // Nhân viên
        XYChart.Series<String, Number> dataSeriesEmployees = new XYChart.Series<>();
        for (Bill bill : employeeStatistical)
        {
            dataSeriesEmployees.getData().add(new XYChart.Data<>(bill.getTime(), bill.getCountMembers()));
        }
        dataSeriesEmployees.setName("Nhân viên");
        lineChart.getData().add(dataSeriesEmployees);
    }

    public void initBoxData(List<Bill> teacherStatistical, List<Bill> employeeStatistical)
    {
        if (currentMonth <= 9)
        {
            time = "0" + currentMonth.toString() + "/" + currentYear.toString();
        }
        for (Bill bill : teacherStatistical)
        {
            if (bill.getTime().equals(time))
            {
                teacherTotalLabel.setText(Integer.toString(bill.getCountMembers()));
                teacherAverageLabel.setText(Integer.toString((int) bill.getAvgAge()));
            }
        }
        for (Bill bill : employeeStatistical)
        {
            if (bill.getTime().equals(time))
            {
                staffTotalLabel.setText(Integer.toString(bill.getCountMembers()));
                staffAverageLabel.setText(Integer.toString((int) bill.getAvgAge()));
            }
        }
    }

    public void initYearsComboBox(List<Integer> years)
    {
        for (Integer year : years)
        {
            yearComboBox.getItems().add(year);
        }
    }

    public void renderTeachersTable(ObservableList<Account> teacherList)
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherTableView.setItems(teacherList);
    }

    public void renderBarChart(List<Bill> lessonsList)
    {
        barChart.getData().clear();
        XYChart.Series<String, Number> dataSeriesLessons = new XYChart.Series<>();
        if (!lessonsList.isEmpty())
        {
            for (Bill bill : lessonsList)
            {
                dataSeriesLessons.getData().add(new XYChart.Data<>(bill.getTime(), bill.getLessonQty()));
            }
            barChart.getData().add(dataSeriesLessons);
        } else
        {
            barChart.getData().clear();
        }
    }

    public void addActionListener(EventHandler<Event> eventHandler)
    {
        teacherTableView.setOnMouseClicked(eventHandler);
    }

    public void addOnActionListener(EventHandler<ActionEvent> eventHandler)
    {
        yearComboBox.setOnAction(eventHandler);
    }
}
