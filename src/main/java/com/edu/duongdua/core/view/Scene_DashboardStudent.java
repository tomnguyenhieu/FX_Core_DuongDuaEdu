package com.edu.duongdua.core.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

public class Scene_DashboardStudent {
    private AnchorPane anchorPane = new AnchorPane();
    public AnchorPane getAnchorPane(){
        return this.anchorPane;
    }
    CategoryAxis categoryAxis = new CategoryAxis();
    NumberAxis numberAxis = new NumberAxis();
    private LineChart totalStudentLineChart = new LineChart<>(categoryAxis, numberAxis);
    private PieChart agePieChart = new PieChart();
    private VBox topStudentContainer = new VBox();
    private VBox classesDataContainer = new VBox();
    private Label femaleStudentLabel = new Label("0");
    private Label maleStudentLabel = new Label("0");
    private Label avgAgeLabel = new Label("0");
    double maxLesson = 10;
    double maxStudent = 10;
    int ageUnder12Count = 0;
    int ageUnder22Count = 0;
    int ageOver22Count = 0;
    int avgStudentAge = 0;
    int maleStudent = 0;
    int femaleStudent = 0;
    XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
    public void setDataSeries(String month, int totalStudent) {
        dataSeries.getData().add(new XYChart.Data<>(month, totalStudent));
    }
    public void setMaleStudent(int maleStudent) {
        this.maleStudent = maleStudent;
    }
    public void setFemaleStudent(int femaleStudent) {
        this.femaleStudent = femaleStudent;
    }
    public void setAvgStudentAge(int avgStudentAge) {
        this.avgStudentAge = avgStudentAge;
    }
    public void setAgeUnder12Count(int ageUnder12Count) {
        this.ageUnder12Count = ageUnder12Count;
    }
    public void setAgeUnder22Count(int ageUnder22Count) {
        this.ageUnder22Count = ageUnder22Count;
    }
    public void setAgeOver22Count(int ageOver22Count) {
        this.ageOver22Count = ageOver22Count;
    }

    public Scene_DashboardStudent(){
        createScene();
    }

    public void createScene(){
        anchorPane.setPrefWidth(1000);
        anchorPane.setPrefHeight(720);
        anchorPane.setStyle("-fx-background-color: #F4F4F4;");
        anchorPane.setPadding(new Insets(5,0,0,0));

        // Hbox phía trên
        HBox hBox = new HBox(studentDataVbox(), totalStudentLineChart);
        hBox.setPrefWidth(972);
        hBox.setPrefHeight(340);
        hBox.setPadding(new Insets(10,10,10,10));

        // Hbox phía dưới
        HBox hBox1 = new HBox(agePieChart, topStudentVbox(), classesDataVbox());
        hBox1.setPrefWidth(972);
        hBox1.setPrefHeight(340);
        hBox1.setPadding(new Insets(10,0,10,0));
        hBox1.setSpacing(10);

        VBox vBox = new VBox();
        vBox.setPrefHeight(720);
        vBox.setPrefWidth(972);
        vBox.setLayoutX(15);
        vBox.getChildren().addAll(hBox, hBox1);
        vBox.setPadding(new Insets(15,0,0,0));
        anchorPane.getChildren().addAll(vBox);
    }

    private VBox topStudentVbox(){
        Label label = new Label("Học viên xuất sắc");
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("System", FontWeight.NORMAL, 30));
        label.setPrefHeight(66);
        label.setPrefWidth(324);
        label.setStyle("-fx-background-color:  linear-gradient(to bottom right, #F05454, #8A3030); -fx-background-radius:  10 10 0 0");
        label.setTextFill(Color.WHITE);

        topStudentContainer.setStyle("-fx-background-color: #f7f7f7");

        ScrollPane scrollPane = new ScrollPane(topStudentContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(259);
        scrollPane.setPrefWidth(324);

        VBox vBox = new VBox(label, scrollPane);

        return vBox;
    }

