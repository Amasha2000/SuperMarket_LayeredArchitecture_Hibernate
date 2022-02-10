
package bo.custom.impl;

import bo.custom.ItemBO;

import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private final ItemDAO itemDAO = (ItemDAO)DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.ITEM);

    @Override
    public void addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        itemDAO.add(new Item(dto.getCode(), dto.getDescription(), dto.getPackSize(), dto.getQtyOnHand(), dto.getUnitPrice(), dto.getDiscountPercent()));
       }

    @Override
    public void updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
         itemDAO.update(new Item(dto.getCode(), dto.getDescription(), dto.getPackSize(), dto.getQtyOnHand(), dto.getUnitPrice(), dto.getDiscountPercent()));
    }

    @Override
    public void deleteItem(String id) throws SQLException, ClassNotFoundException {
        itemDAO.delete(id);
    }

    @Override
    public ItemDTO search(String id) throws SQLException, ClassNotFoundException {
        Item item=itemDAO.search(id);
        return new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice(),item.getDiscountPercent());
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> allItems = new ArrayList<>();
        List<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice(),item.getDiscountPercent()));
        }
        return allItems;
    }

    @Override
    public String getItemCodes() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemCode();
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> itemList=new ArrayList<>();
        List<Item> itemDTOS =itemDAO.getAll();
        for(Item i: itemDTOS){
            itemList.add(i.getItemCode());
        }
        return itemList;
    }
}

