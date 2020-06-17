package com.example.examassistant;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class QuizActivity extends AppCompatActivity {
    TextView subject, ques, o1,o2,o3,o4;
    Button next,prev;
    ProgressBar progressBar;
    Integer count=0;
    LinearLayout optionsConatiner;
    FirebaseFirestore fStore;
    Integer c = 1;
    Integer k = 1;
    Integer i;
    String answer="",question="",option1="",option2="",option3="",option4="";
    Integer score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        subject = findViewById(R.id.tv_subject);
        ques = findViewById(R.id.tv_ques);
        o1 = findViewById(R.id.tv_o1);
        o2 = findViewById(R.id.tv_o2);
        o3 = findViewById(R.id.tv_o3);
        o4 = findViewById(R.id.tv_o4);
        next = findViewById(R.id.btn_next);
        prev = findViewById(R.id.btn_prev);
        optionsConatiner = findViewById(R.id.options_container);
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar7);

        ques.setMovementMethod(new ScrollingMovementMethod());
        o1.setMovementMethod(new ScrollingMovementMethod());
        o2.setMovementMethod(new ScrollingMovementMethod());
        o3.setMovementMethod(new ScrollingMovementMethod());
        o4.setMovementMethod(new ScrollingMovementMethod());

        Intent data = getIntent();
        final String sub = data.getStringExtra("Subject");
        String url = data.getStringExtra("URL");
        String subName = data.getStringExtra("Subject Name");

        subject.setText(subName);

        progressBar.setVisibility(View.VISIBLE);

        final DocumentReference documentReference = fStore.collection(url).document(sub + " MCQ");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable final DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                question = documentSnapshot.getString("Q"+c);
                answer = documentSnapshot.getString("A"+c);
                option1 = documentSnapshot.getString("O"+c+""+k);
                k++;
                option2 = documentSnapshot.getString("O"+c+""+k);
                k++;
                option3 = documentSnapshot.getString("O"+c+""+k);
                k++;
                option4 = documentSnapshot.getString("O"+c+""+k);
                k=1;

                count = 0;
                playAnim(ques ,0);

               /* ques.setText(question);
                o1.setText(option1);
                o2.setText(option2);
                o3.setText(option3);
                o4.setText(option4);
                progressBar.setVisibility(View.INVISIBLE); */

                for(i = 0;i<4; i++){
                    optionsConatiner.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkAnswer((TextView) v);

                        }
                    });
                }

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        next.setEnabled(false);
                        enableOPtion(true);
                        c++;
                        if(documentSnapshot.getString("Q"+c) != null){

                            question = documentSnapshot.getString("Q"+c);
                            answer = documentSnapshot.getString("A"+c);
                            option1 = documentSnapshot.getString("O"+c+""+k);
                            k++;
                            option2 = documentSnapshot.getString("O"+c+""+k);
                            k++;
                            option3 = documentSnapshot.getString("O"+c+""+k);
                            k++;
                            option4 = documentSnapshot.getString("O"+c+""+k);
                            k=1;

                            count = 0;
                            playAnim(ques ,0);

                        }
                        else {
                            c--;
                            int div = 100 / c;
                            int per = score * div;
                            Toast.makeText(QuizActivity.this, "Test finished!\n"+"Your score : " + per + "%", Toast.LENGTH_LONG).show();
                            if(sub.equals("CS 802E")){
                                Intent intent = new Intent(getApplicationContext(), Subject_queryActivity.class);
                                intent.putExtra("Subject", "CS 802E");
                                intent.putExtra("Subject Name", "E-Commerce");
                                //  startActivity(intent);
                                finish();
                            }
                            else if(sub.equals("CS 801D")){
                                Intent intent = new Intent(getApplicationContext(), Subject_queryActivity.class);
                                intent.putExtra("Subject", "CS 801D");
                                intent.putExtra("Subject Name", "Cryptography and network security");
                                finish();
                            }
                            else if(sub.equals("HU 801A")){
                                Intent intent = new Intent(getApplicationContext(), Subject_queryActivity.class);
                                intent.putExtra("Subject", "HU 801A");
                                intent.putExtra("Subject Name", "Organisational behaviour");
                                finish();
                            }

                        }


                    }
                });
            }
        });




    }

    private void playAnim(final View view, final int value){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4){
                    playAnim(optionsConatiner.getChildAt(count), 0);
                    count ++;
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value == 0){
                    playAnim(view, 1);
                    ques.setText(question);
                    o1.setText(option1);
                    o2.setText(option2);
                    o3.setText(option3);
                    o4.setText(option4);
                    progressBar.setVisibility(View.INVISIBLE);


                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(TextView selectedOption){
        enableOPtion(false);
        next.setEnabled(true);
        if(selectedOption.getText().toString().trim().equals(answer.trim())){
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#804CAF50")));
            score++;
            Log.d("Tag", "checkAnswer: "+score);

        }else{
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#80F44336")));
                if(answer.trim().equals(o1.getText().toString().trim())){
                    o1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#804CAF50")));
                }else if(answer.trim().equals(o2.getText().toString().trim())) {
                    o2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#804CAF50")));
                }else if(answer.trim().equals(o3.getText().toString().trim())) {
                    o3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#804CAF50")));
                }else if(answer.trim().equals(o4.getText().toString().trim())) {
                    o4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#804CAF50")));
                }





        }

    }

    private void enableOPtion(boolean enable){
        for(i = 0;i < 4; i++){
            optionsConatiner.getChildAt(i).setEnabled(enable);
            if(enable){
                optionsConatiner.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5F000000")));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general, menu);
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
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}