package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class mainUI extends Application {

    double x, y = 0;
    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("prism.lcdtext", "false");
        Font.loadFont(getClass().getResourceAsStream("/resources/NanumGothicExtraBold.ttf"), 30);
        Font.loadFont(getClass().getResourceAsStream("/resources/NanumGothic.ttf"), 30);
        Parent root = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        stage.setTitle("랜덤 주사위");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/dice.png")));
        stage.initStyle(StageStyle.DECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setScene(new Scene(root));
        stage.show();
    }
    //Main
    public static void main(String[] args) { launch(args); }
}