package com.wsa.controller;

import com.wsa.model.Item;
import com.wsa.model.RedeemRequest;
import com.wsa.model.RedeemResponse;
import com.wsa.model.ResultVO;
import com.wsa.service.RewardStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewardstore") // Base URL for all reward store-related endpoints
public class RewardStoreController {

    @Autowired
    private RewardStoreService rewardStoreService; // Service for managing reward store operations

    /**
     * Endpoint to retrieve all available items in the reward store.
     * @return ResultVO containing a list of items available for redemption.
     */
    @GetMapping("/items")
    public ResultVO<List<Item>> getItems() {
        return ResultVO.success(rewardStoreService.getItems()); // Fetch and return all items in the reward store
    }

    /**
     * Endpoint to redeem an item from the reward store.
     * @param request The redeem request containing user details and item information.
     * @return ResultVO containing the response of the redemption process.
     */
    @PostMapping("/redeem")
    public ResultVO<RedeemResponse> redeemItem(@RequestBody RedeemRequest request) {
        return ResultVO.success(rewardStoreService.redeemItem(request)); // Process the redemption request and return the result
    }

    /**
     * Endpoint to add a new item to the reward store.
     * @param item The item object to be added.
     * @return ResultVO containing a success message if the item is added successfully.
     */
    @PostMapping("/add")
    public ResultVO<String> addItem(@RequestBody Item item) {
        rewardStoreService.addItem(item); // Add the new item to the reward store
        return ResultVO.success("Item added successfully");
    }

    /**
     * Endpoint to update an existing item in the reward store.
     * @param item The item object with updated information.
     * @return ResultVO containing a success message if the item is updated successfully.
     */
    @PutMapping("/update")
    public ResultVO<String> updateItem(@RequestBody Item item) {
        rewardStoreService.updateItem(item); // Update the item in the reward store
        return ResultVO.success("Item updated successfully");
    }

    /**
     * Endpoint to delete an item from the reward store.
     * @param id The ID of the item to be deleted.
     * @return ResultVO containing a success message if the item is deleted successfully.
     */
    @DeleteMapping("/delete/{id}")
    public ResultVO<String> deleteItem(@PathVariable Long id) {
        rewardStoreService.deleteItem(id); // Delete the item by its ID
        return ResultVO.success("Item deleted successfully");
    }
}
