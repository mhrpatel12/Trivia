package com.mhr.trivia.model.Categories;

import com.google.gson.annotations.SerializedName;

/**
 * Created by patelmih on 8/18/2017.
 */

public class Category {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
