package com.example.smartfarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{
        private EditText textEmail,pass;
        private TextView forgot,register;
        private Button login;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;
    String thisEmail;
    String thisPassword;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            textEmail=findViewById(R.id.email);
            pass=findViewById(R.id.pass);
            forgot=findViewById(R.id.forgot);
            register=findViewById(R.id.register);
            login=findViewById(R.id.login);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });


            forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent forgot=new Intent(getApplicationContext(),Forgot.class);
                    startActivity(forgot);
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent register=new Intent(getApplicationContext(),Register.class);
                    startActivity(register);
                }
            });
        }
        public void login(){
            String email=textEmail.getText().toString().trim();
            String password=pass.getText().toString().trim();
            if (email.isEmpty()){
                textEmail.setError("Provide email");
                textEmail.requestFocus();
                return;
            }
            if (password.isEmpty()){
                pass.setError("Please provide password");
                pass.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                textEmail.setError("Provide a valid email");
                textEmail.requestFocus();
                return;
            }
            if (password.length()<6){
                pass.setError("Password should be more than 6 characters long");
                pass.requestFocus();
                return;
            }

            else {

                ref=database.getReference().child("Users");
                ref.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange (DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            thisEmail = data.child("email").getValue().toString().trim();
                            thisPassword = data.child("password").getValue().toString().trim();
                            String thisROLE=data.child("userRole").getValue().toString().trim();
                            String thisFullName=data.child("name").getValue().toString().trim();


                            if(email.equals(thisEmail) && password.equals(thisPassword)) {

                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                                SharedPreferences sharedpreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("email", thisEmail);
                                editor.putString("role", thisROLE);
                                editor.putString("name", thisFullName);
                                editor.commit();

                                Intent home = new Intent(getApplicationContext(), Home.class);
                                startActivity(home);
                            } else if(!email.equals(thisEmail) && !password.equals(thisPassword)) {
                                Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                            }

                            textEmail.setText("");
                            pass.setText("");
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        }
}