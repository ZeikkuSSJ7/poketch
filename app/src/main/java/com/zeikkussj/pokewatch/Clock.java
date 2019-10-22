package com.zeikkussj.pokewatch;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class Clock {
    private MainActivity main;
    private TextView tvReloj;
    private TextView tvFecha;
    private View vIlumn;
    public Clock(MainActivity main){
        this.main = main;
    }
    public void clock(){
        main.initialize(R.layout.activity_main);
        Calendar c = Calendar.getInstance();;
        tvReloj = main.findViewById(R.id.tvReloj);
        tvFecha = main.findViewById(R.id.tvFecha);
        vIlumn = main.findViewById(R.id.watchScreen);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minute = checkMinute(c.get(Calendar.MINUTE));
        tvReloj.setText(main.getApplicationContext().getString(R.string.hora, hour, minute));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String month = checkMonth(c.get(Calendar.MONTH));
        String year = String.valueOf(c.get(Calendar.YEAR));
        tvFecha.setText(main.getApplicationContext().getString(R.string.fecha, day, month, year));
        startUpdatingClock();
        vIlumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vIlumn.setBackgroundResource(R.drawable.clock_background);
            }
        });
        vIlumn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                vIlumn.setBackgroundResource(R.drawable.clock_background_ilumn);
                return false;
            }
        });
    }
    private void startUpdatingClock(){
        Thread thUpdate = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    main.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar newCalendar = Calendar.getInstance();
                            String hour = String.valueOf(newCalendar.get(Calendar.HOUR_OF_DAY));
                            String minute = String.valueOf(checkMinute(newCalendar.get(Calendar.MINUTE)));
                            String day = String.valueOf(newCalendar.get(Calendar.DAY_OF_MONTH));
                            String month = checkMonth(newCalendar.get(Calendar.MONTH));
                            String year = String.valueOf(newCalendar.get(Calendar.YEAR));
                            tvFecha.setText(main.getApplicationContext().getString(R.string.fecha, day, month, year));
                            tvReloj.setText(main.getApplicationContext().getString(R.string.hora, hour, minute));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thUpdate.start();
    }
    private String checkMinute(int currentMinute){
        if (currentMinute < 10){
            return "0" + currentMinute;
        }
        return String.valueOf(currentMinute);
    }
    private String checkMonth(int month){
        String[] months = main.getResources().getStringArray(R.array.months);
        return months[month];
    }
}
