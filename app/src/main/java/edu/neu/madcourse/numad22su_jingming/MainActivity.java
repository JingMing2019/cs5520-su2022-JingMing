package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // click about_me_ID, show a toast containing name and email
        Button aboutMeButton = findViewById(R.id.about_me_ID);
        aboutMeButton.setOnClickListener(v -> {
            String nameAndEmail = "Jing Ming: ming.j@northeastern.edu";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(MainActivity.this, nameAndEmail, duration).show();
        });

        // click clicky_ID, show a new activity
        Button clickyButton = findViewById(R.id.clicky_ID);
        clickyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClickyActivity.class);
            startActivity(intent);
        });

        // show text "hello world"
        TextView textView = findViewById(R.id.hello_ID);
        textView.setText(R.string.hello_world_string);
    }
}