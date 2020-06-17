package com.example.examassistant;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class View_topicsActivity extends AppCompatActivity {

    TextView head, description, subject;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    Integer c = 1;
    Button next,prev;
    ProgressBar p1,p2;
    String ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_topics);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        head = findViewById(R.id.tv_ques);
        description = findViewById(R.id.tv_o4);
        next = findViewById(R.id.btn_next);
        prev = findViewById(R.id.btn_prev);
        subject = findViewById(R.id.tv_subject);
        p1 = findViewById(R.id.progressBaro1);
        p2 = findViewById(R.id.progressBarq);

        head.setMovementMethod(new ScrollingMovementMethod());
        description.setMovementMethod(new ScrollingMovementMethod());

        Intent data = getIntent();
        String sub = data.getStringExtra("Subject");
        String url = data.getStringExtra("URL");
        String subName = data.getStringExtra("Subject Name");

        subject.setText(subName);
       // p1.setVisibility(View.VISIBLE);
        p2.setVisibility(View.VISIBLE);

      /*  DocumentReference documentReference = fStore.collection("ques").document("rand");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                textView.setText(documentSnapshot.getString("1"));
            }
        }); */

        final FirebaseUser user = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection(url).document(sub);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable final DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                final String ques = documentSnapshot.getString("Topic "+c);
                ans = documentSnapshot.getString("Description "+c);
                playAnim(description, 0);
                head.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        head.setText(ques);
                        p2.setVisibility(View.INVISIBLE);

                    }
                }, 800);

              //  p2.setVisibility(View.INVISIBLE);
              //  description.setText(ans);
               // p1.setVisibility(View.INVISIBLE);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // p1.setVisibility(View.VISIBLE);
                        head.setText("");
                        p2.setVisibility(View.VISIBLE);
                        c++;
                        if(documentSnapshot.getString("Topic "+c) != null){
                            final String ques = documentSnapshot.getString("Topic "+c);
                            ans = documentSnapshot.getString("Description "+c);
                            playAnim(description, 0);
                            head.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    head.setText(ques);
                                    p2.setVisibility(View.INVISIBLE);

                                }
                            }, 800);
                           // description.setText(ans);
                          //  p1.setVisibility(View.INVISIBLE);
                        }else{
                            description.setText("");
                            c--;
                            p2.setVisibility(View.INVISIBLE);
                           // p1.setVisibility(View.INVISIBLE);
                            Toast.makeText(View_topicsActivity.this, "No more topic", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // p1.setVisibility(View.VISIBLE);
                        head.setText("");
                        p2.setVisibility(View.VISIBLE);
                        c--;
                        if(documentSnapshot.getString("Topic "+c) != null){
                            final String ques = documentSnapshot.getString("Topic "+c);
                            ans = documentSnapshot.getString("Description "+c);
                            playAnim(description, 0);
                            head.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    head.setText(ques);
                                    p2.setVisibility(View.INVISIBLE);

                                }
                            }, 800);
                           // description.setText(ans);
                          //  p1.setVisibility(View.INVISIBLE);
                        }else{
                            description.setText("");
                            c++;
                            p2.setVisibility(View.INVISIBLE);
                           // p1.setVisibility(View.INVISIBLE);
                            Toast.makeText(View_topicsActivity.this, "No more topic", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }

    private  void playAnim(final View view, final int value){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value == 0){
                    playAnim(view, 1);
                    description.setText(ans);

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
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.log_out:
                SharedPreferences preferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(View_topicsActivity.this, LoginActivity.class);
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finishAffinity();
                return true;
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}