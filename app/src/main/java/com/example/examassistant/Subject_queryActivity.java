package com.example.examassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Subject_queryActivity extends AppCompatActivity {

    Button topics, quespapers,mcq;
    TextView subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_query);

        subject = findViewById(R.id.tv_subject);
        topics = findViewById(R.id.btn_topics);
        quespapers = findViewById(R.id.btn_quespapers);
        mcq = findViewById(R.id.btn_mcq);

        Intent data = getIntent();
        final String sub = data.getStringExtra("Subject");
        String subName = data.getStringExtra("Subject Name");
        subject.setText(subName);

        mcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester");
                    intent.putExtra("Subject Name", "E-Commerce");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/");
                    intent.putExtra("Subject Name", "Cryptography and network security");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester");
                    intent.putExtra("Subject Name", "Organisational behaviour");
                    startActivity(intent);

                }
            }
        });

        topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), View_topicsActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester");
                    intent.putExtra("Subject Name", "E-Commerce");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), View_topicsActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester");
                    intent.putExtra("Subject Name", "Cryptography and network security");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), View_topicsActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester");
                    intent.putExtra("Subject Name", "Organisational behaviour");
                    startActivity(intent);

                }

            }
        });

        quespapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), YearActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("Subject Name", "E-Commerce");
                  //  intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 802E/CS 802E.pdf");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), YearActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("Subject Name", "Cryptography and network security");
                  //  intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 801D/CS 801D.pdf");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), YearActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("Subject Name", "Organisational behaviour");
                  //  intent.putExtra("URL", "MAKAUT/CSE/8th Semester/HU 801A/HU 801A.pdf");
                    startActivity(intent);

                }

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