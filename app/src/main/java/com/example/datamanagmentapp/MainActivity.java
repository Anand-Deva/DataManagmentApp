package com.example.datamanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.log.RealmLog;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        App app = RealmSingleton.getInstance().getRealm();

        /*
        boolean isEmailEmpty, isPasswordEmpty;
        EditText emailEditText, passwordEditText;
        emailEditText = findViewById(R.id.editText_Email);
        passwordEditText = findViewById(R.id.editText_Password);
         */

        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {

            /*
            String emailText = emailEditText.getText().toString();
            String passwordText = passwordEditText.getText().toString();

            if(TextUtils.isEmpty(emailText)) {
                emailEditText.setError(getResources().getString(R.string.logIn_Email_error));
                isEmailEmpty = true;
                return;
            }else{
                isEmailEmpty = false;
            }

            if(TextUtils.isEmpty(passwordText)) {
                passwordEditText.setError(getResources().getString(R.string.logIn_password_error));
                isPasswordEmpty = true;
                return;
            }else{
                isPasswordEmpty = false;
            }

            if(isEmailEmpty == false && isPasswordEmpty == false) {


                Credentials credentials = Credentials.emailPassword(emailText, passwordText);
                app.loginAsync(credentials, it -> {
                    if (it.isSuccess()) {
                        Log.v("START MongoDB Realm", "Successfully authenticated anonymously.");
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(this, "Incorrect user name or password", Toast.LENGTH_LONG).show();
                        RealmLog.error(it.getError().toString());
                        Log.e("Initialize the MongoDB Realm App Client:", "Error logging into the Realm app. Make sure that anonymous authentication is enabled.");
                    }
                });

            }
             */

            Credentials credentials = Credentials.anonymous();
            app.loginAsync(credentials, it -> {
                if (it.isSuccess()) {
                    Log.v("MongoDB Auth", "Success");
                     Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                     startActivity(intent);
                } else {
                    Log.e("MongoDB Auth", it.getError().toString());
                    Toast.makeText(getApplicationContext(),"Error while connecting to MongoDB",Toast.LENGTH_LONG).show();
                }
            });
        });

    }
}