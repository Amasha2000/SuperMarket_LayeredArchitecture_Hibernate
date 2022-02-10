package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import dto.CustomerDTO;
import util.Validation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageCustomerFormController {

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.CUSTOMER);

    public AnchorPane pageContext;

    public TableView<CustomerDTO> tblCustomerList;
    public TableColumn colCustomerId;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;

    public JFXTextField customerSearchBar;
    public JFXTextField txtCustId1;
    public JFXTextField txtTitle1;
    public JFXTextField txtName1;
    public JFXTextField txtAddress1;
    public JFXTextField txtCity1;
    public JFXTextField txtProvince1;
    public JFXTextField txtPostalCode1;
    public JFXTextField txtCustId2;
    public JFXTextField txtTitle2;
    public JFXTextField txtName2;
    public JFXTextField txtAddress2;
    public JFXTextField txtCity2;
    public JFXTextField txtProvince2;
    public JFXTextField txtPostalCode2;

    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    ObservableList<CustomerDTO> obList= FXCollections.observableArrayList();

    LinkedHashMap<TextField,Pattern> map1=new LinkedHashMap<>();
    LinkedHashMap<TextField,Pattern> map2=new LinkedHashMap<>();
    Pattern titlePattern=Pattern.compile("^[[A-z]+(?[\\s.]+[A-z]+)*]{2,5}$");
    Pattern namePattern=Pattern.compile("^[[A-z]+(?[\\s.]+[A-z]+)*]{7,30}$");
    Pattern addressPattern=Pattern.compile("^[[0-9A-z'\\.\\\\\\-\\s\\,]+]{7,30}$");
    Pattern cityPattern= Pattern.compile("^([A-z][\\s]?){5,20}$");
    Pattern provincePattern=Pattern.compile("^([A-z][\\s]?){5,20}$");
    Pattern postalCodePattern=Pattern.compile("^[0-9]{5}$");


    public void initialize(){
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        validateInit();
        
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        try {
            setCustomerId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblCustomerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
                setCustomerData(newValue);
            }
        );

        try {
            viewAllOnAction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        FilteredList<CustomerDTO> filteredList=new FilteredList<>(obList, b->true);
        customerSearchBar.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(customerDTO ->{
                return filterSearchCustomers(newValue, customerDTO);
            });
        });
        SortedList<CustomerDTO> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblCustomerList.comparatorProperty());
        tblCustomerList.setItems(sortedList);

    }

    private void validateInit() {
        map1.put(txtTitle1,titlePattern);
        map1.put(txtName1,namePattern);
        map1.put(txtAddress1,addressPattern);
        map1.put(txtCity1,cityPattern);
        map1.put(txtProvince1,provincePattern);
        map1.put(txtPostalCode1,postalCodePattern);
        map2.put(txtTitle2,titlePattern);
        map2.put(txtName2,namePattern);
        map2.put(txtAddress2,addressPattern);
        map2.put(txtCity2,cityPattern);
        map2.put(txtProvince2,provincePattern);
        map2.put(txtPostalCode2,postalCodePattern);

    }

    private boolean filterSearchCustomers(String newValue, CustomerDTO customerDTO) {
        if(newValue==null || newValue.isEmpty()) {
            return true;
        }
        String search=newValue.toLowerCase();
        if(customerDTO.getId().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customerDTO.getTitle().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customerDTO.getName().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customerDTO.getAddress().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customerDTO.getCity().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customerDTO.getProvince().toLowerCase().indexOf(search)>=0){
            return true;
        }else if(customerDTO.getPostalCode().toLowerCase().indexOf(search)>=0){
            return true;
        }else{
            return false;
        }
    }

    private void setCustomerId() throws SQLException, ClassNotFoundException {
        txtCustId1.setText(customerBO.getCustomerId());
    }
    private void clearAllFields() {
        txtCustId1.clear();
        txtTitle1.clear();
        txtName1.clear();
        txtAddress1.clear();
        txtCity1.clear();
        txtProvince1.clear();
        txtPostalCode1.clear();
        txtCustId2.clear();
        txtTitle2.clear();
        txtName2.clear();
        txtAddress2.clear();
        txtCity2.clear();
        txtProvince2.clear();
        txtPostalCode2.clear();
    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(isFilled(1)) {
            CustomerDTO customerDTO = new CustomerDTO(
                    txtCustId1.getText(),
                    txtTitle1.getText(),
                    txtName1.getText(),
                    txtAddress1.getText(),
                    txtCity1.getText(),
                    txtProvince1.getText(),
                    txtPostalCode1.getText()
            );
            try{
                customerBO.addCustomer(customerDTO);
                new Alert(Alert.AlertType.CONFIRMATION, "Saved...").show();
                    clearAllFields();
                    setCustomerId();
                    refreshPage();
                } catch(Exception e){
                    new Alert(Alert.AlertType.WARNING, "Try again...").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Fields not filled...").show();
            }
        }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(isFilled(2)){
            CustomerDTO customerDTO =new CustomerDTO(
                    txtCustId2.getText(),
                    txtTitle2.getText(),
                    txtName2.getText(),
                    txtAddress2.getText(),
                    txtCity2.getText(),
                    txtProvince2.getText(),
                    txtPostalCode2.getText()
            );

            try{
                customerBO.updateCustomer(customerDTO);
                new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
                clearAllFields();
                setCustomerId();
                refreshPage();
            }catch(Exception e){
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }
    public void deleteCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(isFilled(2)){
            String customerId=txtCustId2.getText();
            try{
                customerBO.deleteCustomer(customerId);
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                clearAllFields();
                setCustomerId();
                refreshPage();
            }catch(Exception e){
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }

    private boolean isFilled(int i) {
        if(i==1 && !txtCustId1.getText().equals("") && !txtTitle1.getText().equals("") && !txtName1.getText().equals("") && !txtAddress1.getText().equals("") && !txtCity1.getText().equals("") && !txtProvince1.getText().equals("") && !txtPostalCode1.getText().equals("")){
            return true;
        }else if(i==2 && !txtCustId2.getText().equals("") && !txtTitle2.getText().equals("") && !txtName2.getText().equals("") && !txtAddress2.getText().equals("") && !txtCity2.getText().equals("") && !txtProvince2.getText().equals("") && !txtPostalCode2.getText().equals("")){
            return true;
        }
        return false;
    }

    public void viewAllOnAction() throws SQLException, ClassNotFoundException {
        obList.clear();
        List<CustomerDTO> all =customerBO.getAllCustomers();
        obList.addAll(all);
        tblCustomerList.setItems(obList);
    }

    private void refreshPage() throws IOException {
        pageContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageCustomerForm.fxml")));
    }

    public void selectCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId=txtCustId2.getText();
        CustomerDTO customerDTO =customerBO.search(customerId);
        if (customerDTO !=null) {
            setCustomerData(customerDTO);
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }
    }
    private void setCustomerData(CustomerDTO c) {
        try {
            txtCustId2.setText(c.getId());
            txtTitle2.setText(c.getTitle());
            txtName2.setText(c.getName());
            txtAddress2.setText(c.getAddress());
            txtCity2.setText(c.getCity());
            txtProvince2.setText(c.getProvince());
            txtPostalCode2.setText(c.getPostalCode());
        }catch(NullPointerException exception){

        }
    }

    public void keyReleased1(KeyEvent keyEvent) {
        Object response = Validation.validate(map1,btnAdd);
        if (response instanceof TextField) {
            TextField errorText = (TextField) response;
            if (keyEvent.getCode() == KeyCode.ENTER) {
                errorText.requestFocus();
            }
        } else if (response instanceof Boolean) {
            btnAdd.setDisable(false);
        }
    }

    public void keyReleased2(KeyEvent keyEvent) {
        Object response = Validation.validate(map2, btnUpdate);
        if (response instanceof TextField) {
            TextField errorText = (TextField) response;
            if (keyEvent.getCode() == KeyCode.ENTER) {
                errorText.requestFocus();
            }
            btnDelete.setDisable(true);
        } else if (response instanceof Boolean) {
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
