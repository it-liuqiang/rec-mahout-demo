//package com.example.rec.core;
//
//import com.example.rec.model.ItemTagUsersDTO;
//import com.example.rec.model.UserTagItemDTO;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class ItemRecommender {
//    private final Map<String, ItemTagUsersDTO> items;
//    private final Map<String, UserTagItemDTO> users;
//    private final Map<String, Map<String, Double>> similarityMatrix;
//
//    public ItemRecommender(Map<String, ItemTagUsersDTO> items, Map<String, UserTagItemDTO> users) {
//        this.items = items;
//        this.users = users;
//        this.similarityMatrix = buildSimilarityMatrix();
//    }
//
//    // 构建物品相似度矩阵
//    private Map<String, Map<String, Double>> buildSimilarityMatrix() {
//        Map<String, Map<String, Double>> matrix = new HashMap<>();
//        List<ItemTagUsersDTO> itemList = new ArrayList<>(items.values());
//        double tagWeight = 0.6, userWeight = 0.4;
//
//        for (ItemTagUsersDTO item1 : itemList) {
//            Map<String, Double> row = new HashMap<>();
//            for (ItemTagUsersDTO item2 : itemList) {
//                if (item1.getItemId().equals(item2.getItemId())) continue;
//                double sim = calculateItemSimilarity(item1, item2, tagWeight, userWeight);
//                row.put(item2.getItemId(), sim);
//            }
//            matrix.put(item1.getItemId(), row);
//        }
//        return matrix;
//    }
//
//    // 计算物品相似度（Jaccard加权）
//    private double calculateItemSimilarity(ItemTagUsersDTO item1, ItemTagUsersDTO item2,
//                                        double tagWeight, double userWeight) {
//        Set<String> commonTags = new HashSet<>(item1.getTags());
//        commonTags.retainAll(item2.getTags());
//        double tagSim = (double) commonTags.size() /
//                       (item1.getTags().size() + item2.getTags().size() - commonTags.size());
//
//        Set<String> commonUsers = new HashSet<>(item1.getInteractedUsers());
//        commonUsers.retainAll(item2.getInteractedUsers());
//        double userSim = (double) commonUsers.size() /
//                        (item1.getInteractedUsers().size() + item2.getInteractedUsers().size() - commonUsers.size());
//
//        return tagWeight * tagSim + userWeight * userSim;
//    }
//
//
//
//
//
//    // 生成推荐列表
//    public List<String> recommendItems(String userId, int topN) {
//        UserTagItemDTO user = users.get(userId);
//        if (user == null) return handleColdStart(); // 冷启动处理
//
//        Map<String, Double> candidateScores = new HashMap<>();
//        for (String interactedItemId : user.getInteractedItems()) {
//            Map<String, Double> similarItems = similarityMatrix.get(interactedItemId);
//            if (similarItems == null) continue;
//
//            similarItems.forEach((itemId, score) -> {
//                if (!user.getInteractedItems().contains(itemId)) {
//                    candidateScores.merge(itemId, score, Double::sum);
//                }
//            });
//        }
//
//        // 按得分排序并取TopN
//        return candidateScores.entrySet().stream()
//            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
//            .limit(topN)
//            .map(Map.Entry::getKey)
//            .collect(Collectors.toList());
//    }
//
//    // 冷启动处理（推荐热门物品）
//    private List<String> handleColdStart() {
//        return items.values().stream()
//            .sorted(Comparator.comparingInt(item -> -item.getInteractedUsers().size()))
//            .limit(5)
//            .map(ItemTagUsersDTO::getItemId)
//            .collect(Collectors.toList());
//    }
//}