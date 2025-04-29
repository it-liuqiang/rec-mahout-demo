package com.example.rec.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rec.domain.Users;
import com.example.rec.model.UserTagItemDTO;

import java.util.List;
import java.util.Map;

/**
* @author it_li
* @description 针对表【users】的数据库操作Service
* @createDate 2025-03-07 14:39:35
*/
public interface UsersService extends IService<Users> {

    Map<String, Map<String,Double>> getUserItemRatings(String popular_window);

    List<UserTagItemDTO> getUserTagAndItems(String popularWindow);



}
