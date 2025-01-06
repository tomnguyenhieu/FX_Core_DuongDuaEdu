package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.view.Scene_Login;
import com.edu.duongdua.core.view.Scene_SideBar;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class LoginController extends Controller implements EventHandler<Event>
{
    public Stage stage = new Stage();
    public Scene_Login sceneLogin = new Scene_Login();
    public LoginController(AnchorPane root, Stage stage)
    {
        sceneLogin.createScene(root);
        sceneLogin.addEventListener(this);
        this.stage = stage;
    }

    public boolean login()
    {
        String email = sceneLogin.txtEmail.getText();
        String password = sceneLogin.txtPassword.getText();
        int check = 0;
        for (Account account : accountDAO.getAllAccountByRole(1))
        {
            if (email.equals(account.getEmail()) && password.equals(account.getPassword()))
            {
                check++;
            }
        }
        if (check > 0)
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public void handle(Event event) {
        String id = ((Node) event.getSource()).getId();
        switch (id)
        {
            case "loginBtn":
                if (login())
                {
                    BorderPane root = new BorderPane();
                    Scene_SideBar sceneSideBar = new Scene_SideBar(root);
                    root.setLeft(sceneSideBar.createSideBar());

                    Scene scene = new Scene(root, 1280, 720);
                    stage.setScene(scene);
                } else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Tài khoản hoặc mật khẩu không chính xác!");
                    alert.show();
                }
            break;
        }
    }
}
