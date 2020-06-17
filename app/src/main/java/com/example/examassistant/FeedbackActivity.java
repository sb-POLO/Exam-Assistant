package com.example.examassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.hsalf.smileyrating.SmileyRating;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class FeedbackActivity extends AppCompatActivity {

    String user;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    SmileyRating smileyRating;
    TextView suggestion, compliment, complain;
    EditText feedback;
    int rating;
    String feedbackType = "";
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        smileyRating = findViewById(R.id.smile_rating);
        suggestion = findViewById(R.id.tv_suggestion);
        complain = findViewById(R.id.tv_complain);
        compliment = findViewById(R.id.tv_compliment);
        feedback = findViewById(R.id.et_feedback);
        submit = findViewById(R.id.btn_submit);

        user = fAuth.getCurrentUser().getUid();
        feedback.setMovementMethod(new ScrollingMovementMethod());
        Log.d("TAG", "onCreate: Feedback Working!");

        smileyRating.setRating(SmileyRating.Type.GREAT, true);
        smileyRating.setRating(5, true);


        smileyRating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {

                if (SmileyRating.Type.GREAT == type) {
                    Log.d("TAG", "onSmileySelected: Great rating is given");
                }

                rating = type.getRating();
               // submit.setEnabled(true);
                Log.d("TAG", "onSmileySelected: "+rating);
                Toast.makeText(FeedbackActivity.this, ""+rating+" Star", Toast.LENGTH_SHORT).show();
            }
        });


        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                suggestion.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#80FFEB3B")));
                compliment.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5F000000")));
                complain.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5F000000")));
                submit.setEnabled(true);
                feedback.setEnabled(true);
                feedbackType = "Suggestion";


            }
        });

        compliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                suggestion.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5F000000")));
                compliment.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#804CAF50")));
                complain.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5F000000")));
                submit.setEnabled(true);
                feedback.setEnabled(true);
                feedbackType = "Compliment";

            }
        });

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                suggestion.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5F000000")));
                compliment.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5F000000")));
                complain.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#80F44336")));
                submit.setEnabled(true);
                feedback.setEnabled(true);
                feedbackType = "Complaint";

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String feed = feedback.getText().toString().trim();
                if(TextUtils.isEmpty(feed)){
                    feedback.setError("Please give some feedback!");
                    return;
                }

                DocumentReference documentReference = fStore.collection("Feedback").document(feedbackType);
                Map<String,Object> fdbk = new HashMap<>();
                fdbk.put(user, feed);
                documentReference.update(fdbk).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FeedbackActivity.this, "Thank you for your feedback", Toast.LENGTH_SHORT).show();

                    }
                });
                Rating(rating);
                finish();
            }
        });

    }

    private void Rating(final int rate){

        final DocumentReference rtng = fStore.collection("Feedback").document("Rating");
        rtng.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                Map<String,Object> rtn = new HashMap<>();
                rtn.put(user,rate);
                rtng.update(rtn);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_feedback, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.my_profile:
                Intent intent1 = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent1);
                return true;
            case R.id.log_out:
                SharedPreferences preferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finishAffinity();
                Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}