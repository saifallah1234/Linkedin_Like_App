package Elkhadema.khadema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import Elkhadema.khadema.util.Session;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Scene scene;
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/Elkhadema/khadema/style.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src//main//resources//images//elkhadema.png"));
        App.stage=stage;
        stage.show();
        stage.setOnCloseRequest(event -> {
            if (Session.getUser() == null) {
                return;
            }
            event.consume();
            logout(stage);
            Session.setUser(null);
        });

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void logout(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("your about to logout");
        alert.setContentText("do you really want to exit");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
