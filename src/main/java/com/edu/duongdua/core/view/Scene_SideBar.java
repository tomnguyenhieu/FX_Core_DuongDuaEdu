package com.edu.duongdua.core.view;

import com.edu.duongdua.core.controller.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Scene_SideBar {
    private BorderPane mainLayout;
    VBox menuContainer = new VBox(20);
    VBox content = new VBox(12);

    public DashboardTeacherEmployeeController dashboardTeacherEmployeeController = new DashboardTeacherEmployeeController();
    public Scene_SideBar(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
        mainLayout.setCenter(dashboardTeacherEmployeeController.sceneDashboardTeacherEmployee.getAnchorPane());
    }

    public VBox createSideBar() {
        HBox logoSection = createLogoSection();
        ScrollPane menuSection = createMenuSection();
        HBox userSection = createUserSection();

        VBox side = new VBox();
        side.setPrefSize(280, 720);
        side.setStyle("-fx-background-color: #30475E;");
        side.getChildren().addAll(logoSection, menuSection, userSection);
        return side;
    }

    private HBox createLogoSection() {
        HBox logoSection = new HBox(16);
        logoSection.setPrefSize(280,120);
        logoSection.setPadding(new Insets(16, 0, 0, 16));

        ImageView imageView = new ImageView(new Image(getClass().getResource("/GermLogo.png").toExternalForm()));
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);

        Label label = new Label("Germ \nEducation");
        label.setPrefSize(140, 75);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 28));

        logoSection.getChildren().addAll(imageView, label);
        return logoSection;
    }

    private ScrollPane createMenuSection() {
        ScrollPane menu = new ScrollPane();
        menu.setPrefSize(280, 521);
        menu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menu.setStyle("-fx-background: #30475E; -fx-background-color: transparent;");

        menuContainer.setPrefWidth(269);
        menuContainer.setPadding(new Insets(16, 16, 0, 16));
        menuContainer.getChildren().addAll(
                createTitlePane("Thống kê"),
                createMenuItem("Lớp học", "ClassScene"),
                createMenuItem("Giáo viên", "TeacherScene"),
                createMenuItem("Nhân viên", "EmployeeScene"),
                createMenuItem("Học viên", "StudentScene"),
                createMenuItem("Lịch học & Phòng học", "ScheduleScene")
        );

        menu.setContent(menuContainer);
        return menu;
    }

    private TitledPane createTitlePane(String title) {
        TitledPane titledPaneItem = new TitledPane();
        titledPaneItem.setAlignment(Pos.CENTER);
        titledPaneItem.setExpanded(false);
        titledPaneItem.setText(title);
        titledPaneItem.setStyle("-fx-font-size: 24px;");
        titledPaneItem.setCursor(Cursor.HAND);

        content.setStyle("-fx-background-color: #30475E;");
        content.getChildren().addAll(
                createSubMenuItem("Giáo viên & Nhân viên", "TeacherEmployeeDashboard"),
                createSubMenuItem("Học viên", "StudentDashboard"),
                createSubMenuItem("Khoản thu", "EarningDashboard"),
                createSubMenuItem("Khoản chi", "ExpenseDashboard")
        );

        titledPaneItem.setContent(content);
        return titledPaneItem;
    }


    // Event được xử lí trong này này!!
    private HBox createMenuItem(String title, String id) {
        HBox menuItem = new HBox();
        menuItem.setPrefSize(180, 55);
        menuItem.setCursor(Cursor.HAND);
        menuItem.setStyle("-fx-background-radius: 5;");
        menuItem.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(title);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Roboto", FontWeight.NORMAL, 22));
        label.setPadding(new Insets(0,0,0,12));
        menuItem.getChildren().add(label);
        menuItem.setId(id);

        menuItem.setOnMouseClicked(event -> {
            String boxId = ((Node) event.getSource()).getId();
            switch (boxId){
                case "StudentScene":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    ManageStudentController manageStudentController = new ManageStudentController();
                    mainLayout.setCenter(manageStudentController.sceneManageStudent.getAnchorPane());
                    break;
                case "EmployeeScene":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    ManageEmployeeController manageEmployeeController = new ManageEmployeeController();
                    mainLayout.setCenter(manageEmployeeController.sceneManageEmployee.getAnchorPane());
                    break;
                case "ClassScene":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    ManageClassController manageClassController = new ManageClassController();
                    mainLayout.setCenter(manageClassController.sceneManageClass.getAnchorPane());
                    break;
                case "ScheduleScene":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    ManageScheduleController manageScheduleController = new ManageScheduleController();
                    mainLayout.setCenter(manageScheduleController.manageScheduleView.getAnchorPane());
                    break;
                case "TeacherScene":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    ManageTeacherController manageTeacherController = new ManageTeacherController();
                    mainLayout.setCenter(manageTeacherController.sceneManageTeacher.getAnchorPane());
                    break;
                case "TeacherEmployeeDashboard":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    DashboardTeacherEmployeeController dashboardTeacherEmployeeController = new DashboardTeacherEmployeeController();
                    mainLayout.setCenter(dashboardTeacherEmployeeController.sceneDashboardTeacherEmployee.getAnchorPane());
                    break;
                case "StudentDashboard":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    DashboardStudentController dashboardStudentController = new DashboardStudentController();
                    mainLayout.setCenter(dashboardStudentController.sceneDashboardStudent.getAnchorPane());
                    break;
                case "EarningDashboard":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    DashboardEarningController dashboardEarningController = new DashboardEarningController();
                    mainLayout.setCenter(dashboardEarningController.sceneDashboardEarning.getAnchorPane());
                    break;
                case "ExpenseDashboard":
                    resetAllButtonStyle();
                    menuItem.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");
                    DashboardExpenseController dashboardExpenseController = new DashboardExpenseController();
                    mainLayout.setCenter(dashboardExpenseController.sceneDashboardExpense.getAnchorPane());
                    break;
            }
        });
        return menuItem;
    }

    private HBox createSubMenuItem(String title, String id) {
        return createMenuItem(title, id);
    }

    private HBox createUserSection() {
        HBox userSection = new HBox(16);
        userSection.setAlignment(Pos.CENTER_LEFT);
        userSection.setLayoutX(10);
        userSection.setLayoutY(10);
        userSection.setPrefSize(280, 86);
        userSection.setPadding(new Insets(16, 0, 0, 16));

        ImageView userImage = new ImageView(new Image(getClass().getResource("/User.jpg").toExternalForm()));
        userImage.setFitWidth(56);
        userImage.setFitHeight(56);

        Label userName = new Label("ADMIN");
        userName.setTextFill(Color.WHITE);
        userName.setFont(Font.font("Roboto", FontWeight.BOLD, 24));

        userSection.getChildren().addAll(userImage, userName);
        return userSection;
    }

    private void resetAllButtonStyle(){
        for(int i = 1; i <= 5; i++){
            menuContainer.getChildren().get(i).setStyle("-fx-background-color: #30475E;");
        }
        for(int i = 0; i <= 3; i++){
            content.getChildren().get(i).setStyle("-fx-background-color: #30475E;");
        }
    }
}
