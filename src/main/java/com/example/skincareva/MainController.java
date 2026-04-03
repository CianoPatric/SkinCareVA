package com.example.skincareva;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML private ComboBox<String> skinTypeComboBox;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> typeColumn;
    @FXML private TableColumn<Product, String> descriptionColumn;
    @FXML private TableColumn<Product, Void> favColumn;
    @FXML private Label recommendationLabel;

    @FXML
    public void initialize(){
        skinTypeComboBox.getItems().addAll("Нормальная", "Сухая", "Жирная", "Комбинированная", "Чувствительная");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        favColumn.setCellFactory(param -> new TableCell<>() {
            private final Button favBtn = new Button("❤");
            {
                favBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #E76F51; -fx-cursor: hand; -fx-font-size: 14px;");
                favBtn.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    try {
                        SupabaseService.addFavorite(product.getId());
                        favBtn.setText("✔");
                        favBtn.setDisable(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(favBtn);
            }
        });
    }

    //Кнопка показа рекомендаций
    @FXML
    private void handleRecommendation(){
        try {
            String selectedType = skinTypeComboBox.getValue();
            if (selectedType == null) {
                showAlert("Внимание", "Выберите тип кожи!");
                return;
            }

            // Получаем данные из Supabase
            List<Product> allProducts = SupabaseService.getProducts();

            // Фильтруем список
            List<Product> filtered = allProducts.stream()
                    .filter(p -> p.getSkinType() != null && p.getSkinType().equalsIgnoreCase(selectedType))
                    .filter(p -> p.getGender() == null || p.getGender().equalsIgnoreCase("any") || (UserSession.gender != null && p.getGender().equalsIgnoreCase(UserSession.gender)))
                    .filter(p -> UserSession.age >= p.getMinAge())
                    .collect(Collectors.toList());

            productsTable.setItems(FXCollections.observableArrayList(filtered));
            recommendationLabel.setText("Найдено средств: " + filtered.size());

        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось загрузить данные: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        try {
            UserSession.clear();
            Main.setScene("start.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToProfile() throws Exception {
        Main.setScene("profile.fxml");
    }
}