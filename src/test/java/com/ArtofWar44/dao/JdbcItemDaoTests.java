package com.ArtofWar44.dao;

import org.ArtofWar44.Dao.JdbcItemDAO;
import org.ArtofWar44.Model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcItemDaoTests extends BaseDaoTests {

    private static final Item ITEM_1 = new Item(1, "Squeaky Ball", 6.00, Item.Category.DOG_TOY, 10);
    private static final Item ITEM_2 = new Item(2, "Rope Tug Toy", 8.00, Item.Category.DOG_TOY, 10);
    private static final Item ITEM_3 = new Item(3, "Plush Chew Toy", 10.00, Item.Category.DOG_TOY, 10);
    private static final Item ITEM_4 = new Item(4, "Rubber Chew Toy", 10.00, Item.Category.DOG_TOY, 10);

    private JdbcItemDAO jdbcItemDao;

    @Before
    public void setup() {
        jdbcItemDao = new JdbcItemDAO(dataSource);
    }


    @Test
    public void getAllItems_returns_all_items() {
        List<Item> items = jdbcItemDao.getAllItems();
        Assert.assertNotNull("getAllItems returned null", items);
        Assert.assertEquals("getAllItems returned wrong number of items", 34, items.size());
    }

    @Test
    public void addItem_adds_item() {  // Create a new item to add
        Item newItem = new Item();
        newItem.setName("New Item");
        newItem.setPrice(15.00);
        newItem.setCategory(Item.Category.MYSTERY_TREAT);
        newItem.setQuantity(10);

        jdbcItemDao.addItem(newItem);  // Add the new item to the database

        List<Item> items = jdbcItemDao.getAllItems();   // Retrieve all items from the database

        Item retrievedItem = null;  // Find the newly added item
        for (Item item : items) {
            if (item.getName().equals("New Item")) {
                retrievedItem = item;
            }
        }
        Assert.assertNotNull("addItem did not add the item", retrievedItem);    // Verify that the item was added
        assertItemsMatch("addItem returned wrong or partial data", newItem, retrievedItem);  // Verify that the retrieved item matches the added item
        jdbcItemDao.deleteItem(retrievedItem.getItemId()); // Cleanup: Delete the newly added item to prevent the 'getAllItemsTest' from failing
    }

    @Test
    public void updateItem_updates_item() {
        Item newItem = new Item("Updated Item Name", 5.00, Item.Category.DOG_TOY, 1);
        jdbcItemDao.addItem(newItem);
        int itemId = jdbcItemDao.getAllItems().get(0).getItemId();

        Item item = jdbcItemDao.getItemById(itemId);
        Assert.assertNotNull("Item with ID " + itemId + " should not be null", item);
        item.setName("Updated Name");
        item.setPrice(20.00);
        item.setCategory(Item.Category.RAWHIDE_BONE);
        item.setQuantity(10);

        jdbcItemDao.updateItem(item);

        Item updatedItem = jdbcItemDao.getItemById(itemId);
        assertItemsMatch("updateItem did not update the item", item, updatedItem);

        jdbcItemDao.deleteItem(itemId);  // Cleanup: Delete the updated item
    }

    @Test
    public void deleteItem_deletes_item() {
        jdbcItemDao.deleteItem(1);
        Item item = jdbcItemDao.getItemById(1);
        Assert.assertNull("deleteItem did not delete the item", item);


    }


    private void assertItemsMatch(String message, Item expected, Item actual) {
        Assert.assertEquals(message, expected.getName(), actual.getName());
        Assert.assertEquals(message, expected.getPrice(), actual.getPrice(), 0);
        Assert.assertEquals(message, expected.getCategory(), actual.getCategory());
        Assert.assertEquals(message, expected.getQuantity(), actual.getQuantity());
    }


}

