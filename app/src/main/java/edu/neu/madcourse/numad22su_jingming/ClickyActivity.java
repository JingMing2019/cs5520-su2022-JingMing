package edu.neu.madcourse.numad22su_jingming;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ClickyActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView pressTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        // show text "Pressed: - "
        pressTV = findViewById(R.id.press_ID);
        pressTV.setText(R.string.default_press_string);
    }

    @Override
    public void onClick(View v) {
        pressTV.setText(R.string.pressed_string);
        pressTV.append(" ");
        if (v.getId() == R.id.aID) {
            pressTV.append(getString(R.string.button_a_string));
        } else if (v.getId() == R.id.bID) {
            pressTV.append(getString(R.string.button_b_string));
        } else if (v.getId() == R.id.cID) {
            pressTV.append(getString(R.string.button_c_string));
        } else if (v.getId() == R.id.dID) {
            pressTV.append(getString(R.string.button_d_string));
        } else if (v.getId() == R.id.eID) {
            pressTV.append(getString(R.string.button_e_string));
        } else if (v.getId() == R.id.fID) {
            pressTV.append(getString(R.string.button_f_string));
        } else {
            pressTV.append(" - ");
        }
    }
}
