
package dao.custom.impl;

import dao.custom.CustomerDAO;
import db.FactoryConfiguration;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public void add(Customer customer) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(customer);
        transaction.commit();
    }

    @Override
    public void update(Customer customer) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(customer);
        transaction.commit();
    }


    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Customer.class,id));
        transaction.commit();
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Customer load = session.load(Customer.class, id);
        transaction.commit();
        return load;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "From Customer";
        Query query=session.createQuery(hql);
        List<Customer> list = query.list();
        transaction.commit();
        return (ArrayList<Customer>) list;
    }

    @Override
    public String getCustomerId() throws SQLException, ClassNotFoundException {
       /* ResultSet rst= (ResultSet) session.createSQLQuery("SELECT custId FROM Customer ORDER BY custId DESC LIMIT 1");
        if(rst.next()){
            int index=Integer.parseInt(rst.getString(1).split("-")[1]);
            index=index+1;
            if(index<10){
                return "C-000"+ index;
            }else if(index<100){
                return "C-00"+ index;
            }else if(index<1000){
                return "C-0"+ index;
            }else{
                return "C-"+ index;
            }
        }else{
            return "C-0001";
        }*/
        return null;
    }

}

