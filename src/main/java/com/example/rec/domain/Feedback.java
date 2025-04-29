package com.example.rec.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName feedback
 */
@TableName(value ="feedback")
@Data
public class Feedback implements Serializable {
    /**
     * 
     */
    @TableField(value = "feedback_type")
    private String feedbackType;

    /**
     * 
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 
     */
    @TableField(value = "item_id")
    private String itemId;

    /**
     * 
     */
    @TableField(value = "time_stamp")
    private Date timeStamp;

    /**
     *
     */
    @TableField(value = "comment")
    private String comment;


}