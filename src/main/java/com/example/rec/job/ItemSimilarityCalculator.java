package com.example.rec.job;


import com.alibaba.fastjson.JSONObject;
import com.example.rec.model.ItemTagUsersDTO;

import java.util.HashSet;
import java.util.Set;

public class ItemSimilarityCalculator {
    // 计算两个集合的Jaccard相似度
    private static double jaccardSimilarity(Set<?> set1, Set<?> set2) {
        if (set1.isEmpty() && set2.isEmpty()) return 0.0;
        Set<Object> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<Object> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();
    }

    // 计算物品综合相似度
    public static double calculateSimilarity(ItemTagUsersDTO item1, ItemTagUsersDTO item2,
                                             double tagWeight, double userWeight) {
        // 标签相似度
        double tagSim = jaccardSimilarity(new HashSet<>(JSONObject.parseArray(item1.getTags()).toJavaList(String.class)),
                new HashSet<>(JSONObject.parseArray(item2.getTags()).toJavaList(String.class)));
        // 用户重叠相似度
        double userSim = jaccardSimilarity(item1.getInteractedUsers(), item2.getInteractedUsers());
        return tagWeight * tagSim + userWeight * userSim;
    }
}