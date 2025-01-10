package com.example.rec.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.rec.core.ItemBasedCollaborativeFiltering;
import com.example.rec.core.ModelBasedCollaborativeFiltering;
import com.example.rec.core.UserBasedCollaborativeFiltering;
import com.example.rec.domain.Ratings;
import com.example.rec.mapper.RatingsMapper;
import com.example.rec.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于用户的协同过滤
 */
@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    private RatingsMapper ratingsMapper;

    @Override
    public List<Long> recommend(Long userId) {
        List<Long> itemIds = new ArrayList<>();
        try {
            //创建数据模型
            DataModel dataModel = this.createDataModel();

            List<Long> userBasedRecommend = new UserBasedCollaborativeFiltering(dataModel).recommend(userId);
            log.info("userBasedRecommend: {}",JSONObject.toJSONString(userBasedRecommend));
            itemIds.addAll(userBasedRecommend);
            List<Long> itemBasedRecommend = new ItemBasedCollaborativeFiltering(dataModel).recommend(userId);
            log.info("itemBasedRecommend: {}",JSONObject.toJSONString(itemBasedRecommend));
            itemIds.addAll(itemBasedRecommend);
            List<Long> modelBasedRecommend = new ModelBasedCollaborativeFiltering(dataModel).recommend(userId);
            log.info("modelBasedRecommend: {}",JSONObject.toJSONString(modelBasedRecommend));
            itemIds.addAll(modelBasedRecommend);

        }catch (Exception e){
            log.error( "recommend error ", e.getMessage());
        }
        return itemIds;
    }

    private DataModel createDataModel() {
        List<Ratings> ratings = ratingsMapper.selectList(null);

        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();


        Map<String, List<Ratings>> map  = ratings.stream().collect(Collectors.groupingBy(Ratings::getUser));
        Collection<List<Ratings>> list = map.values();

        for(List<Ratings> rating : list){
            GenericPreference[] array = new GenericPreference[rating.size()];

            for(int i = 0; i < rating.size(); i++){
                Ratings rat = rating.get(i);
                GenericPreference item = new GenericPreference(Long.parseLong(rat.getUser()), Long.parseLong(rat.getItem()), rat.getRating());
                array[i] = item;
            }
            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIdMap);
    }

}