    private VBox classesDataVbox(){

        classesDataContainer.setPrefWidth(276);
        classesDataContainer.setPrefHeight(623);
        classesDataContainer.setSpacing(12);
        classesDataContainer.setPadding(new Insets(10,0,0,0));
        classesDataContainer.setAlignment(Pos.TOP_CENTER);
        classesDataContainer.setStyle("-fx-background-color: #F4F4F4;");

        ScrollPane scrollPane = new ScrollPane(classesDataContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(358);
        scrollPane.setPrefWidth(276);
        scrollPane.setStyle("-fx-border-color:  #F4F4F4; -fx-background-color: #F4F4F4");

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(15);
        rectangle.setWidth(48);
        rectangle.setStrokeWidth(0);
        rectangle.setFill(Color.valueOf("#f05454"));
        rectangle.setArcWidth(5);
        rectangle.setArcHeight(5);

        Label label = new Label(" Lượng tiết         ");
        label.setFont(Font.font("System", FontWeight.NORMAL, 10));

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setHeight(15);
        rectangle1.setWidth(48);
        rectangle1.setStrokeWidth(0);
        rectangle1.setFill(Color.valueOf("#30475e"));
        rectangle1.setArcWidth(5);
        rectangle1.setArcHeight(5);

        Label label1 = new Label(" Lượng học viên");
        label1.setFont(Font.font("System", FontWeight.NORMAL, 10));

        HBox hBox = new HBox(rectangle, label, rectangle1, label1);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(1));

        VBox vBox = new VBox(scrollPane, hBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPrefWidth(281);
        vBox.setPrefHeight(319);
        vBox.setPadding(new Insets(0,0,0,0));
        vBox.setStyle("-fx-background-color: #F5F5F5");

        return vBox;
    }

    private VBox studentDataVbox(){
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.THREE_PASS_BOX);
        ds.setWidth(8);
        ds.setHeight(8);
        ds.setRadius(3.5);

        Label label = new Label("Nam");
        label.setFont(Font.font("System", FontWeight.NORMAL, 22));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.TOP_CENTER);
        label.setPrefWidth(159);
        label.setPrefHeight(35);

        maleStudentLabel.setPrefHeight(59);
        maleStudentLabel.setPrefWidth(159);
        maleStudentLabel.setAlignment(Pos.CENTER);
        maleStudentLabel.setFont(Font.font("System", FontWeight.BOLD, 40));
        maleStudentLabel.setTextFill(Color.WHITE);

        VBox vBox1 = new VBox(maleStudentLabel, label);
        vBox1.setPrefWidth(160);
        vBox1.setPrefHeight(105);

        Line line = new Line();
        line.setStartX(-100);
        line.setStartY(20);
        line.setEndX(-100);
        line.setEndY(100);
        line.setFill(Color.WHITE);
        line.setStroke(Color.WHITE);

        Label label1 = new Label("Nữ");
        label1.setFont(Font.font("System", FontWeight.NORMAL, 22));
        label1.setTextFill(Color.WHITE);
        label1.setAlignment(Pos.TOP_CENTER);
        label1.setPrefWidth(159);
        label1.setPrefHeight(35);

        femaleStudentLabel.setPrefHeight(59);
        femaleStudentLabel.setPrefWidth(159);
        femaleStudentLabel.setAlignment(Pos.CENTER);
        femaleStudentLabel.setFont(Font.font("System", FontWeight.BOLD, 40));
        femaleStudentLabel.setTextFill(Color.WHITE);

        VBox vBox2 = new VBox(femaleStudentLabel, label1);
        vBox2.setPrefWidth(160);
        vBox2.setPrefHeight(105);

        HBox hBox1 = new HBox(vBox1,line, vBox2);
        hBox1.setPrefHeight(170);
        hBox1.setPrefWidth(314);
        hBox1.setSpacing(5);

        Label label2 = new Label("Số học viên đang hoạt động:");
        label2.setPrefWidth(320);
        label2.setPrefHeight(124);
        label2.setFont(Font.font("System", FontWeight.NORMAL, 22));
        label2.setTextFill(Color.WHITE);
        label2.setAlignment(Pos.CENTER);

        VBox vBox3 = new VBox(label2, hBox1);
        vBox3.setPrefHeight(170);
        vBox3.setPrefWidth(320);
        vBox3.setStyle("-fx-background-color:  linear-gradient(to bottom right, #F05454, #8A3030); -fx-background-radius: 10");
        vBox3.setEffect(ds);

