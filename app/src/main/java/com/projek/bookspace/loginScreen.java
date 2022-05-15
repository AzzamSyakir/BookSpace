package com.projek.bookspace;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    ImageView back;
    Button login;
    EditText edt_email, edt_pass;
    TextView no_acc, frgt_pass;
    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        progressBar = findViewById(R.id.loading);
        back = findViewById(R.id.back);
        login = findViewById(R.id.login);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        no_acc = findViewById(R.id.no_acc);
        frgt_pass = findViewById(R.id.frgt_pass);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginScreen.this, homeScreen.class));
            }
        });no_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent());
            }
        });frgt_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent());
            }
        });
    }

    private void Login() {
        email = edt_email.getText().toString();
        password = edt_pass.getText().toString();
        if (email.isEmpty()){
            edt_email.setError("Email is required");
            edt_email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edt_pass.setError("Pasword is required");
            edt_pass.requestFocus();
            return;
        }
        if (email.length() > 18){
            edt_email.setError("Email is too long");
            edt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt_email.setError("Email is not valid");
            edt_email.requestFocus();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(loginScreen.this, "Login success.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(loginScreen.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}