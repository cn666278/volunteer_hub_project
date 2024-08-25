package com.wsa.lightweightmockmvctests;

import com.wsa.controller.RewardStoreController;
import com.wsa.model.Item;
import com.wsa.model.RedeemRequest;
import com.wsa.model.RedeemResponse;
import com.wsa.service.RewardStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardStoreController.class)
public class RewardStoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RewardStoreService rewardStoreService;

    @InjectMocks
    private RewardStoreController rewardStoreController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rewardStoreController).build();
    }

    @Test
    public void testGetItems_Success() throws Exception {
        // Given
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId(1L);
        item.setItemName("Reward Item");
        item.setPointsRequired(100);
        items.add(item);

        when(rewardStoreService.getItems()).thenReturn(items);

        // When & Then
        mockMvc.perform(get("/rewardstore/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("Reward Item"))
                .andExpect(jsonPath("$.data[0].pointsRequired").value(100));
    }

    @Test
    public void testRedeemItem_Success() throws Exception {
        // Given
        RedeemResponse response = new RedeemResponse(true, "Redemption successful!");
        when(rewardStoreService.redeemItem(any(RedeemRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/rewardstore/redeem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1, \"itemId\":1, \"quantity\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.success").value(true))
                .andExpect(jsonPath("$.data.message").value("Redemption successful!"));
    }

    @Test
    public void testRedeemItem_InsufficientPoints() throws Exception {
        // Given
        RedeemResponse response = new RedeemResponse(false, "Insufficient points!");
        when(rewardStoreService.redeemItem(any(RedeemRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/rewardstore/redeem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1, \"itemId\":1, \"quantity\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.success").value(false))
                .andExpect(jsonPath("$.data.message").value("Insufficient points!"));
    }

    @Test
    public void testAddItem_Success() throws Exception {
        // Given
        doNothing().when(rewardStoreService).addItem(any(Item.class));

        // When & Then
        mockMvc.perform(post("/rewardstore/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Item\", \"pointsRequired\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("Item added successfully"));
    }

    @Test
    public void testUpdateItem_Success() throws Exception {
        // Given
        doNothing().when(rewardStoreService).updateItem(any(Item.class));

        // When & Then
        mockMvc.perform(put("/rewardstore/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\":\"Updated Item\", \"pointsRequired\":150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("Item updated successfully"));
    }

    @Test
    public void testDeleteItem_Success() throws Exception {
        // Given
        doNothing().when(rewardStoreService).deleteItem(anyLong());

        // When & Then
        mockMvc.perform(delete("/rewardstore/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("Item deleted successfully"));
    }
}
