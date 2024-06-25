package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Item;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcItemDAO implements ItemDAO {

    private final String ITEM_SELECT = "SELECT item_id, name, price, category, quantity FROM items";
    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Item getItemById(int id) {
        Item item = null;
        String sql = ITEM_SELECT + " WHERE item_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                item = mapRowToItem(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException("Error getting item by id", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return item;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = ITEM_SELECT;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Item item = mapRowToItem(results);
                items.add(item);
            }
        } catch (DataAccessException | SQLException e) {
            throw new DaoException("Error getting all items", e);
        }

        return items;
    }

    @Override
    public void addItem(Item item) {
        String sql = "INSERT INTO items (name, price, category, quantity) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, item.getName(), item.getPrice(), item.getCategory().name(), item.getQuantity());
        } catch (DataAccessException e) {
            throw new DaoException("Error adding item", e);
        }
    }

    @Override
    public void updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, price = ?, category = ?, quantity = ? WHERE item_id = ?";

        try {
            jdbcTemplate.update(sql, item.getName(), item.getPrice(), item.getCategory().name(), item.getQuantity(), item.getItemId());
        } catch (DataAccessException e) {
            throw new DaoException("Error updating item", e);
        }
    }

    @Override
    public void deleteItem(int id) {
        String sql = "DELETE FROM items WHERE item_id = ?";

        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new DaoException("Error deleting item", e);
        }
    }

    private Item mapRowToItem(SqlRowSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getDouble("price"));
        item.setCategory(Item.Category.valueOf(rs.getString("category")));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }
}
