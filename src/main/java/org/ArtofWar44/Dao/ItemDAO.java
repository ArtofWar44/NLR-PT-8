package org.ArtofWar44.Dao;
import org.ArtofWar44.Model.Item;

import java.util.List;

public interface ItemDAO {
    List<Item> getAllItems();
    Item getItemById(int id);
    void addItem(Item item);
    void updateItem(Item item);
    void deleteItem(int id);

    void deleteAllItems();
}