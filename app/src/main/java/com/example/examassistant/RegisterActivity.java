package com.example.examassistant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    EditText phone;
    Button signup;
    TextView haveaccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_uid);
        password = findViewById(R.id.et_password);
        phone = findViewById(R.id.et_phone);
        signup = findViewById(R.id.btn_login);
        haveaccount = findViewById(R.id.tv_haveaccount);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

      /*  if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), UniversityActivity.class));
            finish();
        } */

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eml = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                final String fullname = name.getText().toString();
                final String phn = phone.getText().toString();
                if(TextUtils.isEmpty(eml)){
                   email.setError("Email is required");
                   return;
                }
                if(TextUtils.isEmpty(fullname)){
                    name.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Password is required");
                    return;
                }
                if(pass.length() < 6){
                    password.setError("Password should not be less than 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(eml,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Verification mail has been sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                                    Toast.makeText(RegisterActivity.this, "Verification mail not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String,Object> user1 = new HashMap<>();
                            user1.put("Name",fullname);
                            user1.put("Email",eml);
                            user1.put("Phone",phn);
                            documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: User profile is created " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: User profile not created " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), UniversityActivity.class));
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);


                        }

                    }
                });
            }
        });
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}