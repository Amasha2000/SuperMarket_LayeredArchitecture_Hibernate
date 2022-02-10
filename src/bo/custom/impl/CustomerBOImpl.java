package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;

import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public void addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        customerDAO.add(new Customer(dto.getId(), dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode()));
    }

    @Override
    public void updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        customerDAO.update(new Customer(dto.getId(), dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode()));
    }

    @Override
    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        customerDAO.delete(id);
    }

    @Override
    public CustomerDTO search(String id) throws SQLException, ClassNotFoundException {
        Customer customer=customerDAO.search(id);
        return new CustomerDTO(customer.getCustId(),customer.getCustTitle(), customer.getCustName(), customer.getCustAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> allCustomers = new ArrayList<>();
        List<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustId(), customer.getCustTitle(), customer.getCustName(), customer.getCustAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public String getCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerId();
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerList=new ArrayList<>();
        List<Customer> customerDTOS =customerDAO.getAll();
        for(Customer c: customerDTOS){
            customerList.add(c.getCustId());
        }
        return customerList;
    }
}

