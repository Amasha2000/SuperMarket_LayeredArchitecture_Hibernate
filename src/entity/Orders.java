package entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Orders {
            @Id
            private String orderId;
            private String orderDate;
            private String time;
            private double cost;
            @JoinColumn(name="cus_Id",referencedColumnName = "custId",nullable = false)
            @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
            private Customer customer;
           // @OneToMany(mappedBy = "orders",cascade = {CascadeType.PERSIST})
            @OneToMany(mappedBy = "orders",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
            private
            List<OrderDetail> orderDetail=new ArrayList<>();

    public Orders() {

    }

    public Orders(String orderId, String orderDate, String time, double cost, Customer customer) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.time = time;
        this.cost = cost;
        this.customer = customer;
    }

    public Orders(String orderId, String orderDate, String time, double cost, Customer customer, List<OrderDetail> orderDetail) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.time = time;
        this.cost = cost;
        this.customer = customer;
        this.orderDetail = orderDetail;
    }

    public Orders(String id, String cusId, String orderDate, String time, double cost) {
    }
/*public Orders(String orderId, String custId, String orderDate, String time, double cost, Customer customer, List<OrderDetail> orderDetailArrayList) {
        this.setOrderId(orderId);
        this.setCustId(custId);
        this.setOrderDate(orderDate);
        this.setTime(time);
        this.setCost(cost);
        this.setCustomer(customer);
        this.setOrderDetailArrayList(orderDetailArrayList);
    }*/


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetailArrayList() {
        return orderDetail;
    }

    public void setOrderDetailArrayList(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
