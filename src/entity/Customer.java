package entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
        @Id
        private String custId;
        private String custTitle;
        private String custName;
        private String custAddress;
        private String city;
        private String province;
        private String postalCode;
        @OneToMany(mappedBy = "customer",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
        private List<Orders> orders=new ArrayList<>();

        public Customer() {
        }

       /* public Customer(String custId, String custTitle, String custName, String custAddress, String city, String province, String postalCode, List<Orders> orders) {
                this.setCustId(custId);
                this.setCustTitle(custTitle);
                this.setCustName(custName);
                this.setCustAddress(custAddress);
                this.setCity(city);
                this.setProvince(province);
                this.setPostalCode(postalCode);
                this.setOrders(orders);
        }*/

    public Customer(String custId, String custTitle, String custName, String custAddress, String city, String province, String postalCode) {
                this.setCustId(custId);
                this.setCustTitle(custTitle);
                this.setCustName(custName);
                this.setCustAddress(custAddress);
                this.setCity(city);
                this.setProvince(province);
                this.setPostalCode(postalCode);
    }

    public String getCustId() {
                return custId;
        }

        public void setCustId(String custId) {
                this.custId = custId;
        }

        public String getCustTitle() {
                return custTitle;
        }

        public void setCustTitle(String custTitle) {
                this.custTitle = custTitle;
        }

        public String getCustName() {
                return custName;
        }

        public void setCustName(String custName) {
                this.custName = custName;
        }

        public String getCustAddress() {
                return custAddress;
        }

        public void setCustAddress(String custAddress) {
                this.custAddress = custAddress;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public String getProvince() {
                return province;
        }

        public void setProvince(String province) {
                this.province = province;
        }

        public String getPostalCode() {
                return postalCode;
        }

        public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
        }

        public List<Orders> getOrders() {
                return orders;
        }

        public void setOrders(List<Orders> orders) {
                this.orders = orders;
        }
}
