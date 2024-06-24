package org.ArtofWar44.Dao;
import org.ArtofWar44.Model.Item;

import javax.sql.DataSource;
import java.sql.*;
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

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Item item = createItemFromResultSet(rs);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public Item getItemById(int id) {
        Item item = null;
        String sql = "SELECT * FROM items WHERE item_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    item = createItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public void addItem(Item item) {
        String sql = "INSERT INTO items (name, price, category, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setString(3, item.getCategory().name());
            pstmt.setInt(4, item.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, price = ?, category = ?, quantity = ? WHERE item_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setString(3, item.getCategory().name());
            pstmt.setInt(4, item.getQuantity());
            pstmt.setInt(5, item.getItemId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(int id) {
        String sql = "DELETE FROM items WHERE item_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Item createItemFromResultSet(ResultSet rs) throws SQLException {
        String categoryName = rs.getString("category");
        Item.Category category = Item.Category.valueOf(categoryName.toUpperCase());

        return category.createItem(
                rs.getString("name"),
                rs.getDouble("price")
        );
    }
}
