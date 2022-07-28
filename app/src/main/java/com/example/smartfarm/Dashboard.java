package com.example.smartfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {
    ImageButton ChickenDir,CattleDir,PigsDir,goatsDir;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        goatsDir = findViewById(R.id.goatsDir);
        PigsDir = findViewById(R.id.PigsDir);
        CattleDir = findViewById(R.id.CattleDir);
        ChickenDir = findViewById(R.id.ChickenDir);


        goatsDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedpreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();



                Intent intent = new Intent(getApplicationContext(), OwnerActivity.class);
                intent.putExtra("animal", "goats");

                startActivity(intent);
            }
        });

        PigsDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

//                editor.putString("email", sharedPreferences.getString("email", null));
//                editor.putString("role", sharedPreferences.getString("role", null));
//                editor.putString("name", sharedPreferences.getString("name", null));

                Intent intent = new Intent(getApplicationContext(), OwnerActivity.class);
                intent.putExtra("animal", "pigs");

                startActivity(intent);

            }
        });

        CattleDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

//                editor.putString("email", sharedPreferences.getString("email", null));
//                editor.putString("role", sharedPreferences.getString("role", null));
//                editor.putString("name", sharedPreferences.getString("name", null));

                Intent intent = new Intent(getApplicationContext(), OwnerActivity.class);
                intent.putExtra("animal", "cattle");

                startActivity(intent);

            }
        });

        ChickenDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

//                String ownerEmail = sharedPreferences.getString("email", null);
//                String ownerRole = sharedPreferences.getString("role", null);
//                String ownerName = sharedPreferences.getString("name", null);
//                editor.putString("email",ownerEmail );
//                editor.putString("role", ownerRole);
//                editor.putString("name", ownerName);

                Intent intent = new Intent(getApplicationContext(), OwnerActivity.class);
                intent.putExtra("animal", "chicken");

                startActivity(intent);
            }
        });

    }
}