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
@RequestMapping("/rewardstore")
public class RewardStoreController {

    @Autowired
    private RewardStoreService rewardStoreService;

    @GetMapping("/items")
    public ResultVO<List<Item>> getItems() {
        return ResultVO.success(rewardStoreService.getItems());
    }

    @PostMapping("/redeem")
    public ResultVO<RedeemResponse> redeemItem(@RequestBody RedeemRequest request) {
        return ResultVO.success(rewardStoreService.redeemItem(request));
    }

    // 新增物品接口
    @PostMapping("/add")
    public ResultVO<String> addItem(@RequestBody Item item) {
        rewardStoreService.addItem(item);
        return ResultVO.success("Item added successfully");
    }

    // 修改物品接口
    @PutMapping("/update")
    public ResultVO<String> updateItem(@RequestBody Item item) {
        rewardStoreService.updateItem(item);
        return ResultVO.success("Item updated successfully");
    }

    // 删除物品接口
    @DeleteMapping("/delete/{id}")
    public ResultVO<String> deleteItem(@PathVariable Long id) {
        rewardStoreService.deleteItem(id);
        return ResultVO.success("Item deleted successfully");
    }
}
