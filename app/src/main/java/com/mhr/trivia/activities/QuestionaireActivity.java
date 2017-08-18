package com.mhr.trivia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mhr.trivia.R;
import com.mhr.trivia.adapters.QuestionnairesAdapter;
import com.mhr.trivia.interfaces.AnswerListner;
import com.mhr.trivia.model.Answer.Answer;
import com.mhr.trivia.model.Trivia.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionaireActivity extends AppCompatActivity implements AnswerListner {

    private ArrayList<Question> questionList;
    private RecyclerView recyclerViewQuestions;
    private ArrayList<Answer> answerList;
    private AnswerListner answerListner;
    private Answer answerStatus = new Answer();
    private String questionnaireType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        answerListner = (AnswerListner) this;

        answerList = new ArrayList<>();
        recyclerViewQuestions = (RecyclerView) findViewById(R.id.recycler_view_questions);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

        questionList = (ArrayList<Question>) getIntent().getSerializableExtra(getString(R.string.list));
        questionnaireType = getIntent().getStringExtra(getString(R.string.type_of_questionaire));

        List<ArrayList<String>> listshuffledAnswers = new ArrayList<>();

        answerStatus.setAnswered(false);
        answerStatus.setAnswerCorrect(false);
        for (Question question : questionList) {
            ArrayList<String> shuffledAnswers = new ArrayList<>();
            for (String answer : question.getIncorrect_answers()) {
                shuffledAnswers.add(answer);
            }
            shuffledAnswers.add(question.getCorrect_answer() + "");
            Collections.shuffle(shuffledAnswers);
            listshuffledAnswers.add(shuffledAnswers);
            answerList.add(answerStatus);
        }

        recyclerViewQuestions.setAdapter(new QuestionnairesAdapter(questionList, listshuffledAnswers, answerListner, questionnaireType, R.layout.list_item_questionaires, this));

        ((AppCompatButton) findViewById(R.id.buttonCompleteTrivia)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = 0;
                for (Answer answer : answerList) {
                    if (answer.isAnswered()) {
                        if (answer.isAnswerCorrect()) {
                            score++;
                        }
                    } else {
                        Toast.makeText(QuestionaireActivity.this, getString(R.string.error_incomplete_answers), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                Toast.makeText(QuestionaireActivity.this, getString(R.string.message_score) + score, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void updateAnswer(int position, boolean answer) {
        Answer answer1 = new Answer();
        answer1.setAnswered(true);
        answer1.setAnswerCorrect(answer);
        answerList.set(position, answer1);
    }
}
