package com.gaote.wuliu.ui.depository;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gaote.wuliu.R;

import butterknife.ButterKnife;

public class YewuDocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yewu_document);
        ButterKnife.bind(this);
    }
}
