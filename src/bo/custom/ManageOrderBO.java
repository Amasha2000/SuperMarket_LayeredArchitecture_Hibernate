package bo.custom;

import bo.SuperBO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface ManageOrderBO extends SuperBO {
    List<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException;
}
