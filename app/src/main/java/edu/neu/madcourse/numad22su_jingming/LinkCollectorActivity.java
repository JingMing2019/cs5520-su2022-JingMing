package edu.neu.madcourse.numad22su_jingming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.madcourse.numad22su_jingming.databinding.ActivityLinkCollectorBinding;

public class LinkCollectorActivity extends AppCompatActivity implements View.OnClickListener{
//    private static final String TAG = "LinkCollectorActivity___";
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        linksRecyclerView = findViewById(R.id.linkRecyclerView);
        linksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linksRecyclerView.setAdapter(new LinkAdapter(linkList, this));
    }

    View.OnClickListener undoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            linkList.remove(linkList.size() - 1);
            // inform the adapter that the last item is removed from the linkList
            Objects.requireNonNull(linksRecyclerView.getAdapter()).notifyItemRemoved(linkList.size());
            Snackbar.make(findViewById(R.id.coordinateLayout),
                            R.string.link_remove, Snackbar.LENGTH_LONG).show();
        }
    };


    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Add the result (name, url) from `data` intent to linkList
                            linkList.add(data.getParcelableExtra("link"));
//                            Log.v(linkList.toString(), "linkList_startForResult");

                            // when link is successfully added to the list, show the snackbar
                            Snackbar.make(findViewById(R.id.coordinateLayout),
                                    R.string.link_added_to_list, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.undo_add_link, undoOnClickListener).show();
                        }
                    }
                }
            });

    @Override
    public void onClick(View v) {
        // click fab button launches a FabActivity
        if (v.getId() == R.id.fabLinkCollectorID) {
            Intent intent = new Intent(this, FabActivity.class);
            // wait for the result from the FabActivity
            startForResult.launch(intent);
        }
    }

    // Override onRestore and onSave method of InstanceState helps save data. The `linkList` will be
    // saved and restored when users rotate the screen.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        linkList = savedInstanceState.getParcelableArrayList("linkList");
//        Log.v(linkList.toString(), "linkList_onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("linkList", (ArrayList<? extends Parcelable>) linkList);
//        Log.v(linkList.toString(), "linkList_onSaveInstanceState");
    }
}
