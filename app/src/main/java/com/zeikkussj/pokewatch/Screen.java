package com.zeikkussj.pokewatch;

import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Screen {
    private ImageView vScreen;
    private MainActivity main;
    public Screen(MainActivity main){
        this.main = main;
        vScreen = main.findViewById(R.id.changeScreen);
    }
    private void animate(int initialPos, int mov, int duration)throws InterruptedException {
        vScreen.startAnimation(getAnimation(initialPos, mov, duration));
    }
    private TranslateAnimation getAnimation(int initialY, int toY, int duration){
        TranslateAnimation ta = new TranslateAnimation(0,0, initialY, toY);
        ta.setDuration(duration);
        ta.setFillAfter(true);
        return ta;
    }
    public void open(boolean opening){
        if (!opening){
            vScreen.setBackgroundResource(R.drawable.watch_change_screen);
            try {
                animate(0, -1940, 720);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void close(){
        vScreen.setBackgroundResource(R.drawable.watch_change_screen);
        try {
            animate(-1940, 0, 720);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
