package dao.custom.impl;

import dao.custom.OrderDetailDAO;
import db.FactoryConfiguration;
import entity.OrderDetail;
import entity.OrderDetailPK;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {


    @Override
    public void add(OrderDetail dto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(dto);
        transaction.commit();
    }

    @Override
    public void update(OrderDetail orderDetailDAO) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public void delete(String s) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail search(String s) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> getAll() {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail getAllOrderDetails(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        //OrderDetailPK orderDetailPK=new OrderDetailPK();
        OrderDetail load = session.load(OrderDetail.class,id);
        transaction.commit();
        return load;
    }
}
