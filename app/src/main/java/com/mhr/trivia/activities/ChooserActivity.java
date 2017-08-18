package com.mhr.trivia.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.mhr.trivia.R;
import com.mhr.trivia.model.Categories.CategoriesResponse;
import com.mhr.trivia.model.Categories.Category;
import com.mhr.trivia.model.Trivia.Question;
import com.mhr.trivia.model.Trivia.TriviaResponse;
import com.mhr.trivia.rest.ApiClient;
import com.mhr.trivia.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooserActivity extends AppCompatActivity {

    private final String TAG = ChooserActivity.class.getSimpleName();
    private Context mContext;
    private EditText edtNumberOfQuestions;
    private AppCompatSpinner spinnerCategory;
    private AppCompatSpinner spinnerDifficulty;
    private AppCompatSpinner spinnerType;

    private ProgressDialog mProgressDialog;

    private List<Category> categoriesResponseList;
    private ArrayAdapter<String> adapterCategories;
    private ArrayAdapter<String> adapterDifficulty;
    private ArrayAdapter<String> adapterType;

    private FloatingActionButton fab;
    private ApiInterface apiService;

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
    }

    private void initializeViews() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.message_fetching_categories));
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        edtNumberOfQuestions = (EditText) findViewById(R.id.edtNumberOfQuestions);
        spinnerCategory = (AppCompatSpinner) findViewById(R.id.spinnerCategory);
        spinnerDifficulty = (AppCompatSpinner) findViewById(R.id.spinnerDifficulty);
        spinnerType = (AppCompatSpinner) findViewById(R.id.spinnerType);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CategoriesResponse> categoriesServiceCall = apiService.getCategories();

        final ArrayList<String> listCategories = new ArrayList<>();
        final String[] listDifficulties = {getString(R.string.difficulty_easy), getString(R.string.difficulty_medium), getString(R.string.difficulty_difficult)};
        final String[] listTypes = {getString(R.string.type_multiple), getString(R.string.type_boolean)};
        adapterCategories = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, listCategories);
        adapterDifficulty = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, listDifficulties);
        adapterType = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, listTypes);

        spinnerCategory.setAdapter(adapterCategories);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        spinnerType.setAdapter(adapterType);
        categoriesServiceCall.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                categoriesResponseList = new ArrayList<Category>(response.body().getCategory());
                for (Category category : response.body().getCategory()) {
                    listCategories.add(category.getName());
                }
                adapterCategories.notifyDataSetChanged();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String categoryID = "";
                String difficulty = "";
                for (Category c : categoriesResponseList) {
                    if (c.getName() != null && c.getName().contains(spinnerCategory.getSelectedItem().toString())) {
                        categoryID = c.getId();
                    }
                }
                if (spinnerDifficulty.getSelectedItem().toString().equals(getString(R.string.difficulty_easy))) {
                    difficulty = getString(R.string.difficulty_easy_api);
                } else if (spinnerDifficulty.getSelectedItem().toString().equals(getString(R.string.difficulty_medium))) {
                    difficulty = getString(R.string.difficulty_medium_api);
                } else if (spinnerDifficulty.getSelectedItem().toString().equals(getString(R.string.difficulty_difficult))) {
                    difficulty = getString(R.string.difficulty_difficult_api);
                }
                Call<TriviaResponse> triviaServiceCall = apiService.getTrivia(
                        edtNumberOfQuestions.getText().toString().trim(),
                        categoryID,
                        difficulty,
                        spinnerType.getSelectedItem().toString().equals(getString(R.string.type_multiple)) ? "multiple" : "boolean");

                triviaServiceCall.enqueue(new Callback<TriviaResponse>() {
                    @Override
                    public void onResponse(Call<TriviaResponse> call, Response<TriviaResponse> response) {
                        questionList = new ArrayList<>(response.body().getQuestionList());
                        Intent intent = new Intent(mContext, QuestionaireActivity.class);
                        intent.putExtra(getString(R.string.list), new ArrayList<>(response.body().getQuestionList()));
                        intent.putExtra(getString(R.string.type_of_questionaire), spinnerType.getSelectedItem().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<TriviaResponse> call, Throwable t) {
                        Log.e(TAG, t.toString());
                        Snackbar.make(view, t.toString() + "", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
/**/
            }
        });
    }
}
