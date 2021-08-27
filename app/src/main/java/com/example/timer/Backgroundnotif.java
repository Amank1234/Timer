package com.example.timer;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Backgroundnotif extends Service {
    Notification notification;
    String set;
    String finish;
    long millileft;
    long x;
    long y;
    public static final String TIME_INFO = "time_info";
    CounterClass timer;
//    RewindClass timer1;
    public Backgroundnotif()
    {

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        x=intent.getLongExtra("Start",0);
        y=intent.getLongExtra("Rewind",0);
set = intent.getStringExtra("set");
//timer = new CounterClass(x,1000);
//timer.start();
//        timer1 = new RewindClass(y,1000);
//        timer1.start();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        Notification notification = new NotificationCompat.Builder(this,"channelid")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(set)
                .setContentIntent(pendingIntent).build();
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelid");
//        builder.setSmallIcon(R.mipmap.ic_launcher).setContentText("This is my service").setContentIntent(pendingIntent);
//        notification = builder.build();

        startForeground(101, notification);

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
//        timer.cancel();
        super.onDestroy();
        Intent timerInfoIntent = new Intent();
        finish=timerInfoIntent.getStringExtra("finish");
//        timerInfoIntent.putExtra("VALUE", "Stopped");
        stopForeground(true);
        stopSelf();


//        LocalBroadcastManager.getInstance(Backgroundnotif.this).sendBroadcast(timerInfoIntent);
    }

//    public void showNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelid");
//        builder.setSmallIcon(R.mipmap.ic_launcher).setContentText("This is my service").setContentTitle("Title");
//        notification = builder.build();
////        startForeground(123,notification);
//    }

    private class CounterClass extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long hr = millisUntilFinished/3600000;
            long lefthr = millisUntilFinished%3600000;
            long min = lefthr/60000;
            long sec = lefthr%60000;
             set = hr+":"+min+":"+sec/1000;
            millileft = millisUntilFinished;
            Intent timerInfoIntent = new Intent(TIME_INFO);
            timerInfoIntent.putExtra("VALUE", set);
            timerInfoIntent.putExtra("millileft", millileft);
            LocalBroadcastManager.getInstance(Backgroundnotif.this).sendBroadcast(timerInfoIntent);

        }



        @Override
        public void onFinish() {
            stopForeground(true);
            stopSelf();
            Intent timerInfoIntent = new Intent(TIME_INFO);
            timerInfoIntent.putExtra("VALUE", "00:00:00");
            LocalBroadcastManager.getInstance(Backgroundnotif.this).sendBroadcast(timerInfoIntent);
        }


}


//    private class RewindClass extends CountDownTimer{
//        /**
//         * @param millisInFuture    The number of millis in the future from the call
//         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
//         *                          is called.
//         * @param countDownInterval The interval along the way to receive
//         *                          {@link #onTick(long)} callbacks.
//         */
//        public RewindClass(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            long hr = millisUntilFinished/3600000;
//            long lefthr = millisUntilFinished%3600000;
//            long min = lefthr/60000;
//            long sec = lefthr%60000;
//            set = hr+":"+min+":"+sec/1000;
//            millileft = millisUntilFinished;
//            Intent timerInfoIntent = new Intent(TIME_INFO);
//            timerInfoIntent.putExtra("VALUE", set);
//            LocalBroadcastManager.getInstance(Backgroundnotif.this).sendBroadcast(timerInfoIntent);
//        }
//
//        @Override
//        public void onFinish() {
//            stopForeground(true);
//            stopSelf();
//            Intent timerInfoIntent = new Intent(TIME_INFO);
//            timerInfoIntent.putExtra("VALUE", "00:00:00");
//            LocalBroadcastManager.getInstance(Backgroundnotif.this).sendBroadcast(timerInfoIntent);
//        }
//    }
}


