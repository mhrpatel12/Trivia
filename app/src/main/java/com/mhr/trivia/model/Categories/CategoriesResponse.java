package com.mhr.trivia.model.Categories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by patelmih on 8/18/2017.
 */

public class CategoriesResponse {
    @SerializedName("trivia_categories")
    public List<Category> category;

    public List<Category> getCategory() {
        return category;
    }
}
