package edu.neu.madcourse.numad22su_jingming;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebServiceActivity extends AppCompatActivity {
    private static final String TAG = "JMWebServiceActivity";
    private String selectedCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        Spinner spinner = findViewById(R.id.categoryTV);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(WebServiceActivity.this,
                R.array.categories, android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, (String) parent.getItemAtPosition(position));
                selectedCategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(WebServiceActivity.this, "NothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
