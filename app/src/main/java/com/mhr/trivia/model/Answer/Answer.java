package com.mhr.trivia.model.Answer;

/**
 * Created by Mihir on 19-08-2017.
 */

public class Answer {
    private boolean isAnswered;
    private boolean isAnswerCorrect;

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setAnswerCorrect(boolean answerCorrect) {
        isAnswerCorrect = answerCorrect;
    }
}
