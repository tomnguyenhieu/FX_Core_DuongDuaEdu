package com.edu.duongdua.core.view;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Bill;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Arrays;
import java.util.List;


public class Scene_ManageEmployee {
    double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private boolean firstClick = true;
    private AnchorPane anchorPane;
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    HBox editHbox = new HBox();
    HBox deleteHbox = new HBox();
    HBox addHbox = new HBox();
    FontIcon moveIcon = new FontIcon();
    HBox confirmHbox = new HBox();
    Label confirmLabel = new Label("Xác nhận");
    HBox cancelHbox = new HBox();
    Label cancelLabel = new Label("Từ chối");
    HBox updateBillHbox = new HBox();
    StackPane billStackPane = new StackPane();

    TextField nameTextField = new TextField();
    ComboBox<Integer> ageComboBox = new ComboBox<>();
    ComboBox<String> genderComboBox = new ComboBox<>();
    TextField emailTextField = new TextField();
    TextField passwordTextField = new TextField();
    TextField phoneTextField = new TextField();
    ComboBox<String> addressComboBox = new ComboBox<>();
    TextField salaryTextField = new TextField();
    TextField certificatesTextField = new TextField();
    ComboBox<String> statusComboBox = new ComboBox<>();

    public String getName(){
        return nameTextField.getText();
    }
    public int getAge(){
        return Integer.parseInt(ageComboBox.getValue().toString());
    }
    public String getGender(){
        return genderComboBox.getValue();
    }
    public String getEmail(){
        return emailTextField.getText();
    }
    public String getPassword(){
        return passwordTextField.getText();
    }
    public String getPhone(){
        return phoneTextField.getText();
    }
    public String getAddress(){
        return addressComboBox.getValue();
    }
    public String getSalary(){
        return salaryTextField.getText();
    }
    public String getCertificates(){
        return certificatesTextField.getText();
    }
    public String getStatus(){
        return statusComboBox.getValue();
    }

    public TableView<Account> employeeTable = new TableView<>();
    TableColumn<Account, String> idCol = new TableColumn<>("Id");
    TableColumn<Account, String> nameCol = new TableColumn<>("Họ và tên");
    TableColumn<Account, String> ageCol = new TableColumn<>("Tuổi");
    TableColumn<Account, String> genderCol = new TableColumn<>("Giới tính");
    TableColumn<Account, String> emailCol = new TableColumn<>("Email");
    TableColumn<Account, String> passCol = new TableColumn<>("Mật khẩu");
    TableColumn<Account, String> phoneCol = new TableColumn<>("Số điện thoại");
    TableColumn<Account, String> addressCol = new TableColumn<>("Địa chỉ");
    TableColumn<Account, String> salaryCol = new TableColumn<>("Lương");
    TableColumn<Account, String> certificatesCol = new TableColumn<>("Bằng cấp");
    TableColumn<Account, String> statusCol = new TableColumn<>("Trạng thái");

    public TableView<Bill> billTable = new TableView<>();
    TableColumn<Bill, String> dateCollum = new TableColumn<>("Tháng");
    TableColumn<Bill, String> priceCollum = new TableColumn<>("Lương");
    TableColumn<Bill, String> statusCollum = new TableColumn<>("Trạng thái");
    TableColumn<Bill, String> updateCollum = new TableColumn<>();

    public Scene_ManageEmployee() {
        createScene();
    }

