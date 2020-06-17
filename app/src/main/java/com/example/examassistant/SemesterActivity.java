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

public class SemesterActivity extends AppCompatActivity {

    Button first, second, third, fourth, fifth, sixth, seventh, eighth;
    TextView stream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        first = findViewById(R.id.btn_1);
        second = findViewById(R.id.btn_2);
        third = findViewById(R.id.btn_3);
        fourth = findViewById(R.id.btn_4);
        fifth = findViewById(R.id.btn_5);
        sixth = findViewById(R.id.btn_6);
        seventh = findViewById(R.id.btn_7);
        eighth = findViewById(R.id.btn_8);
        stream = findViewById(R.id.tv_stream);

        Intent data = getIntent();
        String strm = data.getStringExtra("Stream");
        stream.setText(strm);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SemesterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();

            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SemesterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();

            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SemesterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();

            }
        });

        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SemesterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();

            }
        });

        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SemesterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();

            }
        });

        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SemesterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();

            }
        });

        seventh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SemesterActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();

            }
        });

        eighth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(getApplicationContext(), SubjectActivity.class);
                intent.putExtra("Stream", "CSE");
                intent.putExtra("Semester", "8th Semester");
                startActivity(intent);

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