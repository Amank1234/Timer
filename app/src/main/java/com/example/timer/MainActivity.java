package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mobi.upod.timedurationpicker.TimeDurationPicker;

public class MainActivity extends AppCompatActivity {

    Button button;
ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    ImageButton imageButton6;
    TextView textView;

//    public TimerStatusReceiver receiver;
    CountDownTimer countDownTimer;
    Boolean timerrunning = false;
    Boolean timerchecking = false;
    TimeDurationPicker timeDurationInput;
    long x ;
    long millileft=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
        button = findViewById(R.id.button);
imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton6 = findViewById(R.id.imageButton6);
//        receiver = new TimerStatusReceiver();
        textView = findViewById(R.id.textView);
        timeDurationInput = findViewById(R.id.timeDurationInput);
        x=timeDurationInput.getDuration();
    }
    public void checktimer(View v)
    {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
        x=timeDurationInput.getDuration();

        startTimer();

    }


    public void startTimer()
    {


        countDownTimer = new CountDownTimer(x,1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                long hr = millisUntilFinished/3600000;
                long lefthr = millisUntilFinished%3600000;
                long min = lefthr/60000;
                long sec = lefthr%60000;
                textView.setText(hr+":"+min+":"+sec/1000);
                millileft = millisUntilFinished;
                Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                
                intent.putExtra("set",hr+":"+min+":"+sec/1000);
                startService(intent);
            }

            @Override
            public void onFinish() {
                textView.setText("00:00:00");
                Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                intent.putExtra("set","00:00:00");

                startService(intent);
                stopService(intent);
            }
        }.start();
        timerrunning = true;
        timerchecking = true;
    }
    public void stoptimer()
    {
        countDownTimer.cancel();
        timerrunning = false;
    }
    public void pause_resume(View v)
    {
        if(timerrunning) {
            imageButton2.setImageResource(R.drawable.play);
            countDownTimer.cancel();
            long hr = millileft/3600000;
            long lefthr = millileft%3600000;
            long min = lefthr/60000;
            long sec = lefthr%60000;
            textView.setText(hr+":"+min+":"+sec/1000);
//            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
            timerrunning = false;
            Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
            intent.putExtra("set",hr+":"+min+":"+sec/1000);

            startService(intent);
        }
        else
        {
//            LocalBroadcastManager.getInstance(this).registerReceiver(receiver,new IntentFilter(Backgroundnotif.TIME_INFO));
            imageButton2.setImageResource(R.drawable.pause);
           timerrunning = true;
            countDownTimer = new CountDownTimer(millileft,1000) {
               @Override
                public void onTick(long millisUntilFinished) {
                    long hr = millisUntilFinished/3600000;
                    long lefthr = millisUntilFinished%3600000;
                    long min = lefthr/60000;
                    long sec = lefthr%60000;
                    textView.setText(hr+":"+min+":"+sec/1000);
                    millileft = millisUntilFinished;
                   Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                   intent.putExtra("set",hr+":"+min+":"+sec/1000);

                   startService(intent);
                }

                @Override
                public void onFinish() {
                    textView.setText("00:00:00");
                    Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                    intent.putExtra("set","00:00:00");
                    startService(intent);
                    stopService(intent);
                }
            }.start();
        }
    }
    public void reset(View v)
    {
        if(!timerrunning) {
            countDownTimer = new CountDownTimer(0, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    textView.setText(" " + millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                    stopService(intent);
                }
            }.start();
        }
        else {
            countDownTimer.cancel();
        }
        textView.setText("00:00:00");
        millileft=0;
        String set ="00:00:00";
        timerrunning = false;
        imageButton2.setImageResource(R.drawable.pause);
        Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);

        stopService(intent);
