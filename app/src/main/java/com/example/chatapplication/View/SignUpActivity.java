package com.example.chatapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapplication.R;
import com.example.chatapplication.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    TextView already_signup;
    Button submit;
    EditText email_signup,pass_signup,username;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        already_signup = findViewById(R.id.already_signup);
        submit = findViewById(R.id.submit);
        email_signup = findViewById(R.id.email_signup);
        pass_signup = findViewById(R.id.pass_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);

        already_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_signup.getText().toString();
                String pass = pass_signup.getText().toString();
                final String user_name = username.getText().toString();

                if (email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Please Complete Above Details",Toast.LENGTH_SHORT).show();
                } else {
                    final Users users = new Users(user_name,email);
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user_name)
                                    .build();
                            currUser.updateProfile(profileUpdates);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("users").child(currUser.getUid()).setValue(users);
                            Toast.makeText(SignUpActivity.this,"Succesfully Sign Up",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
