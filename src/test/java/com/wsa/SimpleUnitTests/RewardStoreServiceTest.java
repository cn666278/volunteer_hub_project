package com.wsa.SimpleUnitTests;

import com.wsa.mapper.RewardStoreMapper;
import com.wsa.model.Item;
import com.wsa.model.RedeemRequest;
import com.wsa.model.RedeemResponse;
import com.wsa.service.RewardStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RewardStoreServiceTest {

    @Mock
    private RewardStoreMapper rewardStoreMapper;

    @InjectMocks
    private RewardStoreService rewardStoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetItems() {
        // Arrange
        Item item1 = new Item();
        Item item2 = new Item();
        when(rewardStoreMapper.getItems()).thenReturn(Arrays.asList(item1, item2));

        // Act
        List<Item> items = rewardStoreService.getItems();

        // Assert
        assertThat(items).hasSize(2);
        verify(rewardStoreMapper, times(1)).getItems();
    }

    @Test
    void testRedeemItemSuccess() {
        // Arrange
        RedeemRequest request = new RedeemRequest();
        request.setUserId(1L);
        request.setItemId(2L);

        Item item = new Item();
        item.setId(2L);
        item.setPointsRequired(50);

        when(rewardStoreMapper.getUserPoints(request.getUserId())).thenReturn(100);
        when(rewardStoreMapper.getItemById(request.getItemId())).thenReturn(item);

        // Act
        RedeemResponse response = rewardStoreService.redeemItem(request);

        // Assert
        assertThat(response.isSuccess()).isTrue();
        verify(rewardStoreMapper, times(1)).deductPoints(request.getUserId(), item.getPointsRequired());
        verify(rewardStoreMapper, times(1)).createRedemptionRecord(request.getUserId(), request.getItemId(), item.getPointsRequired());
    }

    @Test
    void testRedeemItemFailure() {
        // Arrange
        RedeemRequest request = new RedeemRequest();
        request.setUserId(1L);
        request.setItemId(2L);

        Item item = new Item();
        item.setId(2L);
        item.setPointsRequired(100);

        when(rewardStoreMapper.getUserPoints(request.getUserId())).thenReturn(50);
        when(rewardStoreMapper.getItemById(request.getItemId())).thenReturn(item);

        // Act
        RedeemResponse response = rewardStoreService.redeemItem(request);

        // Assert
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Insufficient points!");
        verify(rewardStoreMapper, times(0)).deductPoints(anyLong(), anyInt());
        verify(rewardStoreMapper, times(0)).createRedemptionRecord(anyLong(), anyLong(), anyInt());
    }

    @Test
    void testAddItem() {
        // Arrange
        Item item = new Item();
        item.setId(1L);
        item.setItemName("Test Item");

        // Act
        rewardStoreService.addItem(item);

        // Assert
        verify(rewardStoreMapper).addItem(item);
    }

    @Test
    void testUpdateItem() {
        // Arrange
        Item item = new Item();
        item.setId(1L);
        item.setItemName("Updated Item");

        // Act
        rewardStoreService.updateItem(item);

        // Assert
        verify(rewardStoreMapper).updateItem(item);
    }

    @Test
    void testDeleteItem() {
        // Arrange
        Long itemId = 1L;

        // Act
        rewardStoreService.deleteItem(itemId);

        // Assert
        verify(rewardStoreMapper).deleteItem(itemId);
    }
}
