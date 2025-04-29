package com.example.rec.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class UserItemRatingDTO implements Serializable {
    private String userId;
    private Map<String, Double> itemRatings; // 物品ID -> 评分

    public UserItemRatingDTO(String userId, Map<String, Double> itemRatings) {
        this.userId = userId;
        this.itemRatings = itemRatings;
    }

    // Getters
    public String getUserId() { return userId; }
    public Map<String, Double> getItemRatings() { return itemRatings; }
}