package edu.neu.madcourse.numad22su_jingming;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Objects;

public class WebServiceActivity extends AppCompatActivity {
    private static final String TAG = "JMWebServiceActivity";
    private Spinner categorySP;
    private Button fromYearBtN;
    private Button toYearBtN;
    private Button searchBtN;
    private RecyclerView webSearchResultRV;
    private String selectedCategory;
    private String selectedFromYear;
    private String selectedToYear;
    private boolean pressFrom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        categorySP = findViewById(R.id.categorySP);
        createDropDownMenu();
        fromYearBtN = findViewById(R.id.fromYearBtN);
        toYearBtN = findViewById(R.id.toYearBtN);
        searchBtN = findViewById(R.id.searchBtN);

        webSearchResultRV = findViewById(R.id.webSearchResultRV);
    }

    // create the category drop down menu for categorySP
    public void createDropDownMenu() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(WebServiceActivity.this,
                R.array.categories, android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySP.setAdapter(arrayAdapter);
        categorySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(WebServiceActivity.this, "NothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // fromYearBtN onClick listener
    public void showFormYearPickerDialog(View v) {
        YearPickerDialog yearPicker = new YearPickerDialog(fromYearBtN);
        yearPicker.show(getSupportFragmentManager(), "yearPicker");

    }

    // toYearBtN onClick listener
    public void showToYearPickerDialog(View v) {
        YearPickerDialog yearPicker = new YearPickerDialog(toYearBtN);
        yearPicker.show(getSupportFragmentManager(), "yearPicker");
    }

    // searchBtN onClick listener
    public void startSearch(View v) {


    }

    public static void showSetYear(int year, Button yearBtN) {
        yearBtN.setText(String.valueOf(year));
    }

    public static class YearPickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private static final int MIN_YEAR = 1901;
        private final Button yearBtN;

        public YearPickerDialog(Button yearBtN) {
            this.yearBtN = yearBtN;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.v(TAG, "year_picker_dialog_on_create");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            Calendar cal = Calendar.getInstance();

            View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
            NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

            int year = cal.get(Calendar.YEAR);
            yearPicker.setMinValue(MIN_YEAR);
            yearPicker.setMaxValue(year);
            yearPicker.setValue(year);

            builder.setView(dialog)
                    .setTitle(R.string.select_year)
                    // Add action buttons
                    .setPositiveButton("Ok", (dialog1, id) -> setDate(yearPicker.getValue(), this.yearBtN))
                    .setNegativeButton("Cancel", (dialog12, id) -> Objects.requireNonNull(YearPickerDialog.this.getDialog()).cancel());

            return builder.create();
        }

        public static void setDate(int year, Button yearTV) {
            showSetYear(year, yearTV);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        }
    }
}
