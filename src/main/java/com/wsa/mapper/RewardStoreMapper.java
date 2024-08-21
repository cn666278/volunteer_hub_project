package com.wsa.mapper;

import com.wsa.model.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RewardStoreMapper {

    List<Item> getItems();

    int getUserPoints(@Param("userId") long userId);

    Item getItemById(@Param("itemId") long itemId);

    void deductPoints(@Param("userId") long userId, @Param("points") int points);

    void createRedemptionRecord(@Param("userId") long userId, @Param("itemId") long itemId, @Param("pointsUsed") int pointsUsed);

    // 新增物品
    void addItem(Item item);

    // 修改物品
    void updateItem(Item item);

    // 删除物品
    void deleteItem(@Param("id") Long id);
}
