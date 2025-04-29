package com.example.rec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rec.domain.Feedback;
import com.example.rec.service.FeedbackService;
import com.example.rec.mapper.FeedbackMapper;
import org.springframework.stereotype.Service;

/**
* @author it_li
* @description 针对表【feedback】的数据库操作Service实现
* @createDate 2025-03-07 14:39:51
*/
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback>
    implements FeedbackService{

}




