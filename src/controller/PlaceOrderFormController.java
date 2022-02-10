
package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.ManageOrderBO;
import bo.custom.PlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDetailDTO;
import view.tm.CartTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceOrderFormController {

    private final PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.PLACE_ORDER);
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.ITEM);
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.CUSTOMER);
    private final ManageOrderBO manageOrderBO = (ManageOrderBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.MANAGE_ORDER);

    public AnchorPane pageContext;

    public Label lblDate;
    public Label lblTime;

    public JFXTextField txtCustId;
    public JFXTextField txtCustName;
    public JFXTextField txtCustAddress;

    public JFXTextField txtItemCode;
    public JFXTextField txtItemDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQty;
    public JFXButton btnClear;
    public JFXButton btnAddToCart;
    public Label lblTotal;
    public Label lblOrderId;
    public TableView<CartTM> tblCart;
    public TableColumn colItemCode;
    public TableColumn colDesc;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colPrice;
    public JFXComboBox cmbCustomer;
    public JFXComboBox cmbItem;
    public JFXButton btnPlaceOrder;
    public Label lblItemCount;
    public JFXTextField txtOrderId;

    private List<String> customerList;
    private List<String> itemList;
    private double lastDiscount;
    ObservableList<CartTM> obList = FXCollections.observableArrayList();
    int selectedRow = -1;

    public void initialize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            customerList = customerBO.getAllCustomerIds();
            cmbCustomer.getItems().addAll(customerList);
            itemList = itemBO.getAllCustomerIds();
            cmbItem.getItems().addAll(itemList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData((String) newValue);
        });
        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemData((String) newValue);
        });
        if (obList.isEmpty()) {
            lblTotal.setText("Rs. 0.0");
            lblItemCount.setText("Item Count : 0");
        }
        loadDateAndTime();
        //setOrderId();

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
        });
    }

   /* private void setOrderId() {
        try {
            lblOrderId.setText(placeOrderBO.getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

  private void setItemData(String itemCode){
        try {
            ItemDTO itemDTO =itemBO.search(itemCode);
            if (itemDTO != null) {
                txtItemCode.setText(itemDTO.getCode());
                txtItemDesc.setText(itemDTO.getDescription());
                txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
                lastDiscount =itemDTO.getDiscountPercent();
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result Set");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void setCustomerData(String customerId){
        try {
            CustomerDTO customerDTO =customerBO.search(customerId);
            if (customerDTO != null) {
                txtCustId.setText(customerDTO.getId());
                txtCustName.setText(customerDTO.getName());
                txtCustAddress.setText(customerDTO.getAddress());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result Set");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date()));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.format(DateTimeFormatter.ofPattern("HH : mm : ss a")));//ISO_LOCAL_TIME.substring(0,8)
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        try{
        if (isNotFilled()) {
            new Alert(Alert.AlertType.WARNING, "Fields not filled...").show();
        } else if (Integer.parseInt(txtQtyOnHand.getText()) < Integer.parseInt(txtQty.getText())) {
            new Alert(Alert.AlertType.WARNING, "Quantity not sufficient...").show();
        } else {
            CartTM cartTM = new CartTM(
                    txtItemCode.getText(),
                    txtItemDesc.getText(),
                    Integer.parseInt(txtQty.getText()),
                    roundDouble(Double.parseDouble(txtUnitPrice.getText())),
                    roundDouble(lastDiscount * Double.parseDouble(txtQty.getText()) * Double.parseDouble(txtUnitPrice.getText())),
                    roundDouble((1 - lastDiscount) * Double.parseDouble(txtQty.getText()) * Double.parseDouble(txtUnitPrice.getText()))
            );
            lastDiscount = 0;
            if (isExists(cartTM)) {
                int index = 0;
                for (int i = 0; i<obList.size(); i++) {
                    if (cartTM.getCode().equals(obList.get(i).getCode())) {
                        index=i;
                    }
                }
                CartTM temp = obList.get(index);
                CartTM newCartTM = new CartTM(
                        temp.getCode(),
                        temp.getDescription(),
                        temp.getQuantity() + cartTM.getQuantity(),
                        roundDouble(temp.getUnitPrice()),
                        roundDouble(temp.getDiscount() + cartTM.getDiscount()),
                        roundDouble(temp.getPrice() + cartTM.getPrice())
                );
                obList.remove(index);
                obList.add(newCartTM);
            } else {
                obList.add(cartTM);
            }
            deductItemQuantity(cartTM.getCode(),cartTM.getQuantity());
            tblCart.setItems(obList);
            calculateTotal();
            setItemCount();
        }
        }catch(NumberFormatException e){
        new Alert(Alert.AlertType.WARNING,"Please enter number for quantity").show();
        }
    }

    private void setItemCount() {
        lblItemCount.setText("Item Count : "+obList.size());
    }

    private void deductItemQuantity(String code, int quantity){
        try {
            txtQtyOnHand.setText(String.valueOf(Integer.parseInt(txtQtyOnHand.getText())-quantity));
           ItemDTO itemDTO =itemBO.search(code);
            itemDTO.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
           try{
               itemBO.updateItem(itemDTO);
           }catch (Exception e){
               new Alert(Alert.AlertType.WARNING, "Quantity not updated").show();
           }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < obList.size(); i++) {
            total += obList.get(i).getPrice();
        }
        lblTotal.setText("Rs. " + roundDouble(total));
    }

    private boolean isExists(CartTM cartTM) {
        for (int i = 0; i < obList.size(); i++) {
            if (cartTM.getCode().equals(obList.get(i).getCode())) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotFilled() {
        if (
                txtCustId.getText().equals("") ||
                txtCustName.getText().equals("") ||
                txtCustAddress.getText().equals("") ||
                txtItemCode.getText().equals("") ||
                txtItemDesc.getText().equals("") ||
                txtUnitPrice.getText().equals("") ||
                txtQtyOnHand.getText().equals("") ||
                txtQty.getText().equals("")
        ) {
            return true;
        }
        return false;
    }

    public void clearItemOnAction(ActionEvent actionEvent) {
        //System.out.println(selectedRow);
        if (selectedRow >= 0) {
            replaceItemQuantity(obList.get(selectedRow));
            obList.remove(selectedRow);
            calculateTotal();
            setItemCount();
            tblCart.refresh();
            //selectedRow = -1;
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a row").show();
        }
    }

    private void replaceItemQuantity(CartTM cartTM) {
        try {
            ItemDTO itemDTO =itemBO.search(cartTM.getCode());
            itemDTO.setQtyOnHand(itemDTO.getQtyOnHand()+cartTM.getQuantity());
            try{
                itemBO.updateItem(itemDTO);
                if(txtItemCode.getText().equals(cartTM.getCode())){
                    itemDTO =itemBO.search(cartTM.getCode());
                    txtQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.WARNING, "Quantity not updated").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static double roundDouble(double d) {
        BigDecimal bigDecimal=new BigDecimal(Double.toString(d));
        bigDecimal=bigDecimal.setScale(2,RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> detailList=new ArrayList<>();

        //System.out.println(lblTotal.getText().split(" ")[1]);
        for(CartTM temp:obList){
            detailList.add(new OrderDetailDTO(
                    temp.getCode(),
//                    lblOrderId.getText(),
                    txtOrderId.getText(),
                    temp.getQuantity(),
                    roundDouble(temp.getPrice()),
                    roundDouble(temp.getDiscount())
                )
            );
        }
        OrderDTO orderDTO =new OrderDTO(
//                        lblOrderId.getText(),
                        txtOrderId.getText(),
                        txtCustId.getText(),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        new SimpleDateFormat("hh:mm:ss a").format(new Date()),
                        Double.parseDouble(lblTotal.getText().split(" ")[1]),
                        detailList
                    );

        try{
            placeOrderBO.addOrder(orderDTO);
            new Alert(Alert.AlertType.CONFIRMATION, "Orders placed...").show();
            //setOrderId();
            refreshPage();
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING, "Try Again...").show();
        }
    }

    private void refreshPage(){
        try {
            pageContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

