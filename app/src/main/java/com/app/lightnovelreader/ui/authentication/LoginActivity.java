package com.app.lightnovelreader.ui.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.lightnovelreader.R;
import com.app.lightnovelreader.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName;
    private EditText edtPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private TextView btnForgot, btnSignup;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setInitial();
        setControl();
    }

    private void setInitial(){
        edtUserName = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnForgot = findViewById(R.id.btn_forgot);
        btnSignup = findViewById(R.id.signupText);
        edtUserName.setText("nguyentuanhiep01@gmail.com");
        edtPassword.setText("Hiep31102001");
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }

    private void setControl(){
        setBtnForgotPassword();
        setBtnSignup();
        btnLogin.setOnClickListener(v -> {
            loginUserAccount();
        });
    }

    private void loginUserAccount() {
        // show the visibility of progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = edtUserName.getText().toString();
        password = edtPassword.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                // hide the progress bar
                                progressBar.setVisibility(View.GONE);

                                // if sign-in is successful
                                // intent to home activity
                                Intent intent
                                        = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                            }

                            else {

                                // sign-in failed
                                Toast.makeText(getApplicationContext(),
                                                "Login failed!!",
                                                Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar
                                progressBar.setVisibility(View.GONE);
                            }
                        });
    }

    private void setBtnForgotPassword(){
        btnForgot.setOnClickListener(v -> {
            Intent intentForgot = new Intent(LoginActivity.this, ForgotActivity.class);
            intentForgot.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intentForgot);
        });
    }

    private void setBtnSignup(){
        btnSignup.setOnClickListener(v -> {
            Intent intentSignup = new Intent(LoginActivity.this, RegisterActivity.class);
            intentSignup.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intentSignup);
        });
    }
}