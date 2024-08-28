package com.wsa.service;

import com.wsa.mapper.RewardStoreMapper;
import com.wsa.model.Item;
import com.wsa.model.RedeemRequest;
import com.wsa.model.RedeemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing reward store operations.
 * This service interacts with the data access layer (RewardStoreMapper) to handle items in the reward store,
 * as well as user redemptions.
 */
@Service
public class RewardStoreService {

    @Autowired
    private RewardStoreMapper rewardStoreMapper; // Mapper for database interactions related to the reward store

    /**
     * Retrieves all items available in the reward store.
     * @return List of items in the reward store.
     */
    public List<Item> getItems() {
        return rewardStoreMapper.getItems(); // Fetch all items from the database
    }

    /**
     * Processes a redemption request for an item in the reward store.
     * Checks if the user has sufficient points to redeem the item, deducts the points if possible,
     * and creates a redemption record.
     * @param request The redemption request containing user ID and item ID.
     * @return RedeemResponse object indicating whether the redemption was successful or not.
     */
    public RedeemResponse redeemItem(RedeemRequest request) {
        int userPoints = rewardStoreMapper.getUserPoints(request.getUserId()); // Fetch user's points
        Item item = rewardStoreMapper.getItemById(request.getItemId()); // Fetch the item to be redeemed

        // Check if the user has enough points to redeem the item
        if (userPoints >= item.getPointsRequired()) {
            // Deduct the required points and create a redemption record
            rewardStoreMapper.deductPoints(request.getUserId(), item.getPointsRequired());
            rewardStoreMapper.createRedemptionRecord(request.getUserId(), request.getItemId(), item.getPointsRequired());
            return new RedeemResponse(true, "Redemption successful!");
        } else {
            // Return failure response if points are insufficient
            return new RedeemResponse(false, "Insufficient points!");
        }
    }

    /**
     * Adds a new item to the reward store.
     * @param item The item object containing item details.
     */
    public void addItem(Item item) {
        rewardStoreMapper.addItem(item); // Insert new item into the database
    }

    /**
     * Updates an existing item in the reward store.
     * @param item The item object containing updated item details.
     */
    public void updateItem(Item item) {
        rewardStoreMapper.updateItem(item); // Update item details in the database
    }

    /**
     * Deletes an item from the reward store by its ID.
     * @param id The ID of the item to be deleted.
     */
    public void deleteItem(Long id) {
        rewardStoreMapper.deleteItem(id); // Remove the item from the database by its ID
    }
}
