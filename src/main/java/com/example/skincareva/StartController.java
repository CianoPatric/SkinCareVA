package com.example.skincareva;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class StartController {
    @FXML
    private void goToAuth() throws Exception{
        Main.setScene("login.fxml");
    }

    @FXML
    private void goToRegister() throws Exception{
        Main.setScene("register.fxml");
    }

    @FXML
    private void exitApp(){
        Platform.exit();
    }
}
