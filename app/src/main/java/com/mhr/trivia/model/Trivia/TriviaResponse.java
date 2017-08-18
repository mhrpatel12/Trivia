package com.mhr.trivia.model.Trivia;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by patelmih on 8/18/2017.
 */

public class TriviaResponse {
    @SerializedName("results")
    public List<Question> questionList;
    @SerializedName("response_code")
    public int responseCode;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
