package com.mhr.trivia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mhr.trivia.R;
import com.mhr.trivia.adapters.QuestionairesAdapter;
import com.mhr.trivia.model.Trivia.Question;

import java.util.ArrayList;

public class QuestionaireActivity extends AppCompatActivity {

    private ArrayList<Question> questionList;
    private RecyclerView recyclerViewQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        recyclerViewQuestions = (RecyclerView) findViewById(R.id.recycler_view_questions);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

        questionList = (ArrayList<Question>) getIntent().getSerializableExtra(getString(R.string.list));
        recyclerViewQuestions.setAdapter(new QuestionairesAdapter(questionList, R.layout.list_item_questionaires, this));

    }
}
