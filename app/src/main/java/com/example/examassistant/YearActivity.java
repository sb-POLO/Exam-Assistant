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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class YearActivity extends AppCompatActivity {

    Button y2016,y2017,y2018,y2019,y2015,y2014;
    TextView subject;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        y2014  = findViewById(R.id.btn_2014);
        y2015  = findViewById(R.id.btn_2015);
        y2016  = findViewById(R.id.btn_2016);
        y2017  = findViewById(R.id.btn_2017);
        y2018  = findViewById(R.id.btn_2018);
        y2019  = findViewById(R.id.btn_2019);
        subject = findViewById(R.id.tv_subject);
        storageReference = FirebaseStorage.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        Intent data = getIntent();
        final String sub = data.getStringExtra("Subject");
        String subName = data.getStringExtra("Subject Name");
        subject.setText(subName);

        y2014.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 802E/2014/CS 802E.pdf");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 801D/2014/CS 801D.pdf");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/HU 801A/2014/HU 801A.pdf");
                    startActivity(intent);

                }
            }
        });

        y2015.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 802E/2015/CS 802E.pdf");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 801D/2015/CS 801D.pdf");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/HU 801A/2015/HU 801A.pdf");
                    startActivity(intent);

                }
            }
        });

        y2016.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 802E/2016/CS 802E.pdf");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 801D/2016/CS 801D.pdf");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/HU 801A/2016/HU 801A.pdf");
                    startActivity(intent);

                }
            }
        });

        y2017.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 802E/2017/CS 802E.pdf");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 801D/2017/CS 801D.pdf");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/HU 801A/2017/HU 801A.pdf");
                    startActivity(intent);

                }
            }
        });

        y2018.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 802E/2018/CS 802E.pdf");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 801D/2018/CS 801D.pdf");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/HU 801A/2018/HU 801A.pdf");
                    startActivity(intent);

                }
            }
        });

        y2019.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sub.equals("CS 802E")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 802E");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 802E/2019/CS 802E.pdf");
                    startActivity(intent);
                }
                else if(sub.equals("CS 801D")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "CS 801D");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/CS 801D/2019/CS 801D.pdf");
                    startActivity(intent);

                }
                else if(sub.equals("HU 801A")){

                    Intent intent = new Intent(getApplicationContext(), View_papersActivity.class);
                    intent.putExtra("Subject", "HU 801A");
                    intent.putExtra("URL", "MAKAUT/CSE/8th Semester/HU 801A/2019/HU 801A.pdf");
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