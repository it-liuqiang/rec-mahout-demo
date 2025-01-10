package com.example.rec.core;

import freemarker.ext.beans.DateModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于隐语义模型的推荐 (Model-Based Collaborative Filtering)
 * 原理：通过机器学习技术，使用隐语义模型（如矩阵分解）挖掘用户-物品交互矩阵中的潜在模式。
 */
@Slf4j
public class ModelBasedCollaborativeFiltering extends CollaborativeFiltering {


    public ModelBasedCollaborativeFiltering(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public List<Long> recommend(Long userId) {
        List<Long> itemIds = new ArrayList<>();

        try {
            Factorizer factorizer = new ALSWRFactorizer(dataModel, 10, 0.05, 10);
            Recommender recommender = new SVDRecommender(dataModel, factorizer);
            List<RecommendedItem> recommendedItems = recommender.recommend(userId, 3);
            itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
        }catch (Exception e){
            log.error( "recommend error ", e.getMessage());
        }

        return itemIds;
    }


}
