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
    private ActivityWebServiceBinding binding;
    ListenableFuture<JSONObject> jsonObjectFuture;
    private List<Laureate> laureates;
    private boolean searchByCategoryOrName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebServiceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        createDropDownMenu();

        laureates = new ArrayList<>();
    }

    // fromYearBtN onClick listener
    public void showFormYearPickerDialog(View v) {
        YearPickerDialog yearPicker = new YearPickerDialog(binding.fromYearBtN);
        yearPicker.show(getSupportFragmentManager(), "yearPicker");

    }

    // toYearBtN onClick listener
    public void showToYearPickerDialog(View v) {
        YearPickerDialog yearPicker = new YearPickerDialog(binding.toYearBtN);
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
        String mUrl = generateURL();
        if (mUrl.equals("")) {
            return;
        }
        Log.w(TAG, "URL: " + mUrl);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        jsonObjectFuture = Futures.submit(() -> {
            Log.v(TAG, "start a thread");
            JSONObject jsonObject = new JSONObject();
            try {

                // nobel laureates
                //                URL url = new URL("https://api.nobelprize.org/2.1/laureates?name=Albert%20Einstein");
//                URL url = new URL("https://api.nobelprize.org/2.1/laureates?name=Marie%20Curie");
                // nobel prize
                //                URL url = new URL("https://api.nobelprize.org/2.1/nobelPrizes?nobelPrizeYear=2010&yearTo=2020&nobelPrizeCategory=che");
                URL url = new URL(mUrl);
                String response = NetworkUtil.httpResponse(url);

                Log.v("JMWebServiceActivity", "http response success");

                jsonObject = new JSONObject(response);

                Log.v("JMWebServiceActivity", "create new JsonObject success");
                return jsonObject;
            } catch (MalformedURLException e) {
                Log.v(TAG, "MalformedURLException", e);
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException", e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.v(TAG, "MIOException", e);
                e.printStackTrace();
            } catch (JSONException e) {
                Log.v(TAG, "JSONException", e);
                e.printStackTrace();
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
                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        Log.w(TAG, "JSONException", t);
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
            url = String.format("https://api.nobelprize.org/2.1/nobelPrizes?nobelPrizeYear=%s&yearTo=%s&nobelPrizeCategory=%s",
                    fromYear, toYear, category);
        } else {
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
            String idPrefix = getResources().getString(R.string.laureate_id_print_string);
            JSONArray arrLaureates = jsonObject.getJSONArray("laureates");
            if (arrLaureates.length() == 0) {
                Toast.makeText(getApplication(), "No record", Toast.LENGTH_LONG).show();
                return;
            }
            laureates.clear();

            JSONObject objLaureate = arrLaureates.getJSONObject(0);
            String idInt = objLaureate.getString("id");
            String id = String.format("%s%s", idPrefix, idInt);
            String fullName = objLaureate.getJSONObject("fullName").getString("en");

            JSONArray arrPrizes = objLaureate.getJSONArray("nobelPrizes");
            for(int i = 0; i < arrPrizes.length(); i++) {
                JSONObject objPrize = arrPrizes.getJSONObject(i);
                String prize = objPrize.getJSONObject("categoryFullName").getString("en");
                String year = objPrize.getString("awardYear");
                Laureate laureate = new Laureate(id, fullName, prize, year);
                laureates.add(laureate);
                Log.v(TAG, laureates.toString());
            }
            updateWebServiceResultRV();
        } catch (JSONException e) {
            Log.v(TAG,"JSONException", e);
            e.printStackTrace();
        }
    }

    private void displayNobelPrizes(JSONObject jsonObject) {
        try {
            String idPrefix = getResources().getString(R.string.laureate_id_print_string);
            JSONArray arrPrizes = jsonObject.getJSONArray("nobelPrizes");
            if (arrPrizes.length() == 0) {
                Toast.makeText(getApplication(), "No record", Toast.LENGTH_LONG).show();
                return;
            }
            laureates.clear();

            for(int i = 0; i < arrPrizes.length(); i++) {
                JSONObject objPrize = arrPrizes.getJSONObject(i);
                String year = objPrize.getString("awardYear");
                String prize = objPrize.getJSONObject("categoryFullName").getString("en");
                JSONArray arrLaureates = objPrize.getJSONArray("laureates");
                if (arrLaureates.length() == 0) {
                    continue;
                }
                for (int j = 0; j < arrLaureates.length(); j++) {
                    JSONObject objLaureate = arrLaureates.getJSONObject(j);
                    String idInt = objLaureate.getString("id");
                    String id = String.format("%s%s", idPrefix, idInt);
                    String fullName = objLaureate.getJSONObject("fullName").getString("en");
                    Laureate laureate = new Laureate(id, fullName, prize, year);
                    laureates.add(laureate);
                }
            }
            updateWebServiceResultRV();
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
            yearPicker.setMaxValue(year - 1);
            yearPicker.setValue(year - 1);

            builder.setView(dialog)
                    .setTitle(R.string.select_year)
                    // Add action buttons
                    .setPositiveButton("Ok",
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
