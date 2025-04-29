package com.example.rec.job;

import com.alibaba.fastjson.JSONObject;
import com.example.rec.model.UserTagItemDTO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimilarityCalculator {

    /**
     * 计算两个集合的Jaccard相似度
     */
    private static double jaccardSimilarity(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2); // 交集
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);          // 并集

        if (union.isEmpty()) return 0.0;
        return (double) intersection.size() / union.size();
    }

    /**
     * 综合相似度计算（标签和物品的加权平均）
     *
     * @param tagWeight  标签权重（0.0-1.0）
     * @param itemWeight 物品权重（0.0-1.0）
     */
    public static double combinedSimilarity(UserTagItemDTO user1, UserTagItemDTO user2,
                                            double tagWeight, double itemWeight) {

        double tagSim = jaccardSimilarity(new HashSet<>(JSONObject.parseArray(user1.getTags()).toJavaList(String.class)),
                new HashSet<>(JSONObject.parseArray(user2.getTags()).toJavaList(String.class)));
        double itemSim = jaccardSimilarity(user1.getItems(), user2.getItems());
        return tagWeight * tagSim + itemWeight * itemSim;
    }
}