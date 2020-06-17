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

public class SubjectActivity extends AppCompatActivity {

    Button ecom, cryptography, hu;
    TextView stream, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        stream = findViewById(R.id.tv_stream);
        semester = findViewById(R.id.tv_semester);
        ecom = findViewById(R.id.btn_CS802E);
        cryptography = findViewById(R.id.btn_CS801D);
        hu = findViewById(R.id.btn_HU801A);

        Intent data = getIntent();
        String strm = data.getStringExtra("Stream");
        String sem = data.getStringExtra("Semester");
        stream.setText(strm);
        semester.setText(sem);

        ecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Subject_queryActivity.class);
                intent.putExtra("Subject", "CS 802E");
                intent.putExtra("Subject Name", "E-Commerce");
                startActivity(intent);

            }
        });

        cryptography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Subject_queryActivity.class);
                intent.putExtra("Subject", "CS 801D");
                intent.putExtra("Subject Name", "Cryptography and network security");
                startActivity(intent);

            }
        });

        hu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Subject_queryActivity.class);
                intent.putExtra("Subject", "HU 801A");
                intent.putExtra("Subject Name", "Organisational behaviour");
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