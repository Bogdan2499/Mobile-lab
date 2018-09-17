package com.nulp.vp.labs_aplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView tvName;
    private EditText etName;
    private Button btnSetName;
    private Button btnClear;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tvName = findViewById(R.id.tv_name);
        btnSetName = findViewById(R.id.btn_set_name);
        btnClear = findViewById(R.id.btn_clear);
        etName = findViewById(R.id.et_name);

        btnSetName.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = etName.getText().toString();
                        Boolean isEmpty = name.trim().equals("");
                        if (isEmpty) {
                            Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_LONG).show();
                        } else {
                            tvName.setText("Hello " + name);
                        }
                    }
                });

        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etName.setText("");
                    }
                });
    }
}
