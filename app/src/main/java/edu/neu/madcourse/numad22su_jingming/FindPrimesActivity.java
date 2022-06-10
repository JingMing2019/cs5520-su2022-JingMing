package edu.neu.madcourse.numad22su_jingming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class FindPrimesActivity extends AppCompatActivity {
    private static final String TAG = "FindPrimesActivity";
    private final Handler textHandler = new Handler(Looper.getMainLooper());
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

    // find primes button onClickListener. If there's no prime searching running, starts a new
    // runnable Thread
    public void findPrimes(View view) {
        if (!isTerminate) {
            return;
        }
        isTerminate = false;
        runnableThread runnableThread = new runnableThread();
        new Thread(runnableThread).start();
    }

    // Terminate button onClickListener. Stop the thread.
    public void terminate(View view) {
        isTerminate = true;
    }

    // While prime searching is running, click back button brings up a dialog to ask user to confirm
    // whether they want finish searching or not.
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

    /**
     * This class represents a runnable thread. When this thread starts, it will check the number
     * from 2 and increment it by 1 to look for prime numbers while no terminate action interrupts.
     * A textHandler is used to display the latest found prime number and the current being checked
     * number.
     */
    class runnableThread implements Runnable {
        public void run() {
            int i = 2;
            while(!isTerminate) {
                final int finalI = i;
                textHandler.post(() -> {
                    checkedNumTV.setText(R.string.current_check_num_string);
                    checkedNumTV.append(String.valueOf(finalI));
                    if (isPrime(finalI)) {
                        foundPrimeTV.setText(R.string.latest_found_prime_string);
                        foundPrimeTV.append(String.valueOf(finalI));
                    }
                });
                Log.v(TAG, "Runnable tread: " + i);
                try {
                    // Makes the thread sleep or be inactive for 0.05 seconds. In total, the thread
                    // runs 20 times per second.
                    Thread.sleep(50); //
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    // Determine whether a `num` is prime or not. Using naive prime searching algorithm. If `num`
    // has a factor in [2, num - 1], means it is not a prime.
    private boolean isPrime(int num) {
        for(int i = 2; i < num; i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }
}