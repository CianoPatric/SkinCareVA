package com.example.skincareva;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProfileController {
    @FXML private Label emailLabel, genderLabel, ageLabel;
    @FXML private TableView<Product> favoritesTable;
    @FXML private TableColumn<Product, String> favNameColumn;
    @FXML private TableColumn<Product, String> favTypeColumn;

    @FXML
    public void initialize() {
        emailLabel.setText(UserSession.email);
        genderLabel.setText(UserSession.gender);
        ageLabel.setText(UserSession.age + " лет");

        favNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        favTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        loadFavs();
    }

    private void loadFavs() {
        try {
            List<Product> list = SupabaseService.getFavorites();
            favoritesTable.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToMain() throws Exception {
        Main.setScene("main.fxml");
    }

    @FXML
    private void handleLogout() throws Exception {
        UserSession.clear();
        Main.setScene("start.fxml");
    }
}