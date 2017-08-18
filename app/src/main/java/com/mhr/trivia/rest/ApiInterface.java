package com.mhr.trivia.rest;

import com.mhr.trivia.model.Categories.CategoriesResponse;
import com.mhr.trivia.model.Trivia.TriviaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("api_category.php?")
    Call<CategoriesResponse> getCategories();

    @GET("api.php?")
    Call<TriviaResponse> getTrivia(
            @Query("amount") String amount,
            @Query("category") String category,
            @Query("difficulty") String difficulty,
            @Query("type") String type);
}
