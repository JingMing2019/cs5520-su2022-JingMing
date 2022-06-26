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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.neu.madcourse.numad22su_jingming.databinding.ActivityWebServiceBinding;

public class WebServiceActivity extends AppCompatActivity {
    private static final String TAG = "JMWebServiceActivity";
    private static final int MIN_YEAR = 1901;
    private ActivityWebServiceBinding binding;
    ListenableFuture<JSONObject> jsonObjectFuture;
    private List<Laureate> laureates;
    private boolean searchByCategoryOrName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebServiceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.loadingPanel.setVisibility(View.INVISIBLE);
        setContentView(view);

        createDropDownMenu();

        laureates = new ArrayList<>();
    }

    // fromYearBtN onClick listener
    public void showFormYearPickerDialog(View v) {
        YearPickerDialog yearPicker = new YearPickerDialog(binding.fromYearBtN, MIN_YEAR);
        yearPicker.show(getSupportFragmentManager(), "yearPicker");
    }

    // toYearBtN onClick listener
    public void showToYearPickerDialog(View v) {
        int minYear = MIN_YEAR;
        if (!binding.fromYearBtN.getText().toString().equals("From")){
            minYear = Integer.parseInt(binding.fromYearBtN.getText().toString());
        }
        YearPickerDialog yearPicker = new YearPickerDialog(binding.toYearBtN, minYear);
        yearPicker.show(getSupportFragmentManager(), "yearPicker");
    }

    public void clearSelections(View v)  {
        binding.categorySP.setSelection(0);
        binding.fromYearBtN.setText(R.string.from_string);
        binding.toYearBtN.setText(R.string.to_string);
        if (!binding.givenNameET.getText().toString().equals("")){
            binding.givenNameET.setText("");
        }
        if (!binding.familyNameET.getText().toString().equals("")){
            binding.familyNameET.setText("");
        }
        clearWebServiceResultRV();
    }

    private void clearWebServiceResultRV() {
        if (laureates.size() != 0) {
            for (int i = laureates.size() - 1; i > -1; i--){
                laureates.remove(i);
                // inform the adapter that the last item is removed from the linkList
                Objects.requireNonNull(binding.webSearchResultRV.getAdapter()).notifyItemRemoved(laureates.size());
            }
        }
    }

    // searchBtN onClick listener
    public void startSearch(View v) {
        clearWebServiceResultRV();
        String mUrl = generateURL();
        if (mUrl.equals("")) {
            return;
        }
        Log.w(TAG, "URL: " + mUrl);
        binding.loadingPanel.setVisibility(View.VISIBLE);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        jsonObjectFuture = Futures.submit(() -> {
            Log.v(TAG, "start a thread");
            JSONObject jsonObject = new JSONObject();
            try {
                URL url = new URL(mUrl);
                String response = NetworkUtil.httpResponse(url);
                jsonObject = new JSONObject(response);
                return jsonObject;
            } catch (MalformedURLException e) {
                Log.v(TAG, "MalformedURLException", e);
                e.printStackTrace();
                binding.loadingPanel.setVisibility(View.INVISIBLE);
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException", e);
                e.printStackTrace();
                binding.loadingPanel.setVisibility(View.INVISIBLE);
            } catch (IOException e) {
                Log.v(TAG, "MIOException", e);
                e.printStackTrace();
                binding.loadingPanel.setVisibility(View.INVISIBLE);
            } catch (JSONException e) {
                Log.v(TAG, "JSONException", e);
                e.printStackTrace();
                binding.loadingPanel.setVisibility(View.INVISIBLE);
            }

            return jsonObject;
        }, executor);

        Futures.addCallback(
                jsonObjectFuture,
                new FutureCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        if(searchByCategoryOrName){
                            Log.v(TAG, "search By Category");
                            displayNobelPrizes(jsonObject);
                        } else {
                            Log.v(TAG, "search By Name");
                            displayNobelLaureates(jsonObject);
                        }
                        binding.loadingPanel.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        Log.w(TAG, "JSONException", t);
                        binding.loadingPanel.setVisibility(View.INVISIBLE);
                    }
                },
                ContextCompat.getMainExecutor(this));
        executor.shutdown();

    }

    private String generateURL() {
        String url = "";
        if (!binding.givenNameET.getText().toString().equals("")
                && !binding.familyNameET.getText().toString().equals("")){
            searchByCategoryOrName = false;
            url = String.format("https://api.nobelprize.org/2.1/laureates?name=%s%%20%s",
                    binding.givenNameET.getText().toString(),
                    binding.familyNameET.getText().toString());

        } else if (!binding.categorySP.getSelectedItem().toString().equals("None")
                && !binding.fromYearBtN.getText().toString().equals("From")) {
            String fromYear = binding.fromYearBtN.getText().toString();
            String category = binding.categorySP.getSelectedItem().toString().substring(0, 3).toLowerCase(Locale.ROOT);
            String toYear;

            if (binding.toYearBtN.getText().equals("To")) {
                toYear = fromYear;
            } else {
                toYear = binding.toYearBtN.getText().toString();
            }
            searchByCategoryOrName = true;
            url = String.format("https://api.nobelprize.org/2.1/nobelPrizes?limit=220&nobelPrizeYear=%s&yearTo=%s&nobelPrizeCategory=%s",
                    fromYear, toYear, category);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Unable to Start Search")
                    .setCancelable(false)
                    .setMessage("Please check the search option.\n" +
                            "You can either search by\n" +
                            "1. selecting Category and Year\n" +
                            "2. typing Given Name and Family Name.")
                    .setPositiveButton("Ok", ((dialog, which) -> dialog.cancel()))
                    .create()
                    .show();
            return url;
        }

        try {
            return NetworkUtil.validInput(url);
        } catch (NetworkUtil.MyException e) {
            Log.w(TAG, "search by name url generation", e);
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return url;
    }

    private void displayNobelLaureates(JSONObject jsonObject) {
        try {
            laureates.clear();
            String idPrefix = getResources().getString(R.string.laureate_id_print_string);
            JSONArray arrLaureates = jsonObject.getJSONArray("laureates");
            if (arrLaureates.length() == 0) {
                laureates.add(new Laureate("", "", "",
                        getResources().getString(R.string.no_record_string)));
            } else {
                JSONObject objLaureate = arrLaureates.getJSONObject(0);
                String idInt = objLaureate.getString("id");
                String id = String.format("%s%s", idPrefix, idInt);
                String fullName = objLaureate.getJSONObject("fullName").getString("en");

                JSONArray arrPrizes = objLaureate.getJSONArray("nobelPrizes");
                for (int i = 0; i < arrPrizes.length(); i++) {
                    JSONObject objPrize = arrPrizes.getJSONObject(i);
                    String prize = objPrize.getJSONObject("categoryFullName").getString("en");
                    String year = objPrize.getString("awardYear");
                    Laureate laureate = new Laureate(id, fullName, prize, year);
                    laureates.add(laureate);
                    Log.v(TAG, laureates.toString());
                }
            }
            updateWebServiceResultRV();
        } catch (JSONException e) {
            Log.v(TAG,"JSONException", e);
            e.printStackTrace();
        }
    }

    private void displayNobelPrizes(JSONObject jsonObject) {
        try {
            laureates.clear();
            String idPrefix = getResources().getString(R.string.laureate_id_print_string);
            JSONArray arrPrizes = jsonObject.getJSONArray("nobelPrizes");

            if (arrPrizes.length() == 0) {
                laureates.add(new Laureate("", "", "",
                        getResources().getString(R.string.no_record_string)));
            } else {
                for (int i = 0; i < arrPrizes.length(); i++) {
                    JSONObject objPrize = arrPrizes.getJSONObject(i);
                    String year = objPrize.getString("awardYear");
                    String prize = objPrize.getJSONObject("categoryFullName").getString("en");
                    if (!objPrize.has("laureates")) {
                        String fullName = getResources().getString(R.string.no_laureates_string);
                        Laureate laureate = new Laureate("", fullName, prize, year);
                        laureates.add(laureate);
                        continue;
                    }
                    JSONArray arrLaureates = objPrize.getJSONArray("laureates");
                    if (arrLaureates.length() == 0) {
                        continue;
                    }
                    for (int j = 0; j < arrLaureates.length(); j++) {
                        JSONObject objLaureate = arrLaureates.getJSONObject(j);
                        String idInt = objLaureate.getString("id");
                        String id = String.format("%s%s", idPrefix, idInt);
                        String fullName;
                        if (objLaureate.has("fullName")) {
                            fullName = objLaureate.getJSONObject("fullName").getString("en");
                        } else if (objLaureate.has("orgName")) {
                            fullName = objLaureate.getJSONObject("orgName").getString("en");
                        } else {
                            fullName = "";
                        }
                        Laureate laureate = new Laureate(id, fullName, prize, year);
                        laureates.add(laureate);
                    }
                }
            }
            if (!laureates.isEmpty()) {
                updateWebServiceResultRV();
            }
        } catch (JSONException e) {
            Log.v(TAG,"JSONException", e);
            e.printStackTrace();
        }
    }

    private void updateWebServiceResultRV() {
        Log.v("JMWebServiceActivity", "update web service laureates: " + laureates.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.webSearchResultRV.setLayoutManager(linearLayoutManager);
        binding.webSearchResultRV.setAdapter(new LaureateAdapter(laureates));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.webSearchResultRV
                .getContext(), linearLayoutManager.getOrientation());
        binding.webSearchResultRV.addItemDecoration(dividerItemDecoration);
    }

    // create the category drop down menu for categorySP
    private void createDropDownMenu() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                WebServiceActivity.this,
                R.array.categories,
                android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySP.setAdapter(arrayAdapter);
        binding.categorySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WebServiceActivity.this,
                        "Select: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(WebServiceActivity.this, "Nothing Selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showSetYear(int year, Button yearBtN) {
        yearBtN.setText(String.valueOf(year));
    }

    public static class YearPickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private final Button yearBtN;
        private final Integer minYear;

        public YearPickerDialog(Button yearBtN, Integer minYear) {
            this.yearBtN = yearBtN;
            this.minYear = minYear;
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
            yearPicker.setMinValue(minYear);
            yearPicker.setMaxValue(year - 1);
            yearPicker.setValue(year - 1);

            builder.setView(dialog)
                    .setTitle(R.string.select_year)
                    // Add action buttons
                    .setPositiveButton(R.string.ok_string,
                            (dialog1, id) -> setDate(yearPicker.getValue(), this.yearBtN))
                    .setNegativeButton("Cancel",
                            (dialog12, id) -> Objects.requireNonNull(YearPickerDialog.this.getDialog()).cancel());

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
