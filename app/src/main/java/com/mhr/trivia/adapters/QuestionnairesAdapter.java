package com.mhr.trivia.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class QuestionnairesAdapter extends RecyclerView.Adapter<QuestionnairesAdapter.RouteViewHolder> {

    private List<Question> listQuestionnaires;
    private int rowLayout;
    private Context mContext;
    private List<ArrayList<String>> listShuffledAnswers;
    private AnswerListener answerListener;
    private String questionnaireType = "";

    public QuestionnairesAdapter(List<Question> listQuestionnaires, List<ArrayList<String>> listShuffledAnswers, AnswerListener answerListener, String questionnaireType, int rowLayout, Context context) {
        this.listQuestionnaires = listQuestionnaires;
        this.listShuffledAnswers = listShuffledAnswers;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.answerListener = answerListener;
        this.questionnaireType = questionnaireType;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionnairesAdapter.RouteViewHolder holder, final int position) {
        holder.txtQuestion.setText(Html.fromHtml(listQuestionnaires.get(position).getQuestion() + ""));

        if (questionnaireType.equals(mContext.getString(R.string.type_multiple))) {
            holder.layoutMultipleChoiceQuestions.setVisibility(View.VISIBLE);
            holder.layoutBooleanQuestions.setVisibility(View.GONE);

            holder.radioOption1.setText(Html.fromHtml(listShuffledAnswers.get(position).get(0)));
            holder.radioOption2.setText(Html.fromHtml(listShuffledAnswers.get(position).get(1)));
            holder.radioOption3.setText(Html.fromHtml(listShuffledAnswers.get(position).get(2)));
            holder.radioOption4.setText(Html.fromHtml(listShuffledAnswers.get(position).get(3)));

            holder.radioGroupMultiChoiceQuestions.setOnCheckedChangeListener(new RelativeRadioGroup.OnCheckedChangeListener() {
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
            holder.layoutMultipleChoiceQuestions.setVisibility(View.GONE);
            holder.layoutBooleanQuestions.setVisibility(View.VISIBLE);

            holder.radioOptionTrue.setText(listShuffledAnswers.get(position).get(0));
            holder.radioOptionFalse.setText(listShuffledAnswers.get(position).get(1));

            holder.radioGroupBooleanQuestions.setOnCheckedChangeListener(new RelativeRadioGroup.OnCheckedChangeListener() {
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

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        AppCompatRadioButton radioOption1;
        AppCompatRadioButton radioOption2;
        AppCompatRadioButton radioOption3;
        AppCompatRadioButton radioOption4;

        AppCompatRadioButton radioOptionTrue;
        AppCompatRadioButton radioOptionFalse;

        RelativeRadioGroup radioGroupMultiChoiceQuestions;
        RelativeRadioGroup radioGroupBooleanQuestions;

        LinearLayout layoutMultipleChoiceQuestions;
        LinearLayout layoutBooleanQuestions;

        public RouteViewHolder(final View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);

            radioOption1 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption1);
            radioOption2 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption2);
            radioOption3 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption3);
            radioOption4 = (AppCompatRadioButton) itemView.findViewById(R.id.radioOption4);

            radioOptionTrue = (AppCompatRadioButton) itemView.findViewById(R.id.radioOptionTrue);
            radioOptionFalse = (AppCompatRadioButton) itemView.findViewById(R.id.radioOptionFalse);

            radioGroupMultiChoiceQuestions = (RelativeRadioGroup) itemView.findViewById(R.id.radioGroupMultiOptions);
            radioGroupBooleanQuestions = (RelativeRadioGroup) itemView.findViewById(R.id.radioGroupBooleanOptions);

            layoutMultipleChoiceQuestions = (LinearLayout) itemView.findViewById(R.id.layoutMultiChoice);
            layoutBooleanQuestions = (LinearLayout) itemView.findViewById(R.id.layoutBoolean);
        }
    }
}
