package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    void addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    void updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    void deleteItem(String id) throws SQLException, ClassNotFoundException;
    ItemDTO search(String id) throws SQLException, ClassNotFoundException;
    List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    String getItemCodes() throws SQLException, ClassNotFoundException;
    List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
}