        FontIcon icon = new FontIcon("fas-grin");
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(100);
        icon.setLayoutX(29);
        icon.setLayoutY(113);

        AnchorPane anchorPane1 = new AnchorPane(icon);
        anchorPane1.setPrefHeight(170);
        anchorPane1.setPrefWidth(160);

        Label label3 = new Label("Tuổi trung bình \n học viên:");
        label3.setPrefWidth(160);
        label3.setPrefHeight(72);
        label3.setFont(Font.font("System", FontWeight.NORMAL, 18));
        label3.setAlignment(Pos.BOTTOM_CENTER);
        label3.setTextAlignment(TextAlignment.CENTER);
        label3.setTextFill(Color.WHITE);

        avgAgeLabel.setFont(Font.font("System", FontWeight.BOLD, 44));
        avgAgeLabel.setAlignment(Pos.CENTER);
        avgAgeLabel.setPrefHeight(77);
        avgAgeLabel.setPrefWidth(160);
        avgAgeLabel.setTextFill(Color.WHITE);

        VBox vBox5 = new VBox(label3, avgAgeLabel);
        vBox5.setPrefHeight(163);
        vBox5.setPrefWidth(160);

        HBox hBox2 = new HBox(anchorPane1, vBox5);
        hBox2.setPrefWidth(320);
        hBox2.setPrefHeight(170);
        hBox2.setStyle("-fx-background-color:  linear-gradient(to bottom right, #F05454, #8A3030); -fx-background-radius: 10");
        hBox2.setEffect(ds);

        VBox vBox4 = new VBox(vBox3, hBox2);
        vBox4.setPrefWidth(324);
        vBox4.setPrefHeight(340);
        vBox4.setSpacing(5);

