package com.example.demoappcalculator2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewHisActivity extends AppCompatActivity {
    TextView editText;
    String dataResult;
    ArrayList<String> dataList = new ArrayList<>();
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(editText.getText().toString() != null) {
            outState.putString("calculateData", editText.getText().toString());
        }
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.get("calculateData") != null) {
            editText.setText(savedInstanceState.get("calculateData").toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_his);

        editText = findViewById(R.id.dataCalcHis);
        Intent intent = getIntent();

        String data = intent.getStringExtra("calculateDataStr");
        editText.setText(data);
    }
}