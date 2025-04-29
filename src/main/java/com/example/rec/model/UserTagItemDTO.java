package com.example.rec.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserTagItemDTO implements Serializable {

    private String userId;
    private String tags;       // 用户拥有的标签集合
    private Set<String> items;      // 用户交互的物品集合
}
