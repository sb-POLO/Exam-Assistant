package com.example.examassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    private EditText uid;
    private EditText password;
    private TextView logincount;
    private Button login;
    private int counter = 5;
    Timer timer;
    FirebaseAuth fAuth;
    GoogleApiClient googleApiClient;
    TextView newuser;
    ProgressBar progressBar2;
    TextView forgotpassword;
    CheckBox checkBox;
    String rme;
    String SiteKey = "6LeUwqMZAAAAABg1PbfL5R-7jngKPuBAuTFOEr7a";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uid = (EditText)findViewById(R.id.et_uid);
        password = (EditText)findViewById(R.id.et_password);
        logincount = (TextView)findViewById(R.id.tv_logincount);
        login = findViewById(R.id.btn_login);
        logincount.setText("No. of attempts remaining: 5");
        newuser = findViewById(R.id.tv_newuser);
        fAuth = FirebaseAuth.getInstance();
        progressBar2 = findViewById(R.id.progressBar2);
        forgotpassword = findViewById(R.id.tv_forgotpass);
        checkBox = findViewById(R.id.chb_rme);

       googleApiClient = new GoogleApiClient.Builder(this).addApi(SafetyNet.API).addConnectionCallbacks(LoginActivity.this).build();
       googleApiClient.connect();

        SharedPreferences preferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
        rme = preferences.getString("remember", "false");



        if(fAuth.getCurrentUser() != null && rme.equals("true")){
            startActivity(new Intent(getApplicationContext(), UniversityActivity.class));
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = uid.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(TextUtils.isEmpty(eml)){
                    uid.setError("Email is required");
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
                login.setEnabled(false);
                progressBar2.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(eml, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "LogIN successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, UniversityActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            login.setEnabled(true);
                            progressBar2.setVisibility(View.INVISIBLE);
                            counter--;
                            logincount.setText("No. of attempts remaining: "+ String.valueOf(counter));
                            if(counter == 0){
                                login.setEnabled(false);
                                Toast.makeText(LoginActivity.this, "You are trying too often", Toast.LENGTH_SHORT).show();
                                reCAPTCHA();
                            }

                        }


                    }
                });

            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetmail = new EditText(v.getContext());
                AlertDialog.Builder passwordresetdialog = new AlertDialog.Builder(v.getContext());
                passwordresetdialog.setTitle("Reset password ?");
                passwordresetdialog.setMessage("Enter your email to get the password reset link");
                passwordresetdialog.setView(resetmail);

                passwordresetdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordresetdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
               // passwordresetdialog.create().show();

                final AlertDialog dialog = passwordresetdialog.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mail = resetmail.getText().toString();
                        if(TextUtils.isEmpty(mail)){
                            resetmail.setError("Email is required");
                            return;
                        }
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset link sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset link is not sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Log.d("tag", "onCheckedChanged: false");
                }else if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Log.d("tag", "onCheckedChanged: true");

                }
            }
        });
    }

    private void reCAPTCHA(){
        if(counter == 0){

            Log.d("TAG", "onResult: reCAPTCHA working!");
            SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient, SiteKey).setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                @Override
                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {

                    Status status = recaptchaTokenResult.getStatus();
                    if((status != null) && status.isSuccess()){

                        login.setEnabled(true);
                        counter++;
                        logincount.setText("No. of attempts remaining: "+ String.valueOf(counter));

                    }

                }
            });
        }else {
            login.setEnabled(false);
        }
    }


 /*   private void validate(String userID, String userpassword){
        if((userID.equals("Sourav")) && (userpassword.equals("Sou123r@v"))){
            Intent intent = new Intent(LoginActivity.this, UniversityActivity.class);
            Toast.makeText(this, "LogIN successful", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else{
            counter--;
            Toast.makeText(this, "UserID or Password dosen't match", Toast.LENGTH_SHORT).show();
            logincount.setText("No. of attempts remaining: "+ String.valueOf(counter));
            if(counter == 0){
                login.setEnabled(false);
            }
        }
    }*/

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}