//        intent.putExtra("finish",set);

    }
    public void rewind(View v) {

        if (timerchecking)
        {
            Toast.makeText(this, "Rewinding 10s", Toast.LENGTH_SHORT).show();
            if (timerrunning) {

                if (millileft + 10000 <= x) {
                    countDownTimer.cancel();
                    countDownTimer = new CountDownTimer(millileft + 10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            long hr = millisUntilFinished/3600000;
                            long lefthr = millisUntilFinished%3600000;
                            long min = lefthr/60000;
                           long sec = lefthr%60000;
                           textView.setText(hr+":"+min+":"+sec/1000);
                           millileft = millisUntilFinished;
                            Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                            intent.putExtra("set",hr+":"+min+":"+sec/1000);

                            startService(intent);
                       }

                        @Override
                        public void onFinish() {
                            textView.setText("00:00:00");
                            Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                            intent.putExtra("set","00:00:00");

                            startService(intent);
//                            Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                            stopService(intent);
                       }
                   }.start();
                } else {
                    countDownTimer.cancel();
                    countDownTimer = new CountDownTimer(x, 1000) {
                       @Override
                        public void onTick(long millisUntilFinished) {
                           long hr = millisUntilFinished/3600000;
                            long lefthr = millisUntilFinished%3600000;
                            long min = lefthr/60000;
                            long sec = lefthr%60000;
                            textView.setText(hr+":"+min+":"+sec/1000);
                            millileft = millisUntilFinished;
                           Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                           intent.putExtra("set",hr+":"+min+":"+sec/1000);

                           startService(intent);
                        }

                        @Override
                        public void onFinish() {
                            textView.setText("00:00:00");
                            Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                            intent.putExtra("set","00:00:00");

                            startService(intent);
                            stopService(intent);
                        }
                    }.start();
                }
            }
            else
           {
                countDownTimer.cancel();
                if(millileft + 10000<=x) {
                    millileft += 10000;
                    long hr = millileft/3600000;
                    long lefthr = millileft%3600000;
                    long min = lefthr/60000;
                    long sec = lefthr%60000;
                    textView.setText(hr+":"+min+":"+sec/1000);
                    Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                    intent.putExtra("set",hr+":"+min+":"+sec/1000);

                    startService(intent);
                }
               else
                {
                    millileft=x;
                    long hr = millileft/3600000;
                    long lefthr = millileft%3600000;
                    long min = lefthr/60000;
                    long sec = lefthr%60000;
                    textView.setText(hr+":"+min+":"+sec/1000);
                    Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                    intent.putExtra("set",hr+":"+min+":"+sec/1000);

                    startService(intent);

                }
            }
    }
        else
        {
            Toast.makeText(this, "Please start the time first", Toast.LENGTH_SHORT).show();
        }
    }
    public void forward(View v) {

        if (timerchecking) {
            if (timerrunning)
            {
                Toast.makeText(this, "Forwarding 10s", Toast.LENGTH_SHORT).show();
            if (millileft - 10000 >= 0) {
                countDownTimer.cancel();
                countDownTimer = new CountDownTimer(millileft - 10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long hr = millisUntilFinished/3600000;
                        long lefthr = millisUntilFinished%3600000;
                        long min = lefthr/60000;
                        long sec = lefthr%60000;
                        textView.setText(hr+":"+min+":"+sec/1000);
                        millileft = millisUntilFinished;
                        Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                        intent.putExtra("set",hr+":"+min+":"+sec/1000);

                        startService(intent);
                    }

                    @Override
                    public void onFinish() {
                        textView.setText("00:00:00");
                        Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                        intent.putExtra("set","00:00:00");

                        startService(intent);
                        stopService(intent);
                    }
                }.start();
            } else {
                countDownTimer.cancel();
                countDownTimer = new CountDownTimer(0, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                       long hr = millisUntilFinished/3600000;
                        long lefthr = millisUntilFinished%3600000;
                        long min = lefthr/60000;
                        long sec = lefthr%60000;
                        textView.setText(hr+":"+min+":"+sec/1000);
                        millileft = millisUntilFinished;
                        Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                        intent.putExtra("set",hr+":"+min+":"+sec/1000);

                        startService(intent);
                    }

                    @Override
                    public void onFinish() {
                        textView.setText("00:00:00");
                        Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                        intent.putExtra("set","00:00:00");

                        startService(intent);
                        stopService(intent);
                    }
                }.start();
            }
        }
            else
            {
                countDownTimer.cancel();
                if(millileft - 10000>=0) {
                    millileft -= 10000;
                   long hr = millileft/3600000;
                    long lefthr = millileft%3600000;
                    long min = lefthr/60000;
                    long sec = lefthr%60000;
                    textView.setText(hr+":"+min+":"+sec/1000);
                    Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                    intent.putExtra("set",hr+":"+min+":"+sec/1000);
                    startService(intent);
                }
                else
                {
                    countDownTimer.cancel();
millileft=0;
                            textView.setText("00:00:00");
                    Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
                    intent.putExtra("set","00:00:00");
                    startService(intent);
                    stopService(intent);
                }
            }
    }
        else
        {
            Toast.makeText(this, "Please start the timer first", Toast.LENGTH_SHORT).show();
        }
    }

//    public class TimerStatusReceiver extends BroadcastReceiver
//    {
//
//            @Override
//            public void onReceive(Context context, Intent intent)
//            {
//                if (intent != null && intent.getAction().equals(Backgroundnotif.TIME_INFO)) {
//                    if (intent.hasExtra("VALUE")) {
//                        textView.setText(intent.getStringExtra("VALUE"));
//                    }
//                    millileft = intent.getLongExtra("millileft",0);
//                    intent.putExtra("Start",millileft);
//                }
//
//
////                imageButton3.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        if(millileft + 10000<=x) {
////                            Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
////millileft += 10000;
////                            intent.putExtra("Rewind", millileft);
////                            startService(intent);
////                            timerrunning = true;
////                        }
////                        else
////                        {
////                            Intent intent = new Intent(MainActivity.this, Backgroundnotif.class);
////millileft=x;
////                            intent.putExtra("Rewind", x);
////                            startService(intent);
////                            timerrunning = true;
////                        }
////                    }
////                });
//            }
//
//    }



}