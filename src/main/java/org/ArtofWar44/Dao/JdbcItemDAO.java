package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Item;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcItemDAO implements ItemDAO {
    private final String url = "jdbc:postgresql://localhost:5432/PawPointsDB";
    private final String user = "postgres";
    private final String password = "postgres1";

    public JdbcItemDAO(DataSource dataSource) {

    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        return items;
    }

    @Override
    public Item getItemById(int id) {
        Item item = null;
        String sql = "SELECT * FROM items WHERE item_id = ?";

        return item;
    }

    @Override
    public void addItem(Item item) {
        String sql = "INSERT INTO items (name, price, category, quantity) VALUES (?, ?, ?, ?)";

        }


    @Override
    public void updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, price = ?, category = ?, quantity = ? WHERE item_id = ?";

    }

    @Override
    public void deleteItem(int id) {

    }


}
