package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class LoginFormController {
    public AnchorPane loginContext;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public JFXButton btnAdminLogin;
    public JFXButton btnCashierLogin;



    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();
    Pattern patternUserName=Pattern.compile("^(Cashier|Admin)$");
    Pattern patternPassword=Pattern.compile("^(?=.*[A-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-z\\d@$!%*#?&]{8}$");


    public void initialize(){
        validateInit();
        btnAdminLogin.setDisable(true);
        btnCashierLogin.setDisable(true);
    }

    private void validateInit() {
        map.put(txtUsername,patternUserName);
        map.put(txtPassword,patternPassword);
    }

    public void loadAdminDashboard(ActionEvent actionEvent) throws IOException {
            loginContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/AdminDashboardForm.fxml")));
        }

    public void loadCashierDashboard(ActionEvent actionEvent) throws IOException {
        loginContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/CashierDashboardForm.fxml")));
    }

    public void keyReleased(KeyEvent keyEvent) {
        Object response = validation(map,btnCashierLogin,btnAdminLogin);
        if (response instanceof TextField) {
            TextField errorText = (TextField) response;
            if (keyEvent.getCode() == KeyCode.ENTER) {
                errorText.requestFocus();
                btnCashierLogin.setDisable(true);
                btnAdminLogin.setDisable(true);
            }
        } else if (response instanceof Boolean) {
            btnCashierLogin.setDisable(false);
            btnAdminLogin.setDisable(false);
        }
    }

    private Object validation(LinkedHashMap<TextField, Pattern> map, JFXButton btnCashier, JFXButton btnManagement) {
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: red;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
                    ((AnchorPane) textFieldKey.getParent()).getChildren().get(0).setStyle("-fx-text-fill: red;"+"-fx-background-color: white;");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
            ((AnchorPane) textFieldKey.getParent()).getChildren().get(0).setStyle("-fx-text-fill: green;"+"-fx-background-color: white;");
        }
        return true;
    }
}
