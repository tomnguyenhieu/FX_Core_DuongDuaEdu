package com.edu.duongdua.core.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;

public class Scene_Login
{
    private Button btnLogin = new Button();
    public TextField txtEmail = new TextField();
    public PasswordField txtPassword = new PasswordField();
    public void createScene(AnchorPane root)
    {
        root.setPrefSize(1280, 720);

        // Main Label
        Label mainLabel = new Label("TRUNG TÂM ANH NGỮ GERM EDUCATION");
        mainLabel.setAlignment(javafx.geometry.Pos.CENTER);
        mainLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        mainLabel.setLayoutX(838);
        mainLabel.setLayoutY(94);
        mainLabel.setPrefSize(370, 130);
        mainLabel.setWrapText(true);
        mainLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        mainLabel.setFont(new Font("Roboto Bold", 28));

        ImageView imageView = new ImageView(new Image(getClass().getResource("/LoginImage.png").toExternalForm()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);
        imageView.setLayoutX(130);
        imageView.setLayoutY(64);

        // Inner AnchorPane
        AnchorPane innerPane = new AnchorPane();
        innerPane.setLayoutX(824);
        innerPane.setLayoutY(247);
        innerPane.setPrefSize(400, 400);
        innerPane.setStyle("-fx-background-color: #30475E; -fx-background-radius: 15;");

        // Welcome Label
        Label welcomeLabel = new Label("WELLCOME!");
        welcomeLabel.setAlignment(javafx.geometry.Pos.CENTER);
        welcomeLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        welcomeLabel.setLayoutX(54);
        welcomeLabel.setLayoutY(37);
        welcomeLabel.setPrefSize(298, 18);
        welcomeLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        welcomeLabel.setFont(new Font("Roboto Bold", 22));

        // Email TextField

        txtEmail.setLayoutX(39);
        txtEmail.setLayoutY(103);
        txtEmail.setPrefSize(320, 68);
        txtEmail.setPromptText("Email");
        txtEmail.setStyle("-fx-background-radius: 12;");
        txtEmail.setFont(new Font(15));
        txtEmail.setPadding(new Insets(0, 0, 0, 32));

        // Password PasswordField

        txtPassword.setLayoutX(40);
        txtPassword.setLayoutY(207);
        txtPassword.setPrefSize(320, 68);
        txtPassword.setPromptText("Mật khẩu");
        txtPassword.setStyle("-fx-background-radius: 12;");
        txtPassword.setFont(new Font(15));
        txtPassword.setPadding(new Insets(0, 0, 0, 32));

        // Login Button
        btnLogin = new Button("Đăng nhập");
        btnLogin.setId("loginBtn");
        btnLogin.setLayoutX(39);
        btnLogin.setLayoutY(303);
        btnLogin.setPrefSize(320, 60);
        btnLogin.setStyle("-fx-background-radius: 12; -fx-background-color: #F05454;");
        btnLogin.setTextFill(javafx.scene.paint.Color.WHITE);
        btnLogin.setFont(new Font("System Bold", 20));
        btnLogin.setCursor(javafx.scene.Cursor.HAND);

        // Add children to innerPane
        innerPane.getChildren().addAll(welcomeLabel, txtEmail, txtPassword, btnLogin);

        // Add children to root
        root.getChildren().addAll(imageView, mainLabel, innerPane);
    }

    public void addEventListener(EventHandler<Event> eventHandler)
    {
        btnLogin.setOnMouseClicked(eventHandler);
    }
}
