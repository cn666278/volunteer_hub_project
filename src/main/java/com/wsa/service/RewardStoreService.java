package com.wsa.service;

import com.wsa.mapper.RewardStoreMapper;
import com.wsa.model.Item;
import com.wsa.model.RedeemRequest;
import com.wsa.model.RedeemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardStoreService {

    @Autowired
    private RewardStoreMapper rewardStoreMapper;

    public List<Item> getItems() {
        return rewardStoreMapper.getItems();
    }

    public RedeemResponse redeemItem(RedeemRequest request) {
        int userPoints = rewardStoreMapper.getUserPoints(request.getUserId());
        Item item = rewardStoreMapper.getItemById(request.getItemId());

        if (userPoints >= item.getPointsRequired()) {
            rewardStoreMapper.deductPoints(request.getUserId(), item.getPointsRequired());
            rewardStoreMapper.createRedemptionRecord(request.getUserId(), request.getItemId(), item.getPointsRequired());
            return new RedeemResponse(true, "Redemption successful!");
        } else {
            return new RedeemResponse(false, "Insufficient points!");
        }
    }

    public void addItem(Item item) {
        rewardStoreMapper.addItem(item);
    }

    public void updateItem(Item item) {
        rewardStoreMapper.updateItem(item);
    }

    public void deleteItem(Long id) {
        rewardStoreMapper.deleteItem(id);
    }
}
