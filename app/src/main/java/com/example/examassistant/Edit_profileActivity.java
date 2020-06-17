package com.example.examassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Edit_profileActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button save;
    EditText name,email,phone;
    ImageView profilepic;
    FirebaseUser user;
    StorageReference storageReference;
    String userID;
    ProgressBar progressBar, progressBar2;
    ImageView changepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        save = findViewById(R.id.btn_save);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone);
        profilepic = findViewById(R.id.iv_profilepic);
        user = fAuth.getCurrentUser();
        userID = fAuth.getCurrentUser().getUid();
        progressBar = findViewById(R.id.progressBar5);
        progressBar2 = findViewById(R.id.progressBar3);
        changepic = findViewById(R.id.iv_changepic);

        StorageReference profileref = storageReference.child("Users/"+userID+"/Profile picture.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });

        Intent data = getIntent();
        String iname = data.getStringExtra("Name");
        String iemail = data.getStringExtra("Email");
        String iphone = data.getStringExtra("Phone");

        Log.d("tag", "onCreate: "+ iname + " " + iemail + " " + iphone);

        name.setText(iname);
        email.setText(iemail);
        phone.setText(iphone);

        changepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opengalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalleryintent, 2000);

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || phone.getText().toString().isEmpty()){
                    Toast.makeText(Edit_profileActivity.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
                final String eml = email.getText().toString();
                user.updateEmail(eml).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docref = fStore.collection("Users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("Email", eml);
                        edited.put("Name", name.getText().toString());
                        edited.put("Phone", phone.getText().toString());
                        docref.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar2.setVisibility(View.INVISIBLE);
                                Intent intent =  new Intent(getApplicationContext(), ProfileActivity.class);
                                finish();
                            }
                        });
                        Toast.makeText(Edit_profileActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar2.setVisibility(View.INVISIBLE);
                        Toast.makeText(Edit_profileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2000){
            if(resultCode == Activity.RESULT_OK){
                progressBar.setVisibility(View.VISIBLE);
                Uri imageuri = data.getData();
                //profilepic.setImageURI(imageuri);

                uploadImageToFirebase(imageuri);


            }
        }
    }

    private void uploadImageToFirebase(Uri imageuri) {
        final StorageReference fileref = storageReference.child("Users/"+userID+"/Profile picture.jpg");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilepic);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
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
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}