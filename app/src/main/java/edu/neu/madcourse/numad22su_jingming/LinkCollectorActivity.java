package edu.neu.madcourse.numad22su_jingming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.neu.madcourse.numad22su_jingming.databinding.ActivityLinkCollectorBinding;

public class LinkCollectorActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityLinkCollectorBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLinkCollectorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String nameString = data.getExtras().getString("nameString");
                        binding.linkNameTV.setText(nameString);

                        String urlString = data.getExtras().getString("urlString");
                        binding.linkURLTV.setText(urlString);
                    }
                }
            });

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_link_collector_ID) {
            Intent intent = new Intent(this, FABActivity.class);
            startForResult.launch(intent);
        }
    }
}
