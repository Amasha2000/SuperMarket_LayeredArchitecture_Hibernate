package bo.custom.impl;

import bo.custom.ManageOrderBO;
import dao.DAOFactory;
import dao.custom.OrderDetailDAO;
import dto.OrderDetailDTO;
import entity.OrderDetail;
import entity.OrderDetailPK;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageOrderBOImpl implements ManageOrderBO {

    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.ORDER_DETAIL);


    @Override
    public List<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException {
        List<OrderDetailDTO> allOrderDetails = new ArrayList<>();
        List<OrderDetail> all = orderDetailDAO.getAllOrderDetails(id);
        for (OrderDetail orderDetail : all) {
            allOrderDetails.add(new OrderDetailDTO(id,orderDetail.getItem().getItemCode(),orderDetail.getOrderQty(),orderDetail.getPrice(),orderDetail.getDiscount()));
        }
        return allOrderDetails;
    }
}
