package com.example.smartfarm;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Loginfragment extends Fragment {
    private EditText textEmail,pass;
    private TextView forgot,register;
    private Button login;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;
    SharedPreferences sharedPreferences;
    String thisEmail;
    String thisPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);
        textEmail=v.findViewById(R.id.email);
        pass=v.findViewById(R.id.pass);
        forgot=v.findViewById(R.id.forgot);
        register=v.findViewById(R.id.register);
        login=v.findViewById(R.id.login);

        sharedPreferences = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String SHPrefemail= sharedPreferences.getString("email", null);

        if(SHPrefemail !=null && SHPrefemail.equals("owner@gmail.com")) {
            Intent intent = new Intent(getActivity().getApplicationContext(), Dashboard.class);
            startActivity(intent);
        }else if( SHPrefemail !=null && !SHPrefemail.equals("owner@gmail.com")) {
            Intent home = new Intent(getActivity().getApplicationContext(), Home.class);
            startActivity(home);
        }else {

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot=new Intent(getActivity().getApplicationContext(),Forgot.class);
                startActivity(forgot);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register=new Intent(getActivity().getApplicationContext(),Register.class);
                startActivity(register);
            }
        });

        return v;

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
            if(email.equals("owner@gmail.com") && password.equals("123456")) {

                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("email", "owner@gmail.com");
                editor.putString("role", "THEOWNER");
                editor.putString("name", "FARM OWNER");
                editor.commit();

                Intent intent = new Intent(getActivity().getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }else {

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

                                Toast.makeText(getActivity().getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                                SharedPreferences sharedpreferences =getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("email", thisEmail);
                                editor.putString("role", thisROLE);
                                editor.putString("name", thisFullName);
                                editor.commit();

                                Intent home = new Intent(getActivity().getApplicationContext(), Home.class);
                                startActivity(home);
                            } else if(!email.equals(thisEmail) && !password.equals(thisPassword)) {
                                Toast.makeText(getActivity().getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
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
}
