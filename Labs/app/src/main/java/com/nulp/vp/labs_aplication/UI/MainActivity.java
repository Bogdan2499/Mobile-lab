package com.nulp.vp.labs_aplication.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nulp.vp.labs_aplication.R;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private EditText etName, etSurname, etEmail, etPhone, etPassword, etPasswordConf;
    private Button btnSubmit;
    private Button btnChangeAct;
    private Pattern pName, pEmail, pPhone, pPassword;
    private Boolean name, surname, email, phone, password, password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        validation();
    }

    private void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", etName.getText().toString());
        editor.putString("surname", etSurname.getText().toString());
        editor.putString("email", etEmail.getText().toString());
        editor.putString("phone", etPhone.getText().toString());
        editor.apply();
    }

    private boolean validate_field_to_save(EditText et_field, Pattern regex) {
        return et_field.getText().toString().matches(String.valueOf(regex));
    }

    private void validation() {
        pName = Pattern.compile("[A-Z][a-z ,.'-]+$");
        pEmail = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
        pPhone = Pattern.compile("\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})");
        pPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$");

        validate_field(etName, pName);
        validate_field(etSurname, pName);
        validate_field(etEmail, pEmail);
        validate_field(etPhone, pPhone);
        validate_field(etPassword, pPassword);
        validate_field(etPasswordConf, pPassword);
    }


    private void validate_field(final EditText et_field, final Pattern pattern) {
        et_field.addTextChangedListener(new TextWatcher() {

            private CharSequence mText;

            private boolean isValid(CharSequence s) {
                return pattern.matcher(s).matches();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValid(s)) {
                    et_field.setBackgroundResource(R.drawable.norm);

                } else et_field.setBackgroundResource(R.drawable.error);

                if (!etPassword.getText().toString().equals(etPasswordConf.getText().toString()) || etPasswordConf.getText().toString().equals("")) {
                    etPasswordConf.setBackgroundResource(R.drawable.error);
                    etPassword.setBackgroundResource(R.drawable.error);
                } else {
                    etPasswordConf.setBackgroundResource(R.drawable.norm);
                    etPassword.setBackgroundResource(R.drawable.norm);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_last_name);
        btnSubmit = findViewById(R.id.btn_submit);
        btnChangeAct = findViewById(R.id.btn_change_act);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etPasswordConf = findViewById(R.id.et_password_confr);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = validate_field_to_save(etName, pName);
                surname = validate_field_to_save(etSurname, pName);
                email = validate_field_to_save(etEmail, pEmail);
                phone = validate_field_to_save(etPhone, pPhone);
                password = validate_field_to_save(etPassword, pPassword);
                password_confirm = validate_field_to_save(etPasswordConf, pPassword);

                if (name && surname && email && phone && password && password_confirm) {
                    Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_LONG).show();
                    saveInfo();
                } else
                    Toast.makeText(getApplicationContext(), "Please fill out the correct fields", Toast.LENGTH_LONG).show();
            }
        });

        btnChangeAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ListInfo.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
