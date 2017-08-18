package com.mhr.trivia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhr.trivia.R;
import com.mhr.trivia.model.Trivia.Question;

import java.util.List;

/**
 * Created by Mihir on 06-07-2017.
 */

public class QuestionairesAdapter extends RecyclerView.Adapter<QuestionairesAdapter.RouteViewHolder> {

    private List<Question> listQuestionaires;
    private int rowLayout;
    private Context mContext;

    public QuestionairesAdapter(List<Question> listQuestionaires, int rowLayout, Context context) {
        this.listQuestionaires = listQuestionaires;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionairesAdapter.RouteViewHolder holder, final int position) {
        holder.txtQuestion.setText(listQuestionaires.get(position).getQuestion() + "");

        holder.txtOption1.setText(listQuestionaires.get(position).getIncorrect_answers().get(0));
        holder.txtOption2.setText(listQuestionaires.get(position).getIncorrect_answers().get(1));
        holder.txtOption3.setText(listQuestionaires.get(position).getIncorrect_answers().get(2));
        holder.txtOption4.setText(listQuestionaires.get(position).getCorrect_answer());
    }

    @Override
    public int getItemCount() {
        return listQuestionaires.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        TextView txtOption1;
        TextView txtOption2;
        TextView txtOption3;
        TextView txtOption4;

        TextView txtOptionTrue;
        TextView txtOptionFalse;

        public RouteViewHolder(View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);

            txtOption1 = (TextView) itemView.findViewById(R.id.txtOption1);
            txtOption2 = (TextView) itemView.findViewById(R.id.txtOption2);
            txtOption3 = (TextView) itemView.findViewById(R.id.txtOption3);
            txtOption4 = (TextView) itemView.findViewById(R.id.txtOption4);

            txtOptionTrue = (TextView) itemView.findViewById(R.id.txtOptionTrue);
            txtOptionFalse = (TextView) itemView.findViewById(R.id.txtOptionFalse);
        }
    }
}
