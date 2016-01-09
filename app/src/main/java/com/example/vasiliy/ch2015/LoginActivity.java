package com.example.vasiliy.ch2015;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed = (EditText) findViewById(R.id.edName);

        ((Button) findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed.getText().toString().equals("")) {
                    MyUser user = MyUser.getInstance(LoginActivity.this);
                    user.setName(LoginActivity.this, ed.getText().toString());
                    finish();
                }
            }
        });

    }
}
