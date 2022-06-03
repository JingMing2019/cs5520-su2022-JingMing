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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.numad22su_jingming.databinding.ActivityLinkCollectorBinding;

public class LinkCollectorActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityLinkCollectorBinding binding;
    private RecyclerView linksRecyclerView;
    private List<Link> linkList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLinkCollectorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        linkList = new ArrayList<>();

        linksRecyclerView = findViewById(R.id.linkRecyclerView);
        linksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linksRecyclerView.setAdapter(new LinkAdapter(linkList, this));

    }

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }

                        String nameString = data.getExtras().getString("nameString");
                        String urlString = data.getExtras().getString("urlString");

                        linkList.add(new Link(nameString, urlString));

//                        binding.linkNameTV.setText(nameString);
//                        binding.linkURLTV.setText(urlString);


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
