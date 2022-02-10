package dao.custom.impl;
import dao.custom.ItemDAO;
import db.FactoryConfiguration;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public void add(Item item) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        session.save(item);
        transaction.commit();
    }

    @Override
    public void update(Item item) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
         Transaction transaction=session.beginTransaction();
         session.update(item);
         transaction.commit();
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        session.delete(session.load(Item.class,id));
        transaction.commit();
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Item load = session.load(Item.class, id);
        transaction.commit();
        return load;
          }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Query query = session.createQuery(" FROM Item");
        List<Item> list = query.list();
        transaction.commit();
        return list;
    }

    @Override
    public String getItemCode() throws SQLException, ClassNotFoundException {
        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        ResultSet rst = (ResultSet) session.createSQLQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1");
        transaction.commit();
        if (rst.next()) {
            int index = Integer.parseInt(rst.getString(1).split("-")[1]);
            index=index+1;
            if (index < 10) {
                return "I-000" + index;
            } else if (index < 100) {
                return "I-00" + index;
            } else if (index < 1000) {
                return "I-0" + index;
            } else {
                return "I-" + index;
            }
        } else {
            return "I-0001";
        }*/
        return null;
    }
}
