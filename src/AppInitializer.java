import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import db.FactoryConfiguration;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
        FactoryConfiguration.getInstance().getSession().close();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene=new Scene(FXMLLoader.load(getClass().getResource("view/LoginForm.fxml")));
        primaryStage.setTitle("Super Market & Grocery - Version 1.0.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
