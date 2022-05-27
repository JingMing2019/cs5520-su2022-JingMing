package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AppCompatActivity;

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

        // click about_me_button, show a toast containing name and email
        Button aboutMeButton = findViewById(R.id.about_me_button);
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameAndEmail = "Jing Ming: ming.j@northeastern.edu";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(MainActivity.this, nameAndEmail, duration);
                toast.show();
            }
        });

        // show text "hello world"
        TextView textView = findViewById(R.id.helloID);
        textView.setText(R.string.hello_world_string);
    }
}