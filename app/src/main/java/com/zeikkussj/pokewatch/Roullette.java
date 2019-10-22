package com.zeikkussj.pokewatch;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class Roullette {
    private MainActivity main;
    private ImageView buttonStart;
    private ImageView buttonStop;
    private ImageView buttonCancel;
    private ImageView roulletteNeedle;
    private AnimationDrawable animateNeedle;
    public Roullette(MainActivity main){
        this.main = main;
    }
    public void roullette(){
        main.initialize(R.layout.activity_roullette);
        buttonStart = main.findViewById(R.id.roullete_start);
        buttonStop = main.findViewById(R.id.roullete_stop);
        buttonCancel = main.findViewById(R.id.roullete_cancel);
        roulletteNeedle = main.findViewById(R.id.roullette_needle);
        setButtons();
    }
    int i;
    Thread th;
    private void setButtons(){
        buttonStart.setBackgroundResource(R.drawable.roullette_start_anim);
        buttonStop.setBackgroundResource(R.drawable.roullette_stop_anim);
        buttonCancel.setBackgroundResource(R.drawable.roullette_cancel_anim);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Random ran = new Random();
                th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int max = 20;
                            int speedLimit = 5;
                            for (int j = 0; j < max; j++) {
                                for (i = 1; i <= 360; i = i + 10) {
                                    main.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            roulletteNeedle.setRotation(i);
                                        }
                                    });
                                    Thread.sleep(speedLimit);
                                }
                            }
                            main.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    roulletteNeedle.setRotation(ran.nextInt(6) * 60);
                                }
                            });
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                });
                th.start();

            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (th != null && th.isAlive()){
                    th.interrupt();
                    roulletteNeedle.setRotation(i);
                }

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                th.interrupt();
                roulletteNeedle.setRotation(0);
            }
        });
    }
}
