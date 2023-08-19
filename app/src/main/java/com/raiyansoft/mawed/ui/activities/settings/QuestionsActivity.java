package com.raiyansoft.mawed.ui.activities.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.QuestionsAdapter;
import com.raiyansoft.mawed.model.settings.questions.Questions;
import com.raiyansoft.mawed.model.settings.questions.QuestionsData;
import com.raiyansoft.mawed.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {
    private static final String TAG = QuestionsActivity.class.getSimpleName();

    @BindView(R.id.recycler_questions)
    RecyclerView recyclerQuestions;
    @BindView(R.id.pb_loading)
    ProgressBar loading;

    private List<QuestionsData> listQuestions;

    @OnClick(R.id.iv_back)
    void onBackClick() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        initUI();
        getQuestions();
    }

    private void getQuestions() {

        loading.setVisibility(View.VISIBLE);

        HelperMethods.getMawedAPI()
                .getQuestions(HelperMethods.getAppLanguage(this))
                .enqueue(new Callback<Questions>() {
                    @Override
                    public void onResponse(Call<Questions> call, Response<Questions> response) {
                        loading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().isStatus()) {
                                if (response.body().getQuestions() != null){
                                    listQuestions.clear();
                                    listQuestions.addAll(response.body().getQuestions());

                                    QuestionsAdapter questionsAdapter = new QuestionsAdapter(QuestionsActivity.this, listQuestions);
                                    recyclerQuestions.setAdapter(questionsAdapter);
                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Questions> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void initUI() {

        listQuestions = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerQuestions.setHasFixedSize(true);
        recyclerQuestions.setLayoutManager(layoutManager);


    }
}
