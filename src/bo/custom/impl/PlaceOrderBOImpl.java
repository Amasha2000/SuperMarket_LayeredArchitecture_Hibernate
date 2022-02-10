
package bo.custom.impl;

import bo.custom.PlaceOrderBO;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.FactoryConfiguration;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public void addOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        Orders orders=new Orders(dto.getOrderId(), dto.getOrderDate(), dto.getOrderTime(), dto.getCost(), FactoryConfiguration.getInstance().getSession().get(Customer.class,dto.getCustId()));
        orderDAO.add(orders);
        for (OrderDetailDTO detail : dto.getDetailList()) {
            OrderDetail orderDetail = new OrderDetail(dto.getOrderId(),detail.getItemCode(), detail.getOrderQty(), detail.getPrice(), detail.getDiscount());
            orderDetailDAO.add(orderDetail);
                }
            }


    @Override
    public void deleteOrder(String id) throws SQLException, ClassNotFoundException {
        orderDAO.delete(id);
    }

    @Override
    public OrderDTO search(String id) throws SQLException, ClassNotFoundException {
        Orders orders =orderDAO.search(id);
        return new OrderDTO(orders.getOrderId(), orders.getOrderDate(), orders.getTime(), orders.getCost(),orders.getCustomer().getCustId());
    }

    @Override
    public List<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderDTO> allOrders = new ArrayList<>();
        List<Orders> all = orderDAO.getAll();
        for (Orders orders : all) {
            allOrders.add(new OrderDTO(orders.getOrderId(), orders.getOrderDate(), orders.getTime(), orders.getCost(),orders.getCustomer().getCustId()));
        }
        return allOrders;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderId();
    }
}
