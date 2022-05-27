package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private Button aboutMeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutMeButton = findViewById(R.id.about_me_button);
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
//                String nameAndEmail = "Jing Ming: ming.j@northeastern.edu";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(MainActivity.this, nameAndEmail, duration);
//                toast.show();
            }
        });

        TextView textView = findViewById(R.id.helloID);
        textView.setText(R.string.hello_world_string);
    }
}