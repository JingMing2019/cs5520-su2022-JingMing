package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import edu.neu.madcourse.numad22su_jingming.databinding.ActivityFabBinding;

public class FABActivity extends AppCompatActivity {
    private ActivityFabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFabBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void SaveLink(View view){
        if (!binding.linkNameInput.getText().toString().equals("") &&
                !binding.linkURLInput.getText().toString().equals("")) {
            Intent data = new Intent();
            String nameString = binding.linkNameInput.getText().toString();
            data.putExtra("nameString", nameString);

            String urlString = binding.linkURLInput.getText().toString();
            data.putExtra("urlString", urlString);

            setResult(RESULT_OK, data);
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}