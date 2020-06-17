package com.example.examassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UniversityActivity extends AppCompatActivity {

    Button makaut, cu, competitive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        makaut = findViewById(R.id.btn_makaut);
        cu = findViewById(R.id.btn_cu);
        competitive = findViewById(R.id.btn_competitive);

        makaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), StreamActivity.class);
                intent.putExtra("University", "MAKAUT");
                startActivity(intent);

            }
        });

        cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*    Intent intent = new Intent(getApplicationContext(), StreamActivity.class);
                intent.putExtra("University", "CU");
                startActivity(intent); */

                Toast.makeText(UniversityActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onResult: university working!");
            }
        });

        competitive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent intent = new Intent(getApplicationContext(), StreamActivity.class);
                intent.putExtra("University", "COMPETITIVE");
                startActivity(intent); */

                Toast.makeText(UniversityActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();


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