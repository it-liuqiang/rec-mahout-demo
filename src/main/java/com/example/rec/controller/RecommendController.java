package com.example.rec.controller;


import com.example.rec.model.ApiResponse;
import com.example.rec.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 推荐系统 对外接口请求类
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class RecommendController {


    @Resource
    private RecommendService recommendService;



    @RequestMapping("/recommend/{userId}")
    public ApiResponse recommend(@PathVariable Long userId) {
        return ApiResponse.success(recommendService.recommend(userId));
    }
}
