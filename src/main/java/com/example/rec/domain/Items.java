package com.example.rec.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @TableName items
 */
@TableName(value = "items")
@Data
public class Items implements Serializable {
    /**
     *
     */
    @TableId(value = "item_id")
    private String itemId;

    /**
     *
     */
    @TableField(value = "is_hidden")
    private Boolean isHidden;

    /**
     *
     */
    @TableField(value = "categories")
    private String categories;

    /**
     *
     */
    @TableField(value = "time_stamp")
    private Date timeStamp;

    /**
     *
     */
    @TableField(value = "labels")
    private List<String> labels;

    /**
     *
     */
    @TableField(value = "comment")
    private String comment;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public void setLabels(String labels) {
        this.labels = JSONObject.parseArray(labels).toJavaList(String.class);
    }
}