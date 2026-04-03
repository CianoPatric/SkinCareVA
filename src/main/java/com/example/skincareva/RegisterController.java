package com.example.skincareva;

import javafx.fxml.FXML;
import javafx.scene.control.*;
public class RegisterController {
    @FXML private TextField emailField, ageField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private ComboBox<String> genderComboBox;

    @FXML
    public void initialize(){
        genderComboBox.getItems().addAll("Мужчина", "Женщина");
        ageField.textProperty().addListener((obs, oldV, newV) -> {
            if(!newV.matches("\\d*")){
                ageField.setText(newV.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void handleRegister(){
        String email = emailField.getText();
        String pass = passwordField.getText();
        String conf = confirmPasswordField.getText();
        String ageStr = ageField.getText();
        String gender = genderComboBox.getValue();

        if(email.isEmpty() || pass.isEmpty() || ageStr.isEmpty() || gender == null){
            showAlert("Ошибка", "Заполните все поля!");
            return;
        }

        if(!pass.equals(conf)){
            showAlert("Ошибка", "Пароли не совпадают");
            return;
        }

        try{
            int age = Integer.parseInt(ageStr);
            boolean success = SupabaseService.signUp(email, pass, age, gender);
            if(success){
                showAlert("Успех", "Вы зарегистрированы! Теперь войдите в аккаунт");
                Main.setScene("login.fxml");
            } else{
                showAlert("Ошибка", "Не удалось зарегистрироваться (Возможно, email уже зарегистрирован)");
            }
        } catch (Exception e){
            showAlert("Ошибка", "Ошибка сервера: " + e.getMessage());
        }
    }

    @FXML private void goBack() throws Exception{Main.setScene("start.fxml");}

    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.show();
    }
}
