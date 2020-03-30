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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText name, email, password,address,phone;
    Button signin;
    TextView login;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        attachID();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String n = name.getText().toString();
                final String mail = email.getText().toString().trim();
                String pass = password.getText().toString();
                final String addr=address.getText().toString();
                final String contact=phone.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(mail, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user=new User(n,mail,contact,addr);
                        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user);
                        Toast.makeText(SignUp.this, "User created", Toast.LENGTH_SHORT).show();
                        //bhai yahan par intent wali line likh dena
                        finish();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage=new Intent(SignUp.this,LoginActivity.class);
                startActivity(loginPage);
            }
        });

    }

    private void attachID() {
        name=findViewById(R.id.ed_name);
        email=findViewById(R.id.ed_email);
        password=findViewById(R.id.ed_pass);
        phone=findViewById(R.id.ed_phone);
        address=findViewById(R.id.ed_address);
        signin=findViewById(R.id.btnl_signup);
        login=findViewById(R.id.tv_login);
        progressBar=findViewById(R.id.pgbar);
        fAuth=FirebaseAuth.getInstance();
    }
//
//    public boolean emailValidator(String email)
//    {
//        Pattern pattern;
//        Matcher matcher;
//        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        pattern = Pattern.compile(EMAIL_PATTERN);
//        matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
}
