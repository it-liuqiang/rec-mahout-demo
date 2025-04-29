//package com.example.rec.job;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.rec.domain.Items;
//import com.example.rec.model.ItemTagUsersDTO;
//import com.example.rec.model.UserItemRatingDTO;
//import com.example.rec.model.UserTagItemDTO;
//import com.example.rec.service.ItemsService;
//import com.example.rec.service.UsersService;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//
///**
// * 特征工程
// */
//@Component
//public class FixedDelayTask {
//
//
//    @Resource
//    private ItemsService itemsService;
//    @Resource
//    private UsersService usersService;
//
//    //对静态商品数据集预计算并缓存IDF值，避免重复计算：
//    private static Map<String, Double> idfCache = new ConcurrentHashMap<>();
//
//
//    //    private final static int MAX_SIZE = 100; //相似商品集大小
//
//    private final static int cache_size = 100;  //推荐/热门/最新项目的缓存大小。默认值为 100
//    private final static String popular_window = "'720 h'"; //热门商品的时间窗口。默认值为 4320h。
//
//
//    /**
//     * # The type of neighbors for items. There are three types:
//     * #   similar: Neighbors are found by number of common labels.
//     * #   related: Neighbors are found by number of common users.
//     * #   auto: Neighbors are found by number of common labels and users.
//     * # The default value is "auto".
//     */
//    private final static String recommend_item_neighbors = "auto"; //反馈有效日期
//
//    /**
//     * # The type of neighbors for users. There are three types:
//     * #   similar: Neighbors are found by number of common labels.
//     * #   related: Neighbors are found by number of common favorite items.
//     * #   auto: Neighbors are found by number of common labels and favorite items.
//     * # The default value is "auto".
//     */
//    private final static String recommend_user_neighbors = "auto"; //反馈有效日期
//
//    /**
//     * 最热物品
//     */
//    @Scheduled(fixedDelay = 30000) // 上次任务完成后等待 5 秒再执行
//    public void hotItem() {
//        List<String> hotItem = itemsService.hotItem(cache_size, popular_window);
//        System.out.println("最热物品：" + JSONObject.toJSONString(hotItem));
//
//    }
//
//    /**
//     * 最新物品
//     */
//    @Scheduled(fixedDelay = 30000) // 上次任务完成后等待 5 秒再执行
//    public void latestItem() {
//        List<String> latestItems = itemsService.getLatestItems(cache_size);
//        System.out.println("最新物品：" + JSONObject.toJSONString(latestItems));
//    }
//
//    /**
//     * 用户相似度计算
//     * 1. 相似： 根据用户之间的标签重叠程度来计算相似度
//     * 2. 相关： 根据用户之间的物品重叠程度来计算相似度。
//     * 3. 自动： 根据用户之间的标签重叠程度和物品重叠程度来计算相似度。
//     */
//    @Scheduled(fixedDelay = 5000) // 上次任务完成后等待 5 秒再执行
//    public void runUserSimilarity() {
//        if (recommend_user_neighbors.equals("similar")) {
//
//
//        } else if (recommend_user_neighbors.equals("related")) {
//            Map<String, Map<String, Double>> userItemRatings = usersService.getUserItemRatings(popular_window);
//            List<UserItemRatingDTO> collect = userItemRatings.entrySet().stream()
//                    .map(entry -> new UserItemRatingDTO(entry.getKey(), entry.getValue())).collect(Collectors.toList());
//            // 1. 获取用户对物品的评分
//            Map<String, Map<String, Double>> stringMapMap = calculateUserSimilarities(collect);
//            System.out.println(stringMapMap);
//
//        } else if (recommend_user_neighbors.equals("auto")) {
//
//            List<UserTagItemDTO> userTagAndItems = usersService.getUserTagAndItems(popular_window);
//            // 2. 权重配置（可调整）
//            double tagWeight = 0.6;   // 标签权重60%
//            double itemWeight = 0.4;  // 物品权重40%
//
//            // 3. 计算相似度矩阵
//            Map<String, Map<String, Double>> similarityMatrix = new HashMap<>();
//
//            userTagAndItems.parallelStream().forEach(u1 -> {
//                Map<String, Double> similarities = userTagAndItems.parallelStream()
//                        .filter(u2 -> !u1.getUserId().equals(u2.getUserId()))
//                        .collect(Collectors.toMap(
//                                UserTagItemDTO::getUserId,
//                                u2 -> SimilarityCalculator.combinedSimilarity(u1, u2, tagWeight, itemWeight)
//                        ));
//                similarityMatrix.put(u1.getUserId(), similarities);
//            });
//
////            // 4. 输出Top3相似用户
////            similarityMatrix.forEach((userId, similarities) -> {
////                System.out.println("用户 " + userId + " 的相似用户:");
////                similarities.entrySet().stream().filter(Entry -> Entry.getValue() > 0)
////                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
////                        .limit(10)
////                        .forEach(entry ->
////                                System.out.printf("  %s (相似度: %.2f)%n", entry.getKey(), entry.getValue())
////                        );
////            });
//        }
//    }
//
//
//    /**
//     * 物品相似度计算
//     * 1. 相似： 根据物品之间的标签重叠程度来计算相似度
//     * 2. 相关： 根据物品之间用户的重叠程度计算相似度
//     * 3.
//     */
//    @Scheduled(fixedDelay = 5000) // 上次任务完成后等待 5 秒再执行
//    public void runItemSimilarity() {
//
//        if ("similar".equals(recommend_item_neighbors)) {
//            List<Items> list = itemsService.list();
//            // 2.  数据预处理
//            // 3. 计算TF-IDF向量
//            Map<String, Map<String, Double>> tfidfVectors = tfidfVectorize(list);
//            // 4. 计算相似度矩阵
//            Map<String, Map<String, Double>> similarityMatrix = new HashMap<>();
//            List<String> ids = list.stream()
//                    .map(Items::getItemId).collect(Collectors.toList());
//            //使用Java Stream并行处理相似度计算
//            ids.parallelStream().forEach(id1 -> {
//                Map<String, Double> row = new ConcurrentHashMap<>();
//                ids.stream().filter(id2 -> !id1.equals(id2)).forEach(id2 -> {
//                    double sim = cosineSimilarity(tfidfVectors.get(id1), tfidfVectors.get(id2));
//                    row.put(id2, sim);
//                });
//                similarityMatrix.put(id1, row);
//            });
//            // 5. 输出Top3相似商品
////        similarityMatrix.forEach((id, similarities) -> {
////            System.out.println("商品 " + id + " 的相似商品:");
////            similarities.entrySet().stream()
////                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).filter(e -> e.getValue() > 0)
//////                    .limit(MAX_SIZE)
////                    .forEach(e -> System.out.printf("  %s (相似度: %.2f)%n",
////                            e.getKey(), e.getValue()));
////        });
//            System.out.println("FixedDelay 任务执行：" + list.size());
//        } else if ("related".equals(recommend_item_neighbors)) {
//
//
//        } else if ("auto".equals(recommend_item_neighbors)) {
//
//            List<ItemTagUsersDTO> itemList = itemsService.getItemTagAndUsers(popular_window);
//            // 2. 权重配置
//            double tagWeight = 0.6;
//            double userWeight = 0.4;
//
//            // 3. 计算所有物品对的相似度
//            Map<String, Map<String, Double>> similarityMatrix = new HashMap<>();
//
//            // 使用并行流处理
//            similarityMatrix = itemList.parallelStream().collect(Collectors.toMap(
//                    item -> item.getItemId(),
//                    item -> itemList.parallelStream()
//                            .filter(other -> !item.getItemId().equals(other.getItemId()))
//                            .collect(Collectors.toMap(
//                                    other -> other.getItemId(),
//                                    other -> ItemSimilarityCalculator.calculateSimilarity(
//                                            item, other, tagWeight, userWeight)
//                            ))
//            ));
//
//            // 4. 输出结果
//            similarityMatrix.forEach((itemId, similarities) -> {
//                System.out.println("物品 " + itemId + " 的相似物品:");
//                similarities.entrySet().stream().filter(entry -> entry.getValue() > 0.01)
//                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                        .forEach(entry ->
//                                System.out.printf("  %s (相似度: %.2f)%n", entry.getKey(), entry.getValue())
//                        );
//            });
//        }
//
//        System.out.println("FixedDelay 任务执行时间：" + System.currentTimeMillis());
//    }
//
//
//    // TF-IDF向量化
//    public static Map<String, Map<String, Double>> tfidfVectorize(List<Items> products) {
//        // 构建词汇表
//        Set<String> vocabulary = products.stream()
//                .flatMap(p -> p.getLabels().stream())
//                .collect(Collectors.toSet());
//
//        // 计算IDF
//        Map<String, Double> idf = new HashMap<>();
//        int totalDocs = products.size();
//        for (String term : vocabulary) {
//            long docsWithTerm = products.stream()
//                    .filter(p -> p.getLabels().contains(term))
//                    .count();
//            idf.put(term, Math.log((double) totalDocs / (docsWithTerm + 1)));
//        }
//
//        // 计算TF-IDF
//        Map<String, Map<String, Double>> tfidf = new HashMap<>();
//        for (Items p : products) {
//            Map<String, Double> tf = p.getLabels().stream()
//                    .collect(Collectors.groupingBy(Function.identity(),
//                            Collectors.summingDouble(e -> 1.0 / p.getLabels().size())));
//
//            Map<String, Double> vec = new HashMap<>();
//            tf.forEach((term, tfVal) ->
//                    vec.put(term, tfVal * idf.get(term)));
//            tfidf.put(p.getItemId(), vec);
//        }
//        return tfidf;
//    }
//
//    // 余弦相似度计算
//    public static double cosineSimilarity(
//            Map<String, Double> vec1,
//            Map<String, Double> vec2) {
//
//        Set<String> commonTerms = new HashSet<>(vec1.keySet());
//        commonTerms.retainAll(vec2.keySet());
//
//        double dotProduct = commonTerms.stream()
//                .mapToDouble(term -> vec1.get(term) * vec2.get(term))
//                .sum();
//
//        double norm1 = Math.sqrt(vec1.values().stream()
//                .mapToDouble(v -> v * v).sum());
//        double norm2 = Math.sqrt(vec2.values().stream()
//                .mapToDouble(v -> v * v).sum());
//
//        return dotProduct / (norm1 * norm2);
//    }
//
//
//    /**
//     * 计算所有用户的相似度矩阵
//     */
//    public static Map<String, Map<String, Double>> calculateUserSimilarities(List<UserItemRatingDTO> users) {
//        Map<String, Map<String, Double>> similarityMatrix = new HashMap<>();
//
//        users.parallelStream().forEach(id1 -> {
//            Map<String, Double> similarities = new ConcurrentHashMap<>();
//            users.stream().filter(id2 -> !id1.equals(id2)).forEach(id2 -> {
//                double sim = cosineSimilarity(id1.getItemRatings(), id2.getItemRatings());
//                if (sim > 0) {
//                    similarities.put(id2.getUserId(), sim);
//                }
//            });
//            similarityMatrix.put(id1.getUserId(), similarities);
//        });
////        for (UserDTO u1 : users) {
////            Map<String, Double> similarities = new HashMap<>();
////            for (UserDTO u2 : users) {
////                if (u1.getUserId().equals(u2.getUserId())) continue; // 跳过自身
////                double sim = cosineSimilarity(u1.getItemRatings(), u2.getItemRatings());
////                similarities.put(u2.getUserId(), sim);
////            }
////            similarityMatrix.put(u1.getUserId(), similarities);
////        }
//        return similarityMatrix;
//    }
//
//
//    /**
//     * 为新用户提供热门推荐，直至积累足够行为数据。
//     *
//     * @param users
//     * @param topN
//     * @return
//     */
//    public static List<String> getPopularItems(List<UserItemRatingDTO> users, int topN) {
//        Map<String, Long> itemCounts = users.stream()
//                .flatMap(u -> u.getItemRatings().keySet().stream())
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//
//        return itemCounts.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .limit(topN)
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }
//
//
//    /**
//     * 将用户评分归一化到同一尺度（如Z-Score标准化）。
//     *
//     * @param ratings
//     * @return
//     */
//    public static Map<String, Double> normalizeRatings(Map<String, Double> ratings) {
//        double mean = ratings.values().stream().mapToDouble(v -> v).average().orElse(0.0);
//        double stdDev = Math.sqrt(ratings.values().stream()
//                .mapToDouble(v -> Math.pow(v - mean, 2)).average().orElse(0.0));
//
//        return ratings.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        e -> (e.getValue() - mean) / stdDev
//                ));
//    }
//}
