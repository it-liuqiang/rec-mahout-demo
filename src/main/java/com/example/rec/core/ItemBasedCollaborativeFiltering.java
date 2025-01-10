package com.example.rec.core;

import freemarker.ext.beans.DateModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  基于物品的协同过滤 (Item-Based Collaborative Filtering)
 * 原理：基于物品之间的相似性进行推荐。如果一个用户喜欢某个物品，系统会推荐与该物品相似的其他物品
 */
@Slf4j
public class ItemBasedCollaborativeFiltering extends CollaborativeFiltering {


    public ItemBasedCollaborativeFiltering(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public List<Long> recommend(Long userId) {
        List<Long> itemIds = new ArrayList<>();
        try {
            //获取内容相似程度
            ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            Recommender recommender = new GenericItemBasedRecommender(dataModel, similarity);
            List<RecommendedItem> recommendedItems = recommender.recommend(userId, 10);
            itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
        }catch (Exception e){
            log.error( "recommend error ", e.getMessage());
        }
        return itemIds;
    }

}
