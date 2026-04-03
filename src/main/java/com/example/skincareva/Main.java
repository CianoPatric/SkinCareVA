package com.example.skincareva;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
    public static Stage parentStage;
    @Override
    public void start(Stage stage){
        try{
            parentStage = stage;
            setScene("start.fxml");
            stage.setTitle("SkinCare Assistant");
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setScene(String fxml) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        if(loader.getLocation() == null){
            System.err.println("Ошибка: Не найден файл " + fxml + " по пути " + Main.class.getResource(""));
            return;
        }
        Scene scene = new Scene(loader.load(), 900, 700);
        parentStage.setScene(scene);
        parentStage.centerOnScreen();
    }

    public static void main(String[] args){
        launch(args);
    }
}
