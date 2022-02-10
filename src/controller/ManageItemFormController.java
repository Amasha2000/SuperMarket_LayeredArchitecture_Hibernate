
package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
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
import dto.ItemDTO;
import util.Validation;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageItemFormController {
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.ITEM);

    public AnchorPane pageContext;

    public TableView<ItemDTO> tblItemList;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;

    public JFXTextField itemSearchBar;
    public JFXTextField txtItemCode1;
    public JFXTextField txtDescription1;
    public JFXTextField txtPackSize1;
    public JFXTextField txtQtyOnHand1;
    public JFXTextField txtUnitPrice1;
    public JFXTextField txtDiscount1;
    public JFXTextField txtItemCode2;
    public JFXTextField txtDescription2;
    public JFXTextField txtPackSize2;
    public JFXTextField txtQtyOnHand2;
    public JFXTextField txtUnitPrice2;
    public JFXTextField txtDiscount2;

    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;



    ObservableList<ItemDTO> obList= FXCollections.observableArrayList();
    

    LinkedHashMap<TextField, Pattern> map1=new LinkedHashMap<>();
    LinkedHashMap<TextField,Pattern> map2=new LinkedHashMap<>();

    Pattern descriptionPattern=Pattern.compile("^[[A-z]+(?[\\\\s.]+[A-z]+)*]{3,50}$");
    Pattern packSizePattern=Pattern.compile("^([A-z][\\s]?){5,20}$");
    Pattern qtyOnHandPattern=Pattern.compile("^[1-9]([0-9]{0,4})$");
    Pattern unitPricePattern=Pattern.compile("^(0(?!\\.00)|[1-9]\\d{0,6})\\.\\d{2}$");
    Pattern discountPattern=Pattern.compile("^(0(?!\\.00)|[1-9]\\d{0,6})\\.\\d{2}$");

    public void initialize(){
        
        validateInit();
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));

        try {
            setItemCode();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblItemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                setItemData(newValue);
            }
        );

        try {
            viewAllOnAction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        FilteredList<ItemDTO> filteredList=new FilteredList<>(obList, b->true);
        itemSearchBar.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(itemDTO ->{
                return filterSearchItems(newValue, itemDTO);
            });
        });
        SortedList<ItemDTO> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblItemList.comparatorProperty());
        tblItemList.setItems(sortedList);
    }

    private void validateInit() {
        map1.put(txtDescription1,descriptionPattern);
        map1.put(txtPackSize1,packSizePattern);
        map1.put(txtQtyOnHand1,qtyOnHandPattern);
        map1.put(txtUnitPrice1,unitPricePattern);
        map1.put(txtDiscount1,discountPattern);
        map2.put(txtDescription2,descriptionPattern);
        map2.put(txtPackSize2,packSizePattern);
        map2.put(txtQtyOnHand2,qtyOnHandPattern);
        map2.put(txtUnitPrice2,unitPricePattern);
        map2.put(txtDiscount2,discountPattern);

    }

    private boolean filterSearchItems(String newValue, ItemDTO itemDTO) {
        if(newValue==null || newValue.isEmpty()) {
            return true;
        }
        String search=newValue.toLowerCase();
        if(itemDTO.getCode().toLowerCase().contains(search)){
            return true;
        }else if(itemDTO.getDescription().toLowerCase().contains(search)){
            return true;
        }else if(itemDTO.getPackSize().toLowerCase().contains(search)){
            return true;
        }else if(String.valueOf(itemDTO.getQtyOnHand()).toLowerCase().contains(search)){
            return true;
        }else if(String.valueOf(itemDTO.getUnitPrice()).toLowerCase().contains(search)) {
            return true;
        }else if(String.valueOf(itemDTO.getDiscountPercent()).toLowerCase().contains(search)) {
            return true;
        }else{
            return false;
        }
    }


    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(isFilled(1)){
            ItemDTO itemDTO =new ItemDTO(
                    txtItemCode1.getText(),
                    txtDescription1.getText(),
                    txtPackSize1.getText(),
                    Integer.parseInt(txtQtyOnHand1.getText()),
                    roundDouble(Double.parseDouble(txtUnitPrice1.getText())),
                    roundDouble(Double.parseDouble(txtDiscount1.getText()))
            );
            try{
                itemBO.addItem(itemDTO);
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
                clearAllFields();
                setItemCode();
                refreshPage();
            }catch(Exception e){
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
       }
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(isFilled(2)){
            ItemDTO itemDTO =new ItemDTO(
                txtItemCode2.getText(),
                txtDescription2.getText(),
                txtPackSize2.getText(),
                Integer.parseInt(txtQtyOnHand2.getText()),
                roundDouble(Double.parseDouble(txtUnitPrice2.getText())),
                roundDouble(Double.parseDouble(txtDiscount2.getText()))
            );
            try{
                itemBO.updateItem(itemDTO);
                new Alert(Alert.AlertType.CONFIRMATION,"Updated...").show();
                clearAllFields();
                setItemCode();
                refreshPage();
            }catch(Exception e){
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }
    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(isFilled(2)){
            String itemCode=txtItemCode2.getText();
            try{
                itemBO.deleteItem(itemCode);
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                clearAllFields();
                setItemCode();
                refreshPage();
            }catch(Exception e){
                new Alert(Alert.AlertType.WARNING,"Try again...").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fields not filled...").show();
        }

    }

    private void refreshPage() throws IOException {
        pageContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageItemForm.fxml")));
    }

    private boolean isFilled(int i) {
        if(i==1 && !txtItemCode1.getText().equals("") && !txtDescription1.getText().equals("") && !txtPackSize1.getText().equals("") && !txtQtyOnHand1.getText().equals("") && !txtUnitPrice1.getText().equals("") && !txtDiscount1.getText().equals("")){
            return true;
        }else if(i==2 && !txtItemCode2.getText().equals("") && !txtDescription2.getText().equals("") && !txtPackSize2.getText().equals("") && !txtQtyOnHand2.getText().equals("") && !txtUnitPrice2.getText().equals("") && !txtDiscount2.getText().equals("")){
            return true;
        }
        return false;
    }

    private void clearAllFields() {
        txtItemCode1.clear();
        txtDescription1.clear();
        txtPackSize1.clear();
        txtQtyOnHand1.clear();
        txtUnitPrice1.clear();
        txtDiscount1.clear();
        txtItemCode2.clear();
        txtDescription2.clear();
        txtPackSize2.clear();
        txtQtyOnHand2.clear();
        txtUnitPrice2.clear();
        txtDiscount2.clear();
    }

    private void setItemCode() throws SQLException, ClassNotFoundException {
        txtItemCode1.setText(itemBO.getItemCodes());
    }

    private void viewAllOnAction() throws SQLException, ClassNotFoundException {
        obList.clear();
        List<ItemDTO> itemDTOS =itemBO.getAllItems();
        obList.addAll(itemDTOS);
        tblItemList.setItems(obList);
    }

    public void selectItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode=txtItemCode2.getText();
        ItemDTO itemDTO =itemBO.search(itemCode);
        if(itemDTO !=null){
            setItemData(itemDTO);
        }else{
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }
    }

    private void setItemData(ItemDTO i) {
        txtItemCode2.setText(i.getCode());
        txtDescription2.setText(i.getDescription());
        txtPackSize2.setText(i.getPackSize());
        txtQtyOnHand2.setText(String.valueOf(i.getQtyOnHand()));
        txtUnitPrice2.setText(String.valueOf(i.getUnitPrice()));
        txtDiscount2.setText(String.valueOf(i.getDiscountPercent()));
    }

    private static double roundDouble(double d) {
        BigDecimal bigDecimal=new BigDecimal(Double.toString(d));
        bigDecimal=bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public void keyRelased1(KeyEvent keyEvent) {
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

