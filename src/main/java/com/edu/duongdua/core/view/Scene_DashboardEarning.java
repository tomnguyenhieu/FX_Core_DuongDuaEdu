package com.edu.duongdua.core.view;

import com.edu.duongdua.core.model.Bill;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.List;


public class Scene_DashboardEarning {
    private LocalDate today = LocalDate.now();
    public Integer currentMonth = today.getMonthValue();
    public Integer currentYear = today.getYear();
    public String time = currentMonth.toString() + "/" + currentYear.toString();
    public ComboBox<Integer> selectYearCB;
    public BarChart<String, Number> earningByYearChart;
    NumberAxis yAxis = new NumberAxis();
    CategoryAxis xAxis = new CategoryAxis();
    Label totalEarningValue = new Label();
    Label avgEarningValue = new Label();
    Label totalInterestValue = new Label();
    Label increaseValue = new Label();

    public Scene_DashboardEarning () {
        createScene();
    }
    private AnchorPane anchorPane = new AnchorPane();

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void createScene () {
        // VBox as the main container
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setPrefSize(1000, 720);

        // Top HBox for year selection
        HBox topContainer = new HBox(10);
        topContainer.setPrefSize(200, 100);

        selectYearCB = new ComboBox<>();
        selectYearCB.setPrefSize(100, 24);

        topContainer.getChildren().addAll(selectYearCB);

        // BarChart
        earningByYearChart = new BarChart<>(xAxis, yAxis);

        // Bottom HBox for statistics
        HBox statsContainer = new HBox();
        statsContainer.setPrefSize(960, 124);
        statsContainer.setStyle("-fx-background-color: #30475E;");

        // "Tổng thu học phí" section
        VBox totalEarningBox = new VBox();
        totalEarningBox.setPrefSize(259, 131);

        Label totalEarningTitle = new Label("Tổng thu học phí:");
        totalEarningTitle.setFont(new Font(21));
        totalEarningTitle.setTextFill(Color.WHITE);
        totalEarningTitle.setPrefSize(259, 46);
        totalEarningTitle.setAlignment(javafx.geometry.Pos.CENTER);

        totalEarningValue.setText("0");
        totalEarningValue.setFont(new Font(37));
        totalEarningValue.setTextFill(Color.WHITE);
        totalEarningValue.setPrefSize(265, 69);
        totalEarningValue.setAlignment(javafx.geometry.Pos.CENTER);

        totalEarningBox.getChildren().addAll(totalEarningTitle, totalEarningValue);

        // Separator line
        Line separator1 = new Line(-100, 100, -100, 0);
        separator1.setStroke(Color.WHITE);

        // "Doanh thu trung bình" section
        VBox avgEarningBox = new VBox();
        avgEarningBox.setPrefSize(259, 131);

        Label avgEarningTitle = new Label("Doanh thu trung bình:");
        avgEarningTitle.setFont(new Font(21));
        avgEarningTitle.setTextFill(Color.WHITE);
        avgEarningTitle.setPrefSize(259, 46);
        avgEarningTitle.setAlignment(javafx.geometry.Pos.CENTER);

        avgEarningValue.setText("0");
        avgEarningValue.setFont(new Font(37));
        avgEarningValue.setTextFill(Color.WHITE);
        avgEarningValue.setPrefSize(265, 69);
        avgEarningValue.setAlignment(javafx.geometry.Pos.CENTER);

        avgEarningBox.getChildren().addAll(avgEarningTitle, avgEarningValue);

        // Separator line
        Line separator2 = new Line(-100, 100, -100, 0);
        separator2.setStroke(Color.WHITE);

        // "Tổng lãi" section
        VBox totalInterestBox = new VBox();
        totalInterestBox.setPrefSize(259, 131);

        Label totalInterestTitle = new Label("Tổng lãi");
        totalInterestTitle.setFont(new Font(21));
        totalInterestTitle.setTextFill(Color.WHITE);
        totalInterestTitle.setPrefSize(259, 46);
        totalInterestTitle.setAlignment(javafx.geometry.Pos.CENTER);

        totalInterestValue.setText("0");
        totalInterestValue.setFont(new Font(37));
        totalInterestValue.setTextFill(Color.WHITE);
        totalInterestValue.setPrefSize(265, 69);
        totalInterestValue.setAlignment(javafx.geometry.Pos.CENTER);

        totalInterestBox.getChildren().addAll(totalInterestTitle, totalInterestValue);

        // Separator line
        Line separator3 = new Line(-100, 100, -100, 0);
        separator3.setStroke(Color.WHITE);

        // "Tăng" section
        VBox increaseBox = new VBox();
        increaseBox.setPrefSize(259, 131);

        Label increaseTitle = new Label("Tăng:");
        increaseTitle.setFont(new Font(21));
        increaseTitle.setTextFill(Color.WHITE);
        increaseTitle.setPrefSize(259, 46);
        increaseTitle.setAlignment(javafx.geometry.Pos.CENTER);

        increaseValue.setText("0");
        increaseValue.setFont(new Font(37));
        increaseValue.setTextFill(Color.WHITE);
        increaseValue.setPrefSize(265, 69);
        increaseValue.setAlignment(javafx.geometry.Pos.CENTER);

        increaseBox.getChildren().addAll(increaseTitle, increaseValue);

        // Add all sections and separators to the container
        statsContainer.getChildren().addAll(totalEarningBox, separator1, avgEarningBox, separator2, totalInterestBox, separator3, increaseBox);

        // Assemble UI
        mainContainer.getChildren().addAll(topContainer, earningByYearChart, statsContainer);
        anchorPane.getChildren().add(mainContainer);
    }

    // Hiển thị các năm lên combobox
    public void initYearCB (List<Integer> years) {
        for (Integer year : years)
        {
            selectYearCB.getItems().add(year);
        }
    }

    public void renderBarChart (List<Bill> earningByMonths) {
        earningByYearChart.getData().clear();
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Bill monthlyEarning : earningByMonths) {
            dataSeries.getData().add(new XYChart.Data<>(monthlyEarning.getTime(), monthlyEarning.getTotal_price()));
        }

        yAxis.setLabel("Tổng thu");
        dataSeries.setName("Tháng");
        earningByYearChart.getData().add(dataSeries);
    }

    public void renderLabel (int totalEarningByYear, int avgMonthlyEarning, int totalInterset, double increase) {
        totalEarningValue.setText(Integer.toString(totalEarningByYear));
        avgEarningValue.setText(Integer.toString(avgMonthlyEarning));
        totalInterestValue.setText(Integer.toString(totalInterset));
        increaseValue.setText(Integer.toString((int) increase) + "%");
    }

    public void onActionListener(EventHandler<ActionEvent> eventHandler) {
        selectYearCB.setOnAction(eventHandler);
    }
}
