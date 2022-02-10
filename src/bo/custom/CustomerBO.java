package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public interface CustomerBO extends SuperBO {
    void addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    void updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
    void deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    CustomerDTO search(String id) throws SQLException, ClassNotFoundException;
    List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    String getCustomerId() throws SQLException, ClassNotFoundException;
    List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
}
