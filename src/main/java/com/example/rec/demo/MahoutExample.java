package com.example.rec.demo;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.util.List;

/**
 * 代码示例：基于用户的协同过滤
 */
public class MahoutExample {


    public static void main(String[] args) throws Exception {
        // 加载数据模型
        DataModel model = new FileDataModel(new File("data/ratings.csv"));




        // 创建用户相似度度量，使用欧几里得距离
        EuclideanDistanceSimilarity similarity = new EuclideanDistanceSimilarity(model);

        // 创建邻居对象，选取最相似的 2 个用户
        NearestNUserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

        // 创建推荐器
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // 获取用户 1 的前 3 个推荐
        List<RecommendedItem> recommendations = recommender.recommend(1, 3);

        // 打印推荐结果
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Recommended Item ID: " + recommendation.getItemID() + ", Score: " + recommendation.getValue());
        }
    }


    private FastByIDMap<PreferenceArray> getUserData() {
        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();

        PreferenceArray userPreferences = new GenericUserPreferenceArray(10);

        preferences.put(1L, userPreferences);


        return preferences;
    }

}
