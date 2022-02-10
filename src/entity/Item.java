package entity;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Item {
            @Id
            private String itemCode;
            private String description;
            private String packSize;
            private int qtyOnHand;
            private double unitPrice ;
            private double discountPercent;
            //@OneToMany(mappedBy = "item",cascade = CascadeType.PERSIST)
            @OneToMany(mappedBy = "item",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH})
            private
            List<OrderDetail> orderDetail=new ArrayList<>();

    public Item() {
    }

   /* public Item(String itemCode, String description, String packSize, int qtyOnHand, double unitPrice, double discountPercent, List<OrderDetail> orderDetail) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setPackSize(packSize);
        this.setQtyOnHand(qtyOnHand);
        this.setUnitPrice(unitPrice);
        this.setDiscountPercent(discountPercent);
        this.setOrderDetail(orderDetail);
    }*/

    public Item(String itemCode, String description, String packSize, int qtyOnHand, double unitPrice, double discountPercent) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setPackSize(packSize);
        this.setQtyOnHand(qtyOnHand);
        this.setUnitPrice(unitPrice);
        this.setDiscountPercent(discountPercent);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    /*public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }*/
}
