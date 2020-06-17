package com.example.examassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class View_papersActivity extends AppCompatActivity {
    PDFView pdfView;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Task<byte[]> PDFref;
    ProgressBar progressBar;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_papers);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar6);

        Intent data = getIntent();
        url = data.getStringExtra("URL");

        final long ONE_GIGABYTE = 1024 * 1024 * 1024;

        progressBar.setVisibility(View.VISIBLE);

        PDFref = storageReference.child(url).getBytes(ONE_GIGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                pdfView.fromBytes(bytes).enableSwipe(true).enableAnnotationRendering(true).enableAntialiasing(true).nightMode(false).load();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });






    /*    url = PDFref.getDownloadUrl().toString(); .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("tag", "onSuccess: " + uri.toString());
                pdfView.fromUri(uri).enableSwipe(true).enableAnnotationRendering(true).load();
            }
        }); */

    //    new RetrievePDFStream().execute(url);


    }

/*
    class RetrievePDFStream extends AsyncTask<String,Void, InputStream>{
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {

                URL urlx = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) urlx.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    } */
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
                Intent intent = new Intent(View_papersActivity.this, LoginActivity.class);
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