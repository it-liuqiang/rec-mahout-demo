package com.example.rec.core;

import freemarker.ext.beans.DateModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于用户的协同过滤 (User-Based Collaborative Filtering)
 * 原理：基于用户之间的相似性进行推荐。如果一个用户与其他用户具有相似的兴趣，那么系统会推荐那些相似用户喜欢但当前用户未接触的物品。
 */
@Slf4j
public class UserBasedCollaborativeFiltering extends CollaborativeFiltering {


    public UserBasedCollaborativeFiltering(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public List<Long> recommend(Long userId) {
        List<Long> itemIds = new ArrayList<>();
        try {
            //获取用户相似程度
            UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
            //获取用户邻居
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(10, similarity, dataModel);
            //构建推荐器
            Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
            //推荐2个
            List<RecommendedItem> recommendedItems = recommender.recommend(userId, 10);
            itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
        }catch (Exception e){
            log.error( "recommend error ", e.getMessage());
        }
        return itemIds;

    }


}
