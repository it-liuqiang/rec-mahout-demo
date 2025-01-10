package com.example.rec.demo;

import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.util.List;


/**
 * 基于物品的协同过滤
 */
public class ItemBasedMahoutExample {
    public static void main(String[] args) throws Exception {
        // 加载数据模型
        DataModel model = new FileDataModel(new File("data/ratings.csv"));

        // 创建物品相似度度量，使用欧几里得距离
        EuclideanDistanceSimilarity similarity = new EuclideanDistanceSimilarity(model);

        // 创建推荐器
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        // 获取物品 1 的前 3 个推荐
        List<RecommendedItem> recommendations = recommender.recommend(1, 3);

        // 打印推荐结果
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Recommended Item ID: " + recommendation.getItemID() + ", Score: " + recommendation.getValue());
        }
    }

}
