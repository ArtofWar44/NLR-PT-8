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
        Assert.assertEquals("getAllItems returned wrong number of items", 32, items.size());
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
        Item newItem = new Item("Initial Name", 5.00, Item.Category.DOG_TOY, 5);  // Ensure the item with ID 1 exists
        jdbcItemDao.addItem(newItem);
        int itemId = jdbcItemDao.getAllItems().get(0).getItemId(); // Get the ID of the newly added item

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


    /* @Test
    public void getItemById_returns_correct_item_for_id() {
        jdbcItemDao.addItem(ITEM_1);    // Add the items to ensure they exist in the database
        jdbcItemDao.addItem(ITEM_2);
        jdbcItemDao.addItem(ITEM_3);
        jdbcItemDao.addItem(ITEM_4);

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


        jdbcItemDao.deleteItem(ITEM_1.getItemId());  // Cleanup: Delete the added items
        jdbcItemDao.deleteItem(ITEM_2.getItemId());
        jdbcItemDao.deleteItem(ITEM_3.getItemId());
        jdbcItemDao.deleteItem(ITEM_4.getItemId());
        //

*/

}

