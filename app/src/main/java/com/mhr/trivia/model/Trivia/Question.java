package com.mhr.trivia.model.Trivia;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by patelmih on 8/18/2017.
 */

public class Question implements Serializable {
    @SerializedName("type")
    public String type;
    @SerializedName("question")
    public String question;
    @SerializedName("correct_answer")
    public String correct_answer;
    @SerializedName("incorrect_answers")
    public List<String> incorrect_answers;

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }
}
