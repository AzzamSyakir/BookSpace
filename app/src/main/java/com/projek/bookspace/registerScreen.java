package com.projek.bookspace;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

public class registerScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText edt_name, edt_email, edt_pass;
    String email, password, username;
    Button reg;
    TextView have_acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        //find view
        edt_email = findViewById(R.id.reg_email);
        edt_name = findViewById(R.id.reg_name);
        edt_pass = findViewById(R.id.reg_pass);
        reg = findViewById(R.id.reg);
        have_acc = findViewById(R.id.have_acc);
        progressBar = findViewById(R.id.reg_load);
        //on click
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerScreen.this, loginScreen.class));
            }
        });
    }

    private void register() {
        email = edt_email.getText().toString();
        password = edt_pass.getText().toString();
        username = edt_name.getText().toString();
            if (email.isEmpty()) {
                edt_email.setError("Email is required");
                edt_email.requestFocus();
                return;
            }

            if (username.isEmpty()) {
                edt_name.setError("Username is required");
                edt_name.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                edt_pass.setError("Pasword is required");
                edt_pass.requestFocus();
                return;
            }
            if (email.length() > 18) {
                edt_email.setError("Email is too long");
                edt_email.requestFocus();
                return;
            }

            if (username.length() > 18) {
                edt_name.setError("Username is too long");
                edt_name.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                User user = new User(username, email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(registerScreen.this, "Register success.",
                                                            Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    startActivity(new Intent(registerScreen.this, loginScreen.class));
                                                } else {
                                                    Toast.makeText(registerScreen.this, "Register failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(registerScreen.this, "Register failed.",
                                        Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
    }
}