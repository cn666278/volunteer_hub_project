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

    void addItem(Item item);

    void updateItem(Item item);

    void deleteItem(@Param("id") Long id);
}