        return vBox4;
    }

    private VBox addClassBox(String name, int total_lesson, int total_student){
        double maxWidth = 200;

        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.THREE_PASS_BOX);
        ds.setWidth(8);
        ds.setHeight(8);
        ds.setRadius(3.5);


        VBox vBox = new VBox();
        vBox.setPrefHeight(68);
        vBox.setPrefWidth(250);
        vBox.setPadding(new Insets(0,0,0,15));
        vBox.setStyle("-fx-background-color:  #FFFFFF; -fx-background-radius: 12;");
        vBox.setEffect(ds);
        VBox.setMargin(vBox, new Insets(0,0,0,10));

        Label lb = new Label(name);
        lb.setPrefHeight(32);
        lb.setPrefWidth(90);
        lb.setFont(new Font(14));
        lb.setStyle("-fx-font-weight: bold;");
        lb.setAlignment(Pos.BOTTOM_LEFT);

        Rectangle lessonRec = new Rectangle();
        lessonRec.setWidth(maxWidth * (total_lesson / maxLesson) + 4);
        lessonRec.setHeight(12);
        lessonRec.setStrokeWidth(0);
        lessonRec.setFill(Paint.valueOf("#F05454"));

        Rectangle studentRec = new Rectangle();
        studentRec.setWidth(maxWidth * (total_student / maxStudent) + 4);
        studentRec.setHeight(12);
        studentRec.setStrokeWidth(0);
        studentRec.setFill(Paint.valueOf("#30475E"));

        vBox.getChildren().addAll(lb, lessonRec, studentRec);
        return vBox;
    }

    private HBox addStudentInTop(int top, String name, String className, String address, int score){
        HBox hbox = new HBox();
        hbox.setPrefWidth(324);
        hbox.setPrefHeight(66);

        Label topLb = new Label(Integer.toString(top));
        topLb.setAlignment(Pos.CENTER);
        topLb.setFont(new Font(22));
        topLb.setTextFill(Color.BLACK);
        topLb.setPrefHeight(66);
        topLb.setPrefWidth(66);
        topLb.setStyle("-fx-border-color: #8A3030;-fx-font-weight: bold; -fx-border-width: 0 0 1 0;");

        VBox vBox = new VBox();
        vBox.setPrefHeight(66);
        vBox.setPrefWidth(179);
        vBox.setPadding(new Insets(0,0,0,10));

        Label scoreLb = new Label(Integer.toString(score) + "đ");
        scoreLb.setPrefHeight(66);
        scoreLb.setPrefWidth(66);
        scoreLb.setFont(new Font(22));
        scoreLb.setAlignment(Pos.CENTER);
        scoreLb.setStyle("-fx-font-weight: bold; -fx-background-color:  #DCDCDC; -fx-background-radius: 90;-fx-background-insets: 7;");

        Label nameLb = new Label(name);
        nameLb.setPrefWidth(240);
        nameLb.setPrefHeight(44);
        nameLb.setFont(new Font(24));
        nameLb.setTextFill(Paint.valueOf("#30475e"));
        nameLb.setStyle("-fx-font-weight: bold;");
        nameLb.setAlignment(Pos.BOTTOM_LEFT);


        Label infoStudentLb = new Label(className + ", " + address);
        infoStudentLb.setPrefWidth(240);
        infoStudentLb.setPrefHeight(22);
        infoStudentLb.setFont(new Font(12));
        infoStudentLb.setAlignment(Pos.TOP_LEFT);


        vBox.getChildren().addAll(nameLb, infoStudentLb);
        hbox.getChildren().addAll(topLb, vBox, scoreLb);

        return hbox;
    }

    private void numberAnim(Label lb, int num){
        final int[] count = {0};
        int milliDuration;
        if (num <= 10) {
            milliDuration = 100;
        } else{
            milliDuration = 30;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(milliDuration), event -> {
                    if (count[0] < num) {
                        count[0]++;
                        lb.setText(Integer.toString(count[0]));
                    }
                })
        );
        timeline.setCycleCount(num);
        timeline.play();
    }

    public void setUpTopStudent(int top, String name, String className, String address, int score){
        HBox hBox = addStudentInTop(top, name, className, address, score);
        topStudentContainer.getChildren().add(hBox);
    }

    public void setUpClassData(String name, int totalStudent, int totalLesson){
        VBox vBox = addClassBox(name, totalLesson, totalStudent);
        classesDataContainer.getChildren().add(vBox);
    }

    public void setUpLineChart(){
        totalStudentLineChart.setPrefHeight(340);
        totalStudentLineChart.setPrefWidth(638);
        totalStudentLineChart.setTitle("Tổng số học viên theo tháng");
        totalStudentLineChart.setLegendVisible(false);
        totalStudentLineChart.setStyle("-fx-background-color: #F4F4F4");
        numberAxis.setLabel("Số học viên");

        totalStudentLineChart.getData().add(dataSeries);
    }

    public void setUpPieChart(){
        agePieChart.setTitle("Tỉ lệ tuổi học viên");
        agePieChart.setPrefHeight(300);
        agePieChart.setPrefWidth(324);
        System.out.println(ageUnder12Count);

        PieChart.Data ageUnder12CountData = new PieChart.Data("Dưới 12 tuổi",ageUnder12Count);
        PieChart.Data ageUnder22CountData = new PieChart.Data("Dưới 22 tuổi",ageUnder22Count);
        PieChart.Data ageOver22CountData = new PieChart.Data("Trên 22 tuổi",ageOver22Count);
        agePieChart.getData().clear();
        agePieChart.getData().addAll(ageUnder12CountData, ageUnder22CountData, ageOver22CountData);
        agePieChart.setLabelsVisible(true);
        agePieChart.setLabelLineLength(1);
        agePieChart.setLegendVisible(false);
        agePieChart.setStartAngle(0);
        agePieChart.getData().get(0).getNode().setStyle("-fx-background-color: #F05454;");
        agePieChart.getData().get(1).getNode().setStyle("-fx-background-color: #8A3030;");
        agePieChart.getData().get(2).getNode().setStyle("-fx-background-color: #30475E;");
    }

    public void setUpStudentData(){
        numberAnim(avgAgeLabel, avgStudentAge);
        numberAnim(maleStudentLabel, maleStudent);
        numberAnim(femaleStudentLabel, femaleStudent);
    }
}
