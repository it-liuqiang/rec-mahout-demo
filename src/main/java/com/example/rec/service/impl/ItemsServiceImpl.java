package com.example.rec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rec.domain.Items;
import com.example.rec.model.ItemTagUsersDTO;
import com.example.rec.service.ItemsService;
import com.example.rec.mapper.ItemsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author it_li
 * @description 针对表【items】的数据库操作Service实现
 * @createDate 2025-03-07 14:39:47
 */
@Service
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items>
        implements ItemsService {


    @Resource
    private ItemsMapper itemsMapper;

    @Override
    public List<String> getLatestItems(int cache_size) {
        return itemsMapper.getLatestItems(cache_size);
    }

    @Override
    public List<String> hotItem(int cacheSize, String popularWindow) {
        return itemsMapper.geHotItems(cacheSize, popularWindow);
    }

    @Override
    public List<ItemTagUsersDTO> getItemTagAndUsers(String popularWindow) {
        return itemsMapper.getItemTagAndUsers(popularWindow);
    }
}




