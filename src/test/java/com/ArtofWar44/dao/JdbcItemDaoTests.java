package com.ArtofWar44.dao;

import org.ArtofWar44.Dao.JdbcItemDAO;
import org.ArtofWar44.Model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcItemDaoTests extends BaseDaoTests {

    private static final Item ITEM_1 = new Item("Squeaky Ball", 6.00, Item.Category.DOG_TOY);
    private static final Item ITEM_2 = new Item("Rope Tug Toy", 8.00, Item.Category.DOG_TOY);
    private static final Item ITEM_3 = new Item("Plush Chew Toy", 10.00, Item.Category.DOG_TOY);
    private static final Item ITEM_4 = new Item("Rubber Chew Toy", 10.00, Item.Category.DOG_TOY);

    private JdbcItemDAO jdbcItemDao;

    @Before
    public void setup() {
        jdbcItemDao = new JdbcItemDAO(dataSource);
    }

    @Test
    public void getItemById_returns_correct_item_for_id() {
        Item item = jdbcItemDao.getItemById(1);
        Assert.assertNotNull("getItemById(1) returned null", item);
        assertItemsMatch("getItemById(1) returned wrong or partial data", ITEM_1, item);

        item = jdbcItemDao.getItemById(2);
        Assert.assertNotNull("getItemById(2) returned null", item);
        assertItemsMatch("getItemById(2) returned wrong or partial data", ITEM_2, item);

        item = jdbcItemDao.getItemById(3);
        Assert.assertNotNull("getItemById(3) returned null", item);
        assertItemsMatch("getItemById(3) returned wrong or partial data", ITEM_3, item);

        item = jdbcItemDao.getItemById(4);
        Assert.assertNotNull("getItemById(4) returned null", item);
        assertItemsMatch("getItemById(4) returned wrong or partial data", ITEM_4, item);

        item = jdbcItemDao.getItemById(5);
        Assert.assertNull("getItemById(5) does not exist and should be null", item);
    }

    @Test
    public void getAllItems_returns_all_items() {
        List<Item> items = jdbcItemDao.getAllItems();
        Assert.assertEquals("getAllItems returned wrong number of items", 4, items.size());

        assertItemsMatch("getAllItems returned wrong or partial data", ITEM_1, items.get(0));
        assertItemsMatch("getAllItems returned wrong or partial data", ITEM_2, items.get(1));
        assertItemsMatch("getAllItems returned wrong or partial data", ITEM_3, items.get(2));
        assertItemsMatch("getAllItems returned wrong or partial data", ITEM_4, items.get(3));
    }

    @Test
    public void addItem_adds_item() {
        Item newItem = new Item("New Item", 15.00, Item.Category.MYSTERY_TREAT);
        jdbcItemDao.addItem(newItem);

        Item retrievedItem = jdbcItemDao.getItemById(5);
        Assert.assertNotNull("addItem did not add the item", retrievedItem);
        assertItemsMatch("addItem returned wrong or partial data", newItem, retrievedItem);
    }

    @Test
    public void updateItem_updates_item() {
        Item item = jdbcItemDao.getItemById(1);
        item.setName("Updated Name");
        item.setPrice(20.00);
        item.setCategory(Item.Category.RAWHIDE_BONE);
        item.setQuantity(10);

        jdbcItemDao.updateItem(item);

        Item updatedItem = jdbcItemDao.getItemById(1);
        assertItemsMatch("updateItem did not update the item", item, updatedItem);
    }

    @Test
    public void deleteItem_deletes_item() {
        jdbcItemDao.deleteItem(1);
        Item item = jdbcItemDao.getItemById(1);
        Assert.assertNull("deleteItem did not delete the item", item);
    }

    private void assertItemsMatch(String message, Item expected, Item actual) {
        Assert.assertEquals(message, expected.getItemId(), actual.getItemId());
        Assert.assertEquals(message, expected.getName(), actual.getName());
        Assert.assertEquals(message, expected.getPrice(), actual.getPrice(), 0.001);
        Assert.assertEquals(message, expected.getCategory(), actual.getCategory());
        Assert.assertEquals(message, expected.getQuantity(), actual.getQuantity());
    }
}
