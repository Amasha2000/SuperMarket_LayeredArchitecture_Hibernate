
package dao.custom.impl;


import dao.custom.OrderDAO;
import db.FactoryConfiguration;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {


    @Override
    public void add(Orders orders) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(orders);
        transaction.commit();
    }

    @Override
    public void update(Orders orders) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(orders);
        transaction.commit();
        //session.createSQLQuery("UPDATE `Orders` SET custId=?, orderDate=?, time=?, cost=? WHERE orderId=?");
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Orders.class,id));
        transaction.commit();
    }

    @Override
    public Orders search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        session.load(Orders.class,id);
        ResultSet rst = (ResultSet) session.createSQLQuery("SELECT * FROM Orders WHERE orderId= 'id'");
        rst.next();
        transaction.commit();
        return new Orders(id, rst.getString("cusId"), rst.getString("orderDate"), rst.getString("time"),rst.getDouble("cost"));
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Orders");
        List<Orders> list = query.list();
        transaction.commit();
        return (ArrayList<Orders>) list;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
       /* ResultSet rst= (ResultSet) session.createSQLQuery("SELECT orderId FROM `Orders` ORDER BY orderId DESC LIMIT 1");
        if(rst.next()){
            int index=Integer.parseInt(rst.getString(1).split("-")[1]);
            index=index+1;
            if(index<10){
                return "O-000"+ index;
            }else if(index<100){
                return "O-00"+ index;
            }else if(index<1000){
                return "O-0"+ index;
            }else{
                return "O-"+ index;
            }
        }else{
            return "O-0001";
        }*/
        return null;
    }
}

