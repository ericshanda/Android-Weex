package com.taobao.demo.orange.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.luyujie.innovationcourse.R;
import com.taobao.demo.orange.CandidateHelper;
import com.taobao.demo.orange.adapter.CandidateAdapter;
import com.taobao.orange.OCandidate;
import com.taobao.orange.OrangeConfig;


/**
 * Created by wuer on 2018/5/25.
 */

public class CandidateActivity extends AppCompatActivity {
    private EditText candidateKeyEdit;
    private EditText candidateValueEdit;
    private Spinner candidateCompareSpinner;
    private RecyclerView candidateRecyclerView;

    private CandidateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);

        initViews();
    }

    private void initViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        candidateKeyEdit = findViewById(R.id.candidateKey);
        candidateValueEdit = findViewById(R.id.candidateValue);
        candidateCompareSpinner = findViewById(R.id.candidateCompare);
        candidateRecyclerView = findViewById(R.id.candidate_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new CandidateAdapter(CandidateHelper.candidateMap.values());

        candidateRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        candidateRecyclerView.setLayoutManager(layoutManager);
        candidateRecyclerView.setAdapter(adapter);
    }

    public void saveCandidate(View view) {
        String key = candidateKeyEdit.getText().toString();
        String value = candidateValueEdit.getText().toString();
        String compare = candidateCompareSpinner.getSelectedItem().toString();

        OCandidate candidate = CandidateHelper.getCandidate(key, value, compare);
        if (candidate != null) {
            CandidateHelper.addCandidate(candidate);
            OrangeConfig.getInstance().addCandidate(candidate);
        }
        adapter.update(CandidateHelper.candidateMap.values());
    }
}
