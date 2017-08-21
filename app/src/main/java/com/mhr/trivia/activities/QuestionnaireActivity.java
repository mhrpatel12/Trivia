package com.mhr.trivia.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mhr.trivia.R;
import com.mhr.trivia.adapters.QuestionnairesAdapter;
import com.mhr.trivia.interfaces.AnswerListener;
import com.mhr.trivia.model.Answer.Answer;
import com.mhr.trivia.model.Trivia.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionnaireActivity extends AppCompatActivity implements AnswerListener {

    private ArrayList<Question> questionList;
    private RecyclerView recyclerViewQuestions;
    private ArrayList<Answer> answerList;
    private AnswerListener answerListener;
    private Answer answerStatus = new Answer();
    private String questionnaireType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        answerListener = (AnswerListener) this;

        answerList = new ArrayList<>();
        recyclerViewQuestions = (RecyclerView) findViewById(R.id.recycler_view_questions);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

        questionList = (ArrayList<Question>) getIntent().getSerializableExtra(getString(R.string.list));
        questionnaireType = getIntent().getStringExtra(getString(R.string.type_of_questionaire));

        List<ArrayList<String>> listShuffledAnswers = new ArrayList<>();

        answerStatus.setAnswered(false);
        answerStatus.setAnswerCorrect(false);
        for (Question question : questionList) {    //ADD ALL ANSWERS INTO SEPARATE ARRAY AND SHUFFLE
            ArrayList<String> shuffledAnswers = new ArrayList<>();
            for (String answer : question.getIncorrect_answers()) {
                shuffledAnswers.add(answer);
            }
            shuffledAnswers.add(question.getCorrect_answer() + "");
            Collections.shuffle(shuffledAnswers);
            listShuffledAnswers.add(shuffledAnswers);
            answerList.add(answerStatus);
        }

        recyclerViewQuestions.setAdapter(new QuestionnairesAdapter(questionList, listShuffledAnswers, answerListener, questionnaireType, this));

        ((AppCompatButton) findViewById(R.id.buttonCompleteTrivia)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = 0;
                boolean isAllAnswersCompleted = false;
                for (Answer answer : answerList) {
                    if (answer.isAnswered()) {
                        isAllAnswersCompleted = true;
                        if (answer.isAnswerCorrect()) {
                            score++;
                        }
                    } else {
                        isAllAnswersCompleted = false;
                        Toast.makeText(QuestionnaireActivity.this, getString(R.string.error_incomplete_answers), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if (isAllAnswersCompleted) {
                    Snackbar.make(v, getString(R.string.message_score) + " " + score + " " + getString(R.string.message_score_out_of) + " " + answerList.size(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Done", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            }).show();
                }
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
