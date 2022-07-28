package com.example.smartfarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupFragment extends Fragment {

    private EditText textEmail,pass,pwd,textLivestock,textName;
    private Spinner spinner;
    private Button register;
    private FirebaseAuth mAuth;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;
    public static  String role;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_register, container, false);
//        View result=inflater.inflate(R.layout.activity_register, container, false);
       textName= v.findViewById(R.id.name);
         textEmail=v.findViewById(R.id.email);
         pass=v.findViewById(R.id.pwd);
          pwd=v.findViewById(R.id.confPwd);
         textLivestock=v.findViewById(R.id.livestock);
           register=v.findViewById(R.id.register);
            spinner = (Spinner)v. findViewById(R.id.role);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    role=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.role_array, android.R.layout.simple_spinner_item);
//     adapter.setAdapter(new ArrayAdapter<CharSequence>(getView().getContext(),
//                android.R.layout.simple_spinner_item)
//        );

// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            mAuth = FirebaseAuth.getInstance();

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    register();
                }
            });



        return v;

    }
    public void  register() {
        String name=textName.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String confirmPassword = pwd.getText().toString().trim();
        String livestock = textLivestock.getText().toString().trim();

        if (name.isEmpty()) {
            textEmail.setError("Provide email");
            textName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            textEmail.setError("Provide email");
            textEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Please provide password");
            pass.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            pwd.setError("Please confirm password");
            pwd.requestFocus();
            return;
        }
        if (password.length() < 6) {
            pass.setError("Password should be more than 6 characters long");
            pass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("Provide a valid email");
            textEmail.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            pass.setError("Passwords must match");
            pass.requestFocus();
            return;
        } else {
            ref=database.getReference().child("Users");
            ref.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        Toast.makeText(getActivity().getApplicationContext(), "Email already exists,use another email", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String id=ref.push().getKey();
                        UserModel model=new UserModel(name,email,password,role,livestock);
                        ref.child(id).setValue(model);
                        Toast.makeText(getActivity().getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                        Intent log = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(log);
                        textEmail.setText("");
                        textLivestock.setText("");
                        pass.setText("");
                        pwd.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}