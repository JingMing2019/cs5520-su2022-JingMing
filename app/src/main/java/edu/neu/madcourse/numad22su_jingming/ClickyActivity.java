package edu.neu.madcourse.numad22su_jingming;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        if (v.getId() == R.id.a_ID) {
            pressTV.append(getString(R.string.button_a_string));
        } else if (v.getId() == R.id.b_ID) {
            pressTV.append(getString(R.string.button_b_string));
        } else if (v.getId() == R.id.c_ID) {
            pressTV.append(getString(R.string.button_c_string));
        } else if (v.getId() == R.id.d_ID) {
            pressTV.append(getString(R.string.button_d_string));
        } else if (v.getId() == R.id.e_ID) {
            pressTV.append(getString(R.string.button_e_string));
        } else if (v.getId() == R.id.f_ID) {
            pressTV.append(getString(R.string.button_f_string));
        } else {
            pressTV.append(" - ");
        }
    }
}
