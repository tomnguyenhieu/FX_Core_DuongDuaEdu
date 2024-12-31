package com.edu.duongdua.core.controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SideBarController implements EventHandler<MouseEvent> {

    private List<HBox> listHboxs = new ArrayList<>();

    private VBox menuContainer = new VBox();

    private VBox content = new VBox();

    private BorderPane root ;


    // Nhận BorderPane từ SideBar
    public SideBarController(BorderPane root, VBox menuContainer, VBox content) {
        this.root = root;
        this.menuContainer = menuContainer;
        this.content = content;

    }

    public void resetAllSidebarItems() {
        // Kiểm tra menuContainer.getChildren() có đủ phần tử
        if (menuContainer.getChildren().size() >= 4) {
            for (int i = 0; i < 4; i++) {
                listHboxs.add((HBox) menuContainer.getChildren().get(i));
            }
        } else {
            System.out.println("menuContainer không chứa đủ phần tử!");
        }

        // Kiểm tra content.getChildren() có đủ phần tử
        if (content.getChildren().size() >= 6) {
            for (int i = 1; i < 6; i++) {
                listHboxs.add((HBox) content.getChildren().get(i));
            }
        } else {
//            System.out.println("content không chứa đủ phần tử!");
        }

        // Thiết lập style cho các HBox
        for (HBox item : listHboxs) {
            item.setStyle("-fx-background-color: #30475E;");
            Label labelTest = (Label) item.getChildren().get(0);
            labelTest.setTextFill(Color.WHITE);
            System.out.println(item.getChildren());
        }
    }

    // Phương thức chuyển đổi Scene
//    private void switchToScene(String sceneName) {
//        switch (sceneName) {
//            case "scheduleScene": // ID tương ứng
//                ManageScheduleScene manageScheduleScene = new ManageScheduleScene();
//                root.setCenter(manageScheduleScene.createScene());
//                break;
//
//            case "classScene": // ID tương ứng
//                ManageClassScene manageClassScene = new ManageClassScene();
//                root.setCenter(manageClassScene.mcs());
//                break;
//
//
//            default:
//                Label errorLabel = new Label("Không tìm thấy Scene tương ứng!");
//                root.setCenter(errorLabel);
//                break;
//        }
//    }

    @Override
    public void handle(MouseEvent event) {

        if (event.getSource() instanceof HBox) {

            // Reset trạng thái sidebar
            resetAllSidebarItems();

            HBox hbox = (HBox) event.getSource();

            // Đổi màu HBox được click
            hbox.setStyle("-fx-background-color: #F05454; -fx-background-radius: 5px;");

            for (HBox item: listHboxs){
                System.out.println(item);
            }

            // Chuyển Scene dựa trên ID của HBox
            String sceneName = hbox.getId(); // Lấy ID của HBox
            if (sceneName != null)
            {
//                switchToScene(sceneName); // Gọi phương thức chuyển Scene
//                switch (sceneName) {
//                    case "scheduleScene":
//                        ManageScheduleScene manageScheduleScene = new ManageScheduleScene();
//                        ManageScheduleController scheduleController = new ManageScheduleController(manageScheduleScene.getTilePane());
//                        scheduleController.displaySchedule();
//                        root.setCenter(manageScheduleScene.createScene());
//                        break;
//
//                    default:
//                        root.setCenter(new Label("Không tìm thấy Scene!"));
//                }
            }
        }
    }
}





