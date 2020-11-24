package uet.oop.bomberman;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Bomberman Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // vô hiệu hóa resize app
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
