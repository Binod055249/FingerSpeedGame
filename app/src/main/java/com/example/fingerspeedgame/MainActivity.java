package com.example.fingerspeedgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtTimer;
    private TextView txtAThousand;
    private Button btnTap;
    private CountDownTimer countDownTimer;
    private long initialCountDownInMillis = 60000;
    private int timeInterval = 1000;
    private int remainingTime=60;
    private int aThousand =1000;
    private final String REMAINING_TIME_KEY = "remaining time key";
    private final String A_THOUSAND_KEY = "a thousand key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer=findViewById(R.id.txtTimer);
        txtAThousand=findViewById(R.id.txtAThousand);
        btnTap=findViewById(R.id.btnTap);

        txtAThousand.setText(aThousand+"");

        if(savedInstanceState !=null){
            remainingTime=savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand=savedInstanceState.getInt(A_THOUSAND_KEY);
            restoreTheGame();

        }

      btnTap.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

           aThousand--;
           txtAThousand.setText(aThousand+"");

           if(remainingTime > 0 && aThousand <= 0){
            showAlert(R.string.alert_title1 , R.string.alert_Message1);

           }

          }
      });
                if(savedInstanceState == null) {
                    countDownTimer = new CountDownTimer(initialCountDownInMillis, timeInterval) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            remainingTime = (int) millisUntilFinished / 1000;
                            txtTimer.setText(Integer.toString(remainingTime));

                        }

                        @Override
                        public void onFinish() {
                            if (remainingTime > 0 && aThousand <= 0) {
                                showAlert(R.string.alert_title1, R.string.alert_Message1);
                            } else {
                                showAlert(R.string.alert_title2, R.string.alert_Message2);
                            }

                        }
                    };
                    countDownTimer.start();
                }
    }

    private void  restoreTheGame() {

        int restoreReaminingTime = remainingTime;
        int restoreAThousand = aThousand;
        txtTimer.setText(restoreReaminingTime+"");
        txtAThousand.setText(restoreAThousand+"");

        countDownTimer =new CountDownTimer((long)remainingTime*1000,timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                  remainingTime= (int)millisUntilFinished/1000;
                  txtTimer.setText(remainingTime+"");
            }

            @Override
            public void onFinish() {

                    showAlert(R.string.alert_title2 , R.string.alert_Message2);

            }
        };
        countDownTimer.start();
    }

    private void resetTheGame(){
        if (countDownTimer != null){

            countDownTimer.cancel();
            countDownTimer=null;

        }

        aThousand=10;
        txtAThousand.setText(Integer.toString(aThousand));
        txtTimer.setText(Integer.toString(remainingTime));
        countDownTimer =new CountDownTimer(initialCountDownInMillis,timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int)millisUntilFinished/1000;
                txtTimer.setText(Integer.toString(remainingTime));

            }

            @Override
            public void onFinish() {

                    showAlert(R.string.alert_title2 , R.string.alert_Message2);


            }
        };
        countDownTimer.start();


    }


    private void showAlert(int title , int message){
        AlertDialog alertDialog =new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetTheGame();

                    }
                })
               .show();
        alertDialog.setCancelable(false);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REMAINING_TIME_KEY,remainingTime);
        outState.putInt(A_THOUSAND_KEY,aThousand);
        countDownTimer.cancel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.info_item ){
            Toast.makeText(this, "this is your current version please make sure that you are playing the updated version: "+BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
        }
        return  true;

    }
}