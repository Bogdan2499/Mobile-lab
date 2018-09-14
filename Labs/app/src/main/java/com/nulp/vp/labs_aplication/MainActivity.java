package com.nulp.vp.labs_aplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView tv_name;
    private EditText et_name;
    private Button btn_set_name, btn_clear;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btn_set_name.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = et_name.getText().toString();
                        if (et_name.getText().toString().trim().equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_LONG).show();
                        } else {
                            tv_name.setText("Hello " + name);
                        }
                    }
                });

        btn_clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et_name.setText("");
                    }
                });
    }

    private void init() {
        tv_name = findViewById(R.id.tv_name);
        btn_set_name = findViewById(R.id.btn_set_name);
        btn_clear = findViewById(R.id.btn_clear);
        et_name = findViewById(R.id.et_name);
    }
}
