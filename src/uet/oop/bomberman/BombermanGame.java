package uet.oop.bomberman;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.ResourceCollection;
import uet.oop.bomberman.entities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

/**
 * Contains the main method to launch the game.
 */
public class BombermanGame extends  Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Bomberman GGAA");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //public void startgame(){
    //}
}