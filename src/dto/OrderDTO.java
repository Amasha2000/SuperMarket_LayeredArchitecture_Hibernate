package dto;

import java.util.ArrayList;

public class OrderDTO {
	private String orderId;
    private String orderDate;
    private String orderTime;
    private double cost;
    private String custId;
    private ArrayList<OrderDetailDTO> detailList;

    public OrderDTO() {   }

    public OrderDTO(String orderId, String orderDate, String orderTime, double cost,String custId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
        this.custId = custId;
    }

    public OrderDTO(String orderId, String custId, String orderDate, String orderTime, double cost, ArrayList<OrderDetailDTO> detailList) {
        this.setOrderId(orderId);
        this.setCustId(custId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCost(cost);
        this.setDetailList(detailList);
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<OrderDetailDTO> getDetailList() {
        return detailList;
    }
    public void setDetailList(ArrayList<OrderDetailDTO> detailList) {
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + getOrderId() + '\'' +
                ", custId='" + getCustId() + '\'' +
                ", orderDate='" + getOrderDate() + '\'' +
                ", orderTime='" + getOrderTime() + '\'' +
                ", cost=" + getCost() +
                ", detailList=" + getDetailList() +
            '}';
    }
}
