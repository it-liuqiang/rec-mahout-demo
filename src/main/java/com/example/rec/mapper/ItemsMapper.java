package com.example.rec.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rec.domain.Items;
import com.example.rec.model.ItemTagUsersDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author it_li
 * @description 针对表【items】的数据库操作Mapper
 * @createDate 2025-03-07 14:39:47
 * @Entity generator.domain.Items
 */
@DS("rec_system")
@Mapper
public interface ItemsMapper extends BaseMapper<Items> {

    @Select("select item_id from items ORDER BY time_stamp desc limit #{cache_size}")
    List<String> getLatestItems(int cache_size);


    @Select("SELECT item_id FROM ( SELECT item_id, COUNT (*) AS feedback_count FROM feedback WHERE feedback_type IN ('buy') AND time_stamp >= NOW() - INTERVAL ${popularWindow} GROUP BY item_id ) T ORDER BY feedback_count DESC LIMIT #{cache_size}")
    List<String> geHotItems(int cache_size, String popularWindow);

    List<ItemTagUsersDTO> getItemTagAndUsers(String popularWindow);

}




