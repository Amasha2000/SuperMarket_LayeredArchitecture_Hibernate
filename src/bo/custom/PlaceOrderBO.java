package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    void addOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
    void deleteOrder(String id) throws SQLException, ClassNotFoundException;
    OrderDTO search(String id) throws SQLException, ClassNotFoundException;
    List<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;
    String getOrderId() throws SQLException, ClassNotFoundException;
}
