package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nameAndEmail = "Jing Ming: ming.j@northeastern.edu";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(MainActivity.this, nameAndEmail, duration);
        toast.show();
    }
}