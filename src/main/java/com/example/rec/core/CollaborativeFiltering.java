package com.example.rec.core;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;

import java.util.List;

public abstract class CollaborativeFiltering {


    public CollaborativeFiltering(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public DataModel dataModel;

    public abstract List<Long> recommend(Long userId) throws TasteException;

//    public abstract DataModel buildDataModel();
}
