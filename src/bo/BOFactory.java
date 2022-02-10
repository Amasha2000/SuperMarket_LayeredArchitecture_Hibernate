package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.ManageOrderBOImpl;
import bo.custom.impl.PlaceOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}
    public static BOFactory getBoFactory(){
        return boFactory==null? boFactory=new BOFactory():boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PLACE_ORDER,MANAGE_ORDER
    }

    public SuperBO getBOType(BOTypes types){
        switch (types){
             case CUSTOMER:return new CustomerBOImpl();
             case ITEM:return new ItemBOImpl();
             case PLACE_ORDER:return new PlaceOrderBOImpl();
             case MANAGE_ORDER:return new ManageOrderBOImpl();
             default:return null;
        }
    }
}
