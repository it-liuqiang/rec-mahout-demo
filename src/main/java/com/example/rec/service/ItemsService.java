package com.example.rec.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rec.domain.Items;
import com.example.rec.model.ItemTagUsersDTO;

import java.util.List;

/**
* @author it_li
* @description 针对表【items】的数据库操作Service
* @createDate 2025-03-07 14:39:47
*/
public interface ItemsService extends IService<Items> {

    List<String> getLatestItems(int limit);


    List<String> hotItem(int cacheSize, String popularWindow);

    List<ItemTagUsersDTO> getItemTagAndUsers(String popularWindow);

}
