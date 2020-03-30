package com.example.loginpageforquarantine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView signin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        attachID();
        if (fAuth.getCurrentUser() != null) {
            //Bhai yahan par Intent wali line likh dena
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = email.getText().toString();
                final String pwd = password.getText().toString();



                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(user,pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        //bhai yahan Intent wali line daal dena
                        finish();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }

        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpPage=new Intent(LoginActivity.this,SignUp.class);
                startActivity(signUpPage);
            }
        });

    }

    private void attachID() {
        email = findViewById(R.id.ed_email);
        password = findViewById(R.id.ed_pass);
        login = findViewById(R.id.btnl_login);
        signin=findViewById(R.id.tv_signup);
        progressBar=findViewById(R.id.pgbar);
        fAuth=FirebaseAuth.getInstance();

    }
}

