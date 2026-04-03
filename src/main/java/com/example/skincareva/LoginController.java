package com.example.skincareva;

import javafx.fxml.FXML;
import javafx.scene.control.*;
public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin(){
        String email = emailField.getText();
        String pass = passwordField.getText();

        if(email.isEmpty() || pass.isEmpty()){
            errorLabel.setText("Заполните все поля!");
            return;
        }

        try{
            boolean success = SupabaseService.signIn(email, pass);
            if(success){
                Main.setScene("main.fxml");
            } else{
                errorLabel.setText("Неверный логин или пароль");
            }
        } catch (Exception e){
            errorLabel.setText("Ошибка сети: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() throws Exception{
        Main.setScene("start.fxml");
    }
}
