package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // click about_me_ID, show name and email in a new activity
        Button aboutMeButton = findViewById(R.id.about_me_ID);
        aboutMeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
        });

        // click clicky_ID, show 6 buttons in a new activity
        Button clickyButton = findViewById(R.id.clicky_ID);
        clickyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClickyActivity.class);
            startActivity(intent);
        });

        // click link_collector_ID, show a new activity with a list of links
        Button linkCollectorButton = findViewById(R.id.link_collector_ID);
        linkCollectorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LinkCollectorActivity.class);
            startActivity(intent);
        });

        // show text "hello world"
        TextView textView = findViewById(R.id.helloID);
        textView.setText(R.string.hello_world_string);
    }
}