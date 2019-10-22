package com.zeikkussj.pokewatch;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

public class Buttons {
    public static MainActivity context;
    public static void setListeners(ImageView buttonTop, ImageView buttonBottom){
        buttonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer playSound;
                playSound = MediaPlayer.create(context, R.raw.click);
                playSound.start();
                onClickButton(+1);
            }
        });
        buttonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer playSound;
                playSound = MediaPlayer.create(context, R.raw.click);
                playSound.start();
                onClickButton(-1);
            }
        });
    }
    private static void onClickButton(final int screenStep){
        final Screen screen = new Screen(context);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            screen.close();
                        }
                    });
                    Thread.sleep(960);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.screen += screenStep;
                            context.selectScreen(MainActivity.screen);
                        }
                    });
                    Thread.sleep(970);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }
}
