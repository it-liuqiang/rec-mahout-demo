package com.example.rec.service;

import org.apache.mahout.cf.taste.common.TasteException;

import java.util.List;

public interface RecommendService {

    List<Long> recommend(Long userId);


}