package com.example.zyy19;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EdtActivity extends AppCompatActivity {
    EditText mEditText;
    Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt);

        mEditText = (EditText)findViewById(R.id.edt_input);
        mDoneButton = (Button) findViewById(R.id.btn_done);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wy = mEditText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("mySign",wy);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
