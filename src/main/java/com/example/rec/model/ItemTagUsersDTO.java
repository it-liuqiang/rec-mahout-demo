package com.example.rec.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ItemTagUsersDTO implements Serializable {


    private String itemId;

    private String tags;

    private Set<String> interactedUsers; // 交互过该物品的用户集合


}