package entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class OrderDetail extends ArrayList<OrderDetail> {
    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    private int orderQty;
    private double price;
    private double discount;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
   // @JoinColumn(name="oId",referencedColumnName = "orderId",updatable = false,insertable = false)
    @JoinColumn(name= "orderId",updatable = false,insertable = false)
    private
    Orders orders;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
   // @JoinColumn(name="code",referencedColumnName = "itemCode",updatable = false,insertable = false)
    @JoinColumn(name="itemCode",updatable = false,insertable = false)
    private
    Item item;

    public OrderDetail() {
    }

    public OrderDetail( Orders orders,Item item,int orderQty,double price, double discount) {
        this.orders=orders;
        this.item=item;
        this.setOrderQty(orderQty);
        this.setPrice(price);
        this.setDiscount(discount);
    }


    public OrderDetail( String id, String code,int orderQty,double price, double discount) {
        this.orderDetailPK=new OrderDetailPK(id,code);
        this.setOrderQty(orderQty);
        this.setPrice(price);
        this.setDiscount(discount);
    }

    public OrderDetailPK getOrderDetailPK() {
        return orderDetailPK;
    }

    public void setOrderDetailPK(OrderDetailPK orderDetailPK) {
        this.orderDetailPK = orderDetailPK;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Orders getOrder() {
        return orders;
    }

    public void setOrder(Orders orders) {
        this.orders = orders;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
