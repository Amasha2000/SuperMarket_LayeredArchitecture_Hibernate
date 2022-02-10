
package controller;

import bo.BOFactory;
import bo.custom.ManageOrderBO;
import bo.custom.PlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDTO;
import entity.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import dto.OrderDetailDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageOrderFormController {
    private final PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.PLACE_ORDER);
    private final ManageOrderBO manageOrderBO=(ManageOrderBO) BOFactory.getBoFactory().getBOType(BOFactory.BOTypes.MANAGE_ORDER);


    public AnchorPane pageContext;

    public TableView tblOrderList;
    public TableColumn colOrderId1;
    public TableColumn colCustId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCost;

    public TableView tblOrderDetailList;
    public TableColumn colItemCode;
    public TableColumn colOrderId2;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colDiscount;


    public JFXButton btnDelete;


    public JFXTextField txtOrderId;
    public JFXTextField txtCustId;
    public JFXTextField txtDate;
    public JFXTextField txtTime;
    public JFXTextField txtCost;


    ObservableList<OrderDTO> obList=FXCollections.observableArrayList();
    ObservableList<OrderDetailDTO> obDetailList=FXCollections.observableArrayList();

    public void initialize(){
        tblOrderDetailList.setPlaceholder(new Label(""));

        colOrderId1.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderId2.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tblOrderList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{

                //clearAllFields();
               txtOrderId.clear();
               txtCustId.clear();
               txtDate.clear();
               txtTime.clear();
               txtCost.clear();
                setOrderData((OrderDTO) newValue);
                    try {
                        tblOrderDetailList.getItems().clear();
                        displayOrderDetails((OrderDTO) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        );

        viewAllOnAction();
    }


    private boolean isOrderFilled() {
        if(
            !txtOrderId.getText().equals("") &&
            !txtCustId.getText().equals("") &&
            !txtDate.getText().equals("") &&
            !txtTime.getText().equals("") &&
            !txtCost.getText().equals("")
        ){
            return true;
        }
        return false;
    }

    private void clearAllFields() {
        txtOrderId.setText("");
        txtCustId.setText("");
        txtDate.setText("");
        txtTime.setText("");
        txtCost.setText("");
    }

    private void displayOrderDetails(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {

           /* List<OrderDetailDTO> orderDetailDTOS = manageOrderBO.getOrderDetail(orderDTO.getOrderId());
            obDetailList.addAll(orderDetailDTOS);
            tblOrderDetailList.setItems(obDetailList);*/

    }

    private void setOrderData(OrderDTO orderDTO) {
        clearAllFields();
        txtOrderId.setText(orderDTO.getOrderId());
        txtCustId.setText(orderDTO.getCustId());
        txtDate.setText(orderDTO.getOrderDate());
        txtTime.setText(orderDTO.getOrderTime());
        txtCost.setText(String.valueOf(orderDTO.getCost()));
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String orderId=txtOrderId.getText();
        if(isOrderFilled()){

 /*if(isOrderDetailFilled()){

            }else{*/

                try{
                    placeOrderBO.deleteOrder(orderId);
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").show();
                    clearAllFields();
                    refreshPage();
                }catch (Exception e){
                    new Alert(Alert.AlertType.WARNING,"Try again...").show();
                }
           // }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please Select A Row... ").show();
        }
    }

    private void viewAllOnAction(){
        try {
            obList.clear();
            List<OrderDTO> orderDTOS =placeOrderBO.getAllOrders();
            obList.addAll(orderDTOS);
            tblOrderList.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void refreshPage(){
        try {
            pageContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageOrderForm.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/Bill.jrxml"));
            JasperReport compileReport= JasperCompileManager.compileReport(design);
            ObservableList<OrderDetail> items = tblOrderDetailList.getItems();

            String orderId=txtOrderId.getText();
            String custId=txtCustId.getText();
            String date=txtDate.getText();
            String time=txtTime.getText();
            double cost= Double.parseDouble(txtCost.getText());

            HashMap map=new HashMap();
            map.put("oId",orderId);
            map.put("cusId",custId);
            map.put("date",date);
            map.put("time",time);
            map.put("cost",cost);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}

