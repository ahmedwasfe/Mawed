package com.raiyansoft.mawed.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.model.settings.questions.QuestionsData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsHolder> {

    private Activity activity;
    private List<QuestionsData> listQuestions;
    private LayoutInflater inflater;

    public QuestionsAdapter(Activity activity, List<QuestionsData> listQuestions) {
        this.activity = activity;
        this.listQuestions = listQuestions;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public QuestionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionsHolder(inflater.inflate(R.layout.row_questions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsHolder holder, int position) {
        QuestionsData question = listQuestions.get(position);
        if (question != null){
            holder.tvQuestionTitle.setText(question.getTitle());
            holder.tvQuestionDesc.setText(question.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return listQuestions.size();
    }

    static class QuestionsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_question_title)
        TextView tvQuestionTitle;
        @BindView(R.id.tv_question_desc)
        TextView tvQuestionDesc;

        public QuestionsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
