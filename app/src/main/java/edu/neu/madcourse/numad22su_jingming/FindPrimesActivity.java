package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class FindPrimesActivity extends AppCompatActivity {
    private static final String TAG = "FindPrimesActivity";
    TextView foundPrimeTV;
    TextView checkedNumTV;
    private boolean isTerminate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_primes);
        foundPrimeTV = findViewById(R.id.foundedPrimeNumID);
        checkedNumTV = findViewById(R.id.checkedNumID);
        isTerminate = true;
    }

    public void findPrimes(View view) {
        if (!isTerminate) {
            return;
        }
        isTerminate = false;
        runnableThread runnableThread = new runnableThread();
        new Thread(runnableThread).start();
    }

    public void terminate(View view) {
        isTerminate = true;
    }

    @Override
    public void onBackPressed() {
        if(!isTerminate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to terminate the prime search?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                isTerminate = true;
                finish();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            super.onBackPressed();
        }
    }

    class runnableThread implements Runnable {
        public void run() {
            int i = 2;
            while(!isTerminate) {
                final int finalI = i;
                new Handler(Looper.getMainLooper()).post(() -> {
                    checkedNumTV.setText(R.string.current_check_num_string);
                    checkedNumTV.append(String.valueOf(finalI));
                    if (isPrime(finalI)) {
                        foundPrimeTV.setText(R.string.latest_found_prime_string);
                        foundPrimeTV.append(String.valueOf(finalI));
                    }
                });
                Log.v(TAG, "Runnable tread: " + i);
                try {
                    // 20 times per second
                    Thread.sleep(50); //Makes the thread sleep or be inactive for 0.05 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    private boolean isPrime(int num) {
        for(int i = 2; i < num; i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }
}