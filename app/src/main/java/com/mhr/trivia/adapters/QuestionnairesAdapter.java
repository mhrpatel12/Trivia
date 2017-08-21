package com.mhr.trivia.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhr.trivia.R;
import com.mhr.trivia.interfaces.AnswerListener;
import com.mhr.trivia.model.Trivia.Question;
import com.mhr.trivia.ui.RelativeRadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mihir on 06-07-2017.
 */

public class QuestionnairesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Question> listQuestionnaires;
    private Context mContext;
    private List<ArrayList<String>> listShuffledAnswers;
    private AnswerListener answerListener;
    private String questionnaireType = "";

    public QuestionnairesAdapter(List<Question> listQuestionnaires, List<ArrayList<String>> listShuffledAnswers, AnswerListener answerListener, String questionnaireType, Context context) {
        this.listQuestionnaires = listQuestionnaires;
        this.listShuffledAnswers = listShuffledAnswers;
        this.mContext = context;
        this.answerListener = answerListener;
        this.questionnaireType = questionnaireType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (questionnaireType.equals(mContext.getString(R.string.type_multiple))) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_multiple_questionaires, parent, false);
            return new MultiChoiceViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_boolean_questionaires, parent, false);
            return new BooleanViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (questionnaireType.equals(mContext.getString(R.string.type_multiple))) {
            ((MultiChoiceViewHolder) holder).txtQuestion.setText(Html.fromHtml(listQuestionnaires.get(position).getQuestion() + ""));

            ((MultiChoiceViewHolder) holder).radioOption1.setText(Html.fromHtml(listShuffledAnswers.get(position).get(0)));
            ((MultiChoiceViewHolder) holder).radioOption2.setText(Html.fromHtml(listShuffledAnswers.get(position).get(1)));
            ((MultiChoiceViewHolder) holder).radioOption3.setText(Html.fromHtml(listShuffledAnswers.get(position).get(2)));
            ((MultiChoiceViewHolder) holder).radioOption4.setText(Html.fromHtml(listShuffledAnswers.get(position).get(3)));

            ((MultiChoiceViewHolder) holder).radioGroupMultiChoiceQuestions.setOnCheckedChangeListener(new RelativeRadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RelativeRadioGroup group, @IdRes int checkedId) {
                    AppCompatRadioButton radioButton = (AppCompatRadioButton) holder.itemView.findViewById(checkedId);
                    if (radioButton.getText().equals(Html.fromHtml(listQuestionnaires.get(position).getCorrect_answer()))) {
                        if (answerListener != null) {
                            answerListener.updateAnswer(position, true);
                        }
                    } else {
                        if (answerListener != null) {
                            answerListener.updateAnswer(position, false);
                        }
                    }
                }
            });
        } else if (questionnaireType.equals(mContext.getString(R.string.type_boolean))) {
            ((BooleanViewHolder) holder).txtQuestion.setText(Html.fromHtml(listQuestionnaires.get(position).getQuestion() + ""));

            ((BooleanViewHolder) holder).radioOptionTrue.setText(listShuffledAnswers.get(position).get(0));
            ((BooleanViewHolder) holder).radioOptionFalse.setText(listShuffledAnswers.get(position).get(1));

            ((BooleanViewHolder) holder).radioGroupBooleanQuestions.setOnCheckedChangeListener(new RelativeRadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RelativeRadioGroup group, @IdRes int checkedId) {
                    AppCompatRadioButton radioButton = (AppCompatRadioButton) holder.itemView.findViewById(checkedId);
                    if (radioButton.getText().equals(listQuestionnaires.get(position).getCorrect_answer())) {
                        if (answerListener != null) {
                            answerListener.updateAnswer(position, true);
                        }
                    } else {
                        if (answerListener != null) {
                            answerListener.updateAnswer(position, false);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listQuestionnaires.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class BooleanViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;

        AppCompatRadioButton radioOptionTrue;
        AppCompatRadioButton radioOptionFalse;

        RelativeRadioGroup radioGroupBooleanQuestions;

        public BooleanViewHolder(final View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);

            radioOptionTrue = (AppCompatRadioButton) itemView.findViewById(R.id.radioOptionTrue);
            radioOptionFalse = (AppCompatRadioButton) itemView.findViewById(R.id.radioOptionFalse);

            radioGroupBooleanQuestions = (RelativeRadioGroup) itemView.findViewById(R.id.radioGroupBooleanOptions);
        }
    }

    public class MultiChoiceViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        AppCompatRadioButton radioOption1;
        AppCompatRadioButton radioOption2;
        AppCompatRadioButton radioOption3;
        AppCompatRadioButton radioOption4;

        RelativeRadioGroup radioGroupMultiChoiceQuestions;

        public MultiChoiceViewHolder(final View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);

            radioOption1 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption1);
            radioOption2 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption2);
            radioOption3 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption3);
            radioOption4 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption4);

            radioGroupMultiChoiceQuestions = (RelativeRadioGroup) itemView.findViewById(R.id.radioGroupMultiOptions);
        }
    }
}
