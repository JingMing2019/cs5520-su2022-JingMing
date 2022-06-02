package edu.neu.madcourse.numad22su_jingming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LinkCollectorActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_link_collector_ID) {
            Intent intent = new Intent(this, FABActivity.class);
            startActivity(intent);
//            Snackbar.make(v, R.string.create_link_success, Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
        }
    }
}
