package com.example.rec.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rec.domain.Users;
import com.example.rec.mapper.FeedbackMapper;
import com.example.rec.model.UserTagItemDTO;
import com.example.rec.service.UsersService;
import com.example.rec.mapper.UsersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author it_li
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2025-03-07 14:39:35
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    @Resource
    FeedbackMapper feedbackMapper;
    @Resource
    UsersMapper usersMapper;

    @Override
    public Map<String, Map<String, Double>> getUserItemRatings(String popular_window) {
        List<JSONObject> data = feedbackMapper.getUserItemRatings(popular_window);
        Map<String, Map<String, Double>> itemRatings = new HashMap<>();
        for (JSONObject d : data) {
            String userId = d.getString("user_id");
            if (itemRatings.containsKey(userId)) {
                itemRatings.get(userId).put(d.getString("item_id"), d.getDouble("rating"));
            } else {
                Map<String, Double> ratingMap = new HashMap<>();
                ratingMap.put(d.getString("item_id"), d.getDouble("rating"));
                itemRatings.put(userId, ratingMap);
            }
        }
        return itemRatings;
    }

    @Override
    public List<UserTagItemDTO> getUserTagAndItems(String popularWindow) {
        return usersMapper.getUserTagAndItems(popularWindow);
    }
}




