package com.gaote.wuliu.ui.client.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.gaote.wuliu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnBindAccountActivity extends AppCompatActivity {
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_bind_account);
        ButterKnife.bind(this);
    }
}
