package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String>{
    OrderDetail getAllOrderDetails(String id) throws SQLException, ClassNotFoundException;
}
