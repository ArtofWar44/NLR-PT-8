package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Item;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcItemDAO implements ItemDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Item getItemById(int itemId) {
        Item item = null;
        String sql = "SELECT item_id, name, price, category, quantity FROM items WHERE item_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                item = mapRowToItem(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT item_id, name, price, category, quantity FROM items ORDER BY name";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Item item = mapRowToItem(results);
                items.add(item);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return items;
    }

    @Override
    public void addItem(Item item) {
        String sql = "INSERT INTO items (name, price, category, quantity) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, item.getName(), item.getPrice(), item.getCategory().name(), item.getQuantity());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Error adding item", e);
        }
    }

    @Override
    public void updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, price = ?, category = ?, quantity = ? WHERE item_id = ?";
        try {
            jdbcTemplate.update(sql, item.getName(), item.getPrice(), item.getCategory().name(), item.getQuantity(), item.getItemId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Error updating item", e);
        }
    }

    @Override
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?";
        try {
            jdbcTemplate.update(sql, itemId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Error deleting item", e);
        }
    }

    @Override
    public void deleteAllItems() {
        String sql = "DELETE FROM items";
        try {
            jdbcTemplate.update(sql);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Error deleting all items", e);
        }
    }

    private Item mapRowToItem(SqlRowSet results) {
        Item item = new Item();
        item.setItemId(results.getInt("item_id"));
        item.setName(results.getString("name"));
        item.setPrice(results.getDouble("price"));
        item.setCategory(Item.Category.valueOf(results.getString("category")));
        item.setQuantity(results.getInt("quantity"));
        return item;
    }
}
