package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class AdminDashboardFormController {
    public AnchorPane adminDashboardContext;
    public AnchorPane adminPerformanceContext;
    public JFXButton logoutButton;

    public void initialize() throws IOException {
        adminPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageItemForm.fxml")));
    }

    public void manageItemsOnAction(ActionEvent actionEvent) throws IOException {
        adminPerformanceContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageItemForm.fxml")));
    }

    public void viewReportsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminPerformanceContext.getChildren().clear();
        adminPerformanceContext.getChildren().add(load);

    }

    public void backToLoginForm(ActionEvent actionEvent) throws IOException {
        adminDashboardContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
    }
}