    public void createScene(){
        anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(720);
        anchorPane.setPrefWidth(1000);
        anchorPane.setStyle("-fx-background-color: #FFFFFF;");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        hBox.setPrefWidth(954);
        hBox.setPrefHeight(84);
        hBox.setLayoutX(23);
        hBox.setLayoutY(14);

        Label label = new Label("DANH SÁCH NHÂN VIÊN");
        label.setFont(Font.font("System", FontWeight.BOLD, 42));
        label.setTextFill(Paint.valueOf("#30475e"));
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: #F5F5F5;");
        label.setPrefWidth(672);
        label.setPrefHeight(84);


        Label label1 = new Label("Sửa ");
        label1.setFont(Font.font("System", FontWeight.NORMAL, 22));
        label1.setTextFill(Color.WHITE);

        FontIcon editIcon = new FontIcon("fas-edit");
        editIcon.setIconSize(20);
        editIcon.setIconColor(Color.WHITE);

        editHbox.getChildren().addAll(label1, editIcon);
        editHbox.setId("Edit");
        editHbox.setAlignment(Pos.CENTER_LEFT);
        editHbox.setPadding(new Insets(0,0,0,30));
        editHbox.setPrefHeight(44);
        editHbox.setPrefWidth(124);
        editHbox.setMaxHeight(44);
        HBox.setMargin(editHbox, new Insets(0,0,0,20));
        editHbox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 10;");
        editHbox.setCursor(Cursor.HAND);

        Label label2 = new Label("Xóa ");
        label2.setFont(Font.font("System", FontWeight.NORMAL, 22));
        label2.setTextFill(Color.WHITE);

        FontIcon deleteIcon = new FontIcon("fas-trash-alt");
        deleteIcon.setIconSize(20);
        deleteIcon.setIconColor(Color.WHITE);

        deleteHbox.getChildren().addAll(label2, deleteIcon);
        deleteHbox.setId("Delete");
        deleteHbox.setPadding(new Insets(0,0,0,30));
        deleteHbox.setAlignment(Pos.CENTER_LEFT);
        deleteHbox.setPrefHeight(44);
        deleteHbox.setPrefWidth(124);
        deleteHbox.setMaxHeight(44);
        deleteHbox.setLayoutX(702);
        deleteHbox.setLayoutY(20);
        HBox.setMargin(deleteHbox, new Insets(0,0,0,20));
        deleteHbox.setStyle("-fx-background-color: #F05454; -fx-background-radius: 10;");
        deleteHbox.setCursor(Cursor.HAND);

        hBox.getChildren().addAll(label, editHbox, deleteHbox);

        StackPane addStackPane = new StackPane();
        addStackPane.setLayoutX(860);
        addStackPane.setLayoutY(600);
        addStackPane.setPrefHeight(86);
        addStackPane.setPrefWidth(84);
        addStackPane.setAlignment(Pos.BOTTOM_RIGHT);

        moveIcon.setIconLiteral("fas-arrows-alt");
        moveIcon.setIconSize(20);
        moveIcon.setIconColor(Color.BLACK);
        moveIcon.setLayoutX(65);
        moveIcon.setLayoutY(65);

        FontIcon addIcon = new FontIcon("fas-user-plus");
        addIcon.setIconSize(28);
        addIcon.setIconColor(Color.WHITE);


        addHbox.setId("Add");
        addHbox.getChildren().add(addIcon);
        addHbox.setAlignment(Pos.CENTER);
        addHbox.setMaxHeight(70);
        addHbox.setMaxWidth(70);
        addHbox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 10");
        addHbox.setCursor(Cursor.HAND);

        addStackPane.getChildren().addAll(addHbox, moveIcon);
        addStackPane.setAlignment(moveIcon, Pos.TOP_LEFT);
        addStackPane.setOnMousePressed(event -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            addStackPane.setScaleY(1.1);
            addStackPane.setScaleX(1.1);
            orgTranslateX = addStackPane.getTranslateX();
            orgTranslateY = addStackPane.getTranslateY();
        });
        addStackPane.setOnMouseReleased(event -> {
            addStackPane.setScaleY(1);
            addStackPane.setScaleX(1);
        });
        addStackPane.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            addStackPane.setTranslateX(newTranslateX);
            addStackPane.setTranslateY(newTranslateY);
        });

        billTable.setPrefWidth(439);
        billTable.setPrefHeight(198);
        billTable.setFixedCellSize(35);
        billTable.getColumns().addAll(dateCollum, priceCollum, statusCollum, updateCollum);

        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.THREE_PASS_BOX);
        ds.setWidth(8);
        ds.setHeight(8);
        ds.setRadius(3.5);
        billStackPane.setPrefWidth(439);
        billStackPane.setPrefHeight(198);
        billStackPane.setAlignment(Pos.CENTER);
        billStackPane.getChildren().add(billTable);
        billStackPane.setEffect(ds);
        billStackPane.setVisible(false);

        employeeTable.setId("EmployeeTable");
        employeeTable.setOnMousePressed(event -> {
            double posX = event.getX();
            double posY = event.getY();
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.15), billStackPane);
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(.15), billStackPane);

            if(posX + 23 < 1080 - 526 && posY + 107 < 594-120){
                billStackPane.setLayoutX(posX + 30);
                billStackPane.setLayoutY(posY + 120);
            } else if (posX + 23 >= 1080 - 526){
                if(posY + 107 >= 594 -100){
                    billStackPane.setLayoutX(1080-526);
                    billStackPane.setLayoutY(594-100);
                } else {
                    billStackPane.setLayoutX(1080-526);
                    billStackPane.setLayoutY(posY + 120);
                }
            } else{
                billStackPane.setLayoutX(posX + 40);
                billStackPane.setLayoutY(594 - 100);
            }

            if(firstClick){
                translateTransition.setFromX(-200);
                translateTransition.setFromY(-90);
                translateTransition.setToX(0);
                translateTransition.setToY(0);
                scaleTransition.setFromX(0.1);
                scaleTransition.setFromY(0.1);
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                translateTransition.play();
                scaleTransition.play();
            }
            billStackPane.setVisible(firstClick);
            firstClick = !firstClick;
        });

        idCol.setPrefWidth(30);
        nameCol.setPrefWidth(170);
        ageCol.setPrefWidth(50);
        genderCol.setPrefWidth(64);
        emailCol.setPrefWidth(100);
        passCol.setPrefWidth(80);
        phoneCol.setPrefWidth(114);
        addressCol.setPrefWidth(92);
        certificatesCol.setPrefWidth(90);
        salaryCol.setPrefWidth(70);
        statusCol.setPrefWidth(70);


        dateCollum.setPrefWidth(90);
        priceCollum.setPrefWidth(120);
        statusCollum.setPrefWidth(120);
        updateCollum.setPrefWidth(90);

        confirmHbox.getChildren().add(confirmLabel);
        cancelHbox.getChildren().add(cancelLabel);

        anchorPane.getChildren().addAll(hBox, employeeTable, addStackPane, billStackPane);
    }

    public void createModalStage(){
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setPrefWidth(858);
        root.setPrefHeight(595);
        root.setAlignment(Pos.TOP_RIGHT);
        root.setPadding(new Insets(25,20,0,20));
        root.setSpacing(20);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.centerOnScreen();
        stage.show();

        VBox vBox = new VBox();

        HBox hBox = new HBox();
        hBox.setPrefWidth(820);
        hBox.setPrefHeight(75);
        VBox vBox1 = new VBox();
        vBox1.setPrefHeight(75);
        vBox1.setPrefWidth(618);
        Label label = new Label("Họ và tên");
        label.setPrefHeight(31);
        label.setPrefWidth(122);
        label.setFont(Font.font("System", FontWeight.NORMAL, 20));
        nameTextField.setId("nameTextField");
        nameTextField.setPrefHeight(47);
        nameTextField.setPrefWidth(618);
        vBox1.getChildren().addAll(label, nameTextField);
        vBox1.setPadding(new Insets(4,4,0,4));

        VBox vBox2 = new VBox();
        vBox2.setPrefWidth(92);
        vBox2.setPrefHeight(75);
        Label label1 = new Label("Tuổi");
        label1.setFont(Font.font("System", FontWeight.NORMAL, 20));
        ageComboBox.setPrefWidth(79);
        ageComboBox.setPrefHeight(54);
        vBox2.getChildren().addAll(label1, ageComboBox);
        vBox2.setPadding(new Insets(4,4,0,4));

        VBox vBox3 = new VBox();
        vBox3.setPrefWidth(110);
        vBox3.setPrefHeight(75);
        Label label2 = new Label("Giới tính");
        label2.setFont(Font.font("System", FontWeight.NORMAL, 20));
        genderComboBox.setPrefHeight(41);
        genderComboBox.setPrefWidth(110);
        vBox3.getChildren().addAll(label2, genderComboBox);
        vBox3.setPadding(new Insets(4,4,0,4));

        hBox.getChildren().addAll(vBox1, vBox2, vBox3);

        HBox hBox1 = new HBox();
        hBox1.setPrefWidth(820);
        hBox1.setPrefHeight(75);
        VBox vBox4 = new VBox();
        vBox4.setPrefHeight(75);
        vBox4.setPrefWidth(410);
        Label label3 = new Label("Email");
        label3.setFont(Font.font("System", FontWeight.NORMAL, 20));
        emailTextField.setPrefWidth(410);
        emailTextField.setPrefHeight(49);
        vBox4.getChildren().addAll(label3, emailTextField);
        vBox4.setPadding(new Insets(0,15,0,0));

        VBox vBox5 = new VBox();
        vBox5.setPrefHeight(75);
        vBox5.setPrefWidth(410);
        Label label4 = new Label("Mật khẩu");
        label4.setFont(Font.font("System", FontWeight.NORMAL, 20));
        passwordTextField.setPrefWidth(410);
        passwordTextField.setPrefHeight(49);
        vBox5.getChildren().addAll(label4, passwordTextField);
        vBox5.setPadding(new Insets(0,0,0,15));
        hBox1.getChildren().addAll(vBox4, vBox5);

        HBox hBox2 = new HBox();
        hBox2.setPrefWidth(820);
        hBox2.setPrefHeight(75);
        VBox vBox6 = new VBox();
        vBox6.setPrefWidth(410);
        vBox6.setPrefHeight(75);
        Label label5 = new Label("Số điện thoại");
        label5.setFont(Font.font("System", FontWeight.NORMAL, 20));
        phoneTextField.setPrefWidth(410);
        phoneTextField.setPrefHeight(62);
        vBox6.getChildren().addAll(label5, phoneTextField);
        vBox6.setPadding(new Insets(0,15,0,0));


        Label label6 = new Label("Địa chỉ");
        label6.setFont(Font.font("System", FontWeight.NORMAL, 20));
        addressComboBox.setPrefWidth(213);
        addressComboBox.setPrefHeight(46);
        VBox vBox7 = new VBox(label6, addressComboBox);
        vBox7.setPrefHeight(75);
        vBox7.setPrefWidth(205);
        vBox7.setPadding(new Insets(0,0,0,15));

        Label label7 = new Label("Trạng thái hoạt động");
        label7.setFont(Font.font("System", FontWeight.NORMAL, 20));
        statusComboBox.setPrefHeight(46);
        statusComboBox.setPrefWidth(213);
        VBox vBox8 = new VBox(label7, statusComboBox);
        vBox8.setPrefWidth(205);
        vBox8.setPrefHeight(75);
        vBox8.setPadding(new Insets(0,0,0,15));

        hBox2.getChildren().addAll(vBox6, vBox7, vBox8);

        Label label8 = new Label("Lương");
        label8.setFont(Font.font("System", FontWeight.NORMAL, 20));
        salaryTextField.setPrefHeight(59);
        salaryTextField.setPrefWidth(410);
        VBox vBox9 = new VBox(label8, salaryTextField);
        vBox9.setPrefHeight(75);
        vBox9.setPrefWidth(410);
        vBox9.setPadding(new Insets(0,15,0,0));

        Label label9 = new Label("Bằng cấp");
        label9.setFont(Font.font("System", FontWeight.NORMAL, 20));
        certificatesTextField.setPrefWidth(410);
        certificatesTextField.setPrefHeight(59);
        VBox vBox10 = new VBox(label9, certificatesTextField);
        vBox10.setPrefHeight(75);
        vBox10.setPrefWidth(410);
        vBox10.setPadding(new Insets(0,0,0,15));
        HBox hBox3 = new HBox(vBox9, vBox10);
        hBox3.setPrefWidth(820);
        hBox3.setPrefHeight(75);

        cancelLabel.setTextFill(Color.WHITE);
        cancelLabel.setFont(Font.font("System", FontWeight.NORMAL, 20));

        confirmLabel.setTextFill(Color.WHITE);
        confirmLabel.setFont(Font.font("System", FontWeight.NORMAL, 20));

        confirmHbox.setAlignment(Pos.CENTER);
        confirmHbox.setPrefWidth(120);
        confirmHbox.setPrefHeight(44);
        confirmHbox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 8");
        confirmHbox.setCursor(Cursor.HAND);
        confirmHbox.setId("ModalConfirm");
        cancelHbox.setAlignment(Pos.CENTER);
        cancelHbox.setPrefHeight(44);
        cancelHbox.setPrefWidth(120);
        cancelHbox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 8");
        cancelHbox.setCursor(Cursor.HAND);
        cancelHbox.setOnMouseClicked(event -> {
            stage.close();
        });

        HBox hBox5 = new HBox(cancelHbox, confirmHbox);
        hBox5.setPrefWidth(820);
        hBox5.setPrefHeight(75);
        hBox5.setAlignment(Pos.BOTTOM_RIGHT);
        hBox5.setFillHeight(false);
        hBox5.setSpacing(30);
        root.getChildren().addAll(hBox, hBox1, hBox2, hBox3, hBox5);
        clearModalStage();
    }

    public void setUpComboBox(){
        genderComboBox.getItems().add("Nam");
        genderComboBox.getItems().add("Nữ");
        for(int i = 1; i < 100; i++){
            ageComboBox.getItems().add(i);
        }
        statusComboBox.getItems().add("Đang hoạt động");
        statusComboBox.getItems().add("Dừng hoạt động");
        List<String> cityLst = Arrays.asList(
                "Hà Nội", "Hải Phòng", "Hồ Chí Minh", "Đà Nẵng", "Cần Thơ",
                "Bắc Ninh", "Hà Nam", "Hải Dương", "Hưng Yên", "Nam Định",
                "Ninh Bình", "Thái Bình", "Vĩnh Phúc", "Quảng Ninh", "Lào Cai",
                "Yên Bái", "Điện Biên", "Lai Châu", "Sơn La", "Hòa Bình",
                "Thái Nguyên", "Tuyên Quang", "Phú Thọ", "Bắc Giang", "Bắc Kạn",
                "Cao Bằng", "Lạng Sơn", "Hà Giang", "Thanh Hóa", "Nghệ An",
                "Hà Tĩnh", "Quảng Bình", "Quảng Trị", "Thừa Thiên Huế", "Quảng Nam",
                "Quảng Ngãi", "Bình Định", "Phú Yên", "Khánh Hòa", "Ninh Thuận",
                "Bình Thuận", "Kon Tum", "Gia Lai", "Đắk Lắk", "Đắk Nông",
                "Lâm Đồng", "Bình Phước", "Tây Ninh", "Bình Dương", "Đồng Nai",
                "Bà Rịa - Vũng Tàu", "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh",
                "Vĩnh Long", "Đồng Tháp", "An Giang", "Kiên Giang", "Hậu Giang",
                "Sóc Trăng", "Bạc Liêu", "Cà Mau"
        );
        addressComboBox.getItems().addAll(cityLst);
    }

    public void setUpModalStage(Account employee){
        createModalStage();
        nameTextField.setText(employee.getName());
        ageComboBox.setValue(employee.getAge());
        genderComboBox.setValue(employee.getGender());
        emailTextField.setText(employee.getEmail());
        passwordTextField.setText(employee.getPassword());
        phoneTextField.setText(employee.getPhone());
        addressComboBox.setValue(employee.getAddress());
        salaryTextField.setText(Integer.toString(employee.getSalary()));
        certificatesTextField.setText(employee.getCertificates());
        statusComboBox.setValue(employee.getStatus());
    }

    public void clearModalStage(){
        nameTextField.setText("");
        ageComboBox.setValue(0);
        genderComboBox.setValue("");
        emailTextField.setText("");
        passwordTextField.setText("");
        phoneTextField.setText("");
        addressComboBox.setValue("");
        salaryTextField.setText("");
        certificatesTextField.setText("");
        statusComboBox.setValue("");
    }

    public void refreshEmployeeTable(ObservableList<Account> EmployeeList){
        employeeTable.setItems(EmployeeList);
    }

    public void loadEmployeeTable(ObservableList<Account> EmployeeList){
        employeeTable.setStyle("-fx-background-color: #F5F5F5;");
        employeeTable.setPrefHeight(594);
        employeeTable.setPrefWidth(954);
        employeeTable.setLayoutX(23);
        employeeTable.setLayoutY(107);
        employeeTable.setFixedCellSize(45);

        employeeTable.getColumns().addAll(idCol, nameCol, ageCol, genderCol, emailCol, passCol, phoneCol, addressCol, salaryCol, certificatesCol, statusCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        certificatesCol.setCellValueFactory(new PropertyValueFactory<>("certificates"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        employeeTable.setItems(EmployeeList);
    }

    public void loadBillTable(ObservableList<Bill> BillList){
        dateCollum.setCellValueFactory(new PropertyValueFactory<>("time"));
        priceCollum.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        statusCollum.setCellValueFactory(new PropertyValueFactory<>("status"));
        billTable.setItems(BillList);
        updateBillHbox.setId("UpdateBill");

        // Tạo button cập nhật
        Callback<TableColumn<Bill, String>, TableCell<Bill, String>> cellFoctory = (TableColumn<Bill, String> param) -> {
            final TableCell<Bill, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Bill bill = getTableView().getItems().get(getIndex());
                        if (!bill.getStatus().equals("Đã thanh toán")){
                            Label lb = new Label("Cập nhật");
                            lb.setTextFill(Color.WHITE);

                            HBox hbox = new HBox(lb);
                            hbox.setMaxWidth(100);
                            hbox.setStyle("-fx-alignment:center; -fx-cursor: hand; -fx-background-color:  #30475E; -fx-background-radius: 8;");

                            hbox.setOnMouseClicked((MouseEvent event) -> {
                                updateBillHbox.fireEvent(event);
                            });
                            hbox.setOnMouseEntered((MouseEvent event) -> {
                                HBox myHbox = (HBox) event.getSource();
                                myHbox.setEffect(new ColorAdjust(0,0,0,-0.2));
                            });
                            hbox.setOnMouseExited((MouseEvent event) -> {
                                HBox myHbox = (HBox) event.getSource();
                                myHbox.setEffect(new ColorAdjust(0,0,0,0));
                            });
                            setGraphic(hbox);
                            setText(null);
                        } else{
                            FontIcon font = new FontIcon("fas-check-circle");
                            font.setIconSize(12);
                            HBox hb = new HBox(font);
                            hb.setMaxWidth(100);
                            hb.setStyle("-fx-alignment:center;-fx-background-color:  #FFFFFF;");
                            setGraphic(hb);
                            setText(null);
                        }
                    }
                }
            };
            return cell;
        };
        updateCollum.setCellFactory(cellFoctory);
    }

    public void informationAlert(String alertString){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(alertString);
        alert.showAndWait();
    }

    public void addActionListener(EventHandler<Event> manageEmployeeController){
        editHbox.setOnMouseClicked(manageEmployeeController);
        deleteHbox.setOnMouseClicked(manageEmployeeController);
        addHbox.setOnMouseClicked(manageEmployeeController);
        employeeTable.setOnMouseClicked(manageEmployeeController);
        confirmHbox.setOnMouseClicked(manageEmployeeController);
        updateBillHbox.setOnMouseClicked(manageEmployeeController);
    }
}
