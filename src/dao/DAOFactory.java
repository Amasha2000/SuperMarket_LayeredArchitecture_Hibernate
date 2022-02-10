package dao;

import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private  DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return daoFactory==null? daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL
    }
    public SuperDAO getDAOType(DAOTypes types){
        switch (types){
            case CUSTOMER:return new CustomerDAOImpl();
            case ITEM:return new ItemDAOImpl();
            case ORDER:return new OrderDAOImpl();
            case ORDER_DETAIL:return new OrderDetailDAOImpl();
            default:return null;
        }
    }
}
