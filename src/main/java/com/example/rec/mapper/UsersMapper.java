package com.example.rec.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rec.domain.Users;
import com.example.rec.model.UserTagItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author it_li
* @description 针对表【users】的数据库操作Mapper
* @createDate 2025-03-07 14:39:35
* @Entity generator.domain.Users
*/
@DS("rec_system")
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    List<UserTagItemDTO> getUserTagAndItems(String popularWindow);
}




