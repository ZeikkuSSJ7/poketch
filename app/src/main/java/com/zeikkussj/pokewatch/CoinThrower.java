package com.zeikkussj.pokewatch;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class CoinThrower {
    private MainActivity main;
    private ImageView coin;
    private AnimationDrawable flipCoin;
    private RelativeLayout rv;
    public CoinThrower(MainActivity main){
        this.main = main;
    }
    int initialPos;
    public void coinThrower(){
        main.initialize(R.layout.activity_coin);
        rv = main.findViewById(R.id.wrapCoin);
        coin = main.findViewById(R.id.coin);
        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin.setBackgroundResource(R.drawable.throw_coin);
                flipCoin = (AnimationDrawable) coin.getBackground();
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        main.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                flipCoin.start();
                            }
                        });
                        try {
                            MediaPlayer playSound;
                            playSound = MediaPlayer.create(main.getApplicationContext(), R.raw.bounce);
                            playSound.start();
                            initialPos = coin.getPaddingBottom();
                            final CoinContainer cc = new CoinContainer(-630, 520);
                            for (int i = 0; i < 4; i++) {
                                main.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rv.startAnimation(setCoinMove(initialPos, cc.getTo(), cc.getDuration()));

                                    }
                                });
                                Thread.sleep(cc.getDuration());
                                main.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rv.startAnimation(setCoinMove(cc.getTo(), initialPos, cc.getDuration()));
                                    }
                                });
                                Thread.sleep(cc.getDuration());
                                if (i == 0){
                                    cc.setTo(-430);
                                    cc.setDuration(330);
                                }
                                if (i == 1){
                                    cc.setTo(-250);
                                    cc.setDuration(220);
                                }
                                if (i == 2){
                                    cc.setTo(-105);
                                    cc.setDuration(150);
                                }
                            }
                            Thread.sleep(400);
                            playSound.release();
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }

                });
                th.start();
                Thread th2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(2445);

                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        main.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setCoin();
                            }
                        });
                    }
                });
                th2.start();
            }
        });
    }
    private TranslateAnimation setCoinMove(int initialY, int toY, int duration){
        TranslateAnimation ta = new TranslateAnimation(0,0, initialY, toY);
        ta.setDuration(duration);
        ta.setFillAfter(true);
        return ta;
    }
    private void setCoin(){
        Random ran = new Random();
        if (ran.nextBoolean()){
            coin.setBackgroundResource(R.drawable.coin_pokeball);
        } else {
            coin.setBackgroundResource(R.drawable.coin_magikarp);
        }
    }
}
