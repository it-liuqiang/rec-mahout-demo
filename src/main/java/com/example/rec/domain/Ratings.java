package com.example.rec.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName ratings
 */
@TableName(value ="ratings")
public class Ratings implements Serializable {
    /**
     * 
     */
    @TableField(value = "user")
    private String user;

    /**
     * 
     */
    @TableField(value = "item")
    private String item;

    /**
     * 
     */
    @TableField(value = "rating")
    private Integer rating;

    /**
     * 
     */
    @TableField(value = "datetime")
    private Long datetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getUser() {
        return user;
    }

    /**
     * 
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 
     */
    public String getItem() {
        return item;
    }

    /**
     * 
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * 
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * 
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * 
     */
    public Long getDatetime() {
        return datetime;
    }

    /**
     * 
     */
    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Ratings other = (Ratings) that;
        return (this.getUser() == null ? other.getUser() == null : this.getUser().equals(other.getUser()))
            && (this.getItem() == null ? other.getItem() == null : this.getItem().equals(other.getItem()))
            && (this.getRating() == null ? other.getRating() == null : this.getRating().equals(other.getRating()))
            && (this.getDatetime() == null ? other.getDatetime() == null : this.getDatetime().equals(other.getDatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
        result = prime * result + ((getItem() == null) ? 0 : getItem().hashCode());
        result = prime * result + ((getRating() == null) ? 0 : getRating().hashCode());
        result = prime * result + ((getDatetime() == null) ? 0 : getDatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", user=").append(user);
        sb.append(", item=").append(item);
        sb.append(", rating=").append(rating);
        sb.append(", datetime=").append(datetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}