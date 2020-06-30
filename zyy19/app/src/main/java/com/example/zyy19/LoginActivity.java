package com.example.zyy19;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zyy19.list.NetListActivity;


public class LoginActivity extends AppCompatActivity {

    EditText mNameEditText;
    EditText mPwdEditText;
    Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNameEditText=(EditText)findViewById(R.id.edt_name) ;
        mPwdEditText=(EditText)findViewById(R.id.edt_pwd);
        mLoginButton =(Button) findViewById(R.id.btn_login);



        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mNameEditText.getText().toString();
                String nameVal=mNameEditText.getText().toString().trim();
                String pwdVal=mPwdEditText.getText().toString().trim();
                if (TextUtils.isEmpty(nameVal)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pwdVal)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, NetListActivity.class);
                SharedPreferences sp= PreferenceManager.
                        getDefaultSharedPreferences(LoginActivity.this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", content);
                editor.apply();

                intent.putExtra("loginName",content);
                intent.putExtra("name",nameVal);
                intent.putExtra("pwd",pwdVal);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sp = PreferenceManager.
                getDefaultSharedPreferences(LoginActivity.this);
        String username = sp.getString("username","");
        if ("".equals(username)){
            mNameEditText.setHint("请输入用户名");
        }else{
            mNameEditText.setText(username);
        }
    }


}
