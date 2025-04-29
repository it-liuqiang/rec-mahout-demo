package com.example.rec.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rec.domain.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author it_li
* @description 针对表【feedback】的数据库操作Mapper
* @createDate 2025-03-07 14:39:51
* @Entity generator.domain.Feedback
*/
@DS("rec_system")
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

    List<JSONObject> getUserItemRatings(String day);
}




