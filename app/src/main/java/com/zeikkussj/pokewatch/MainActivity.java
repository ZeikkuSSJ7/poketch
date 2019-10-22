package com.zeikkussj.pokewatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static int MAX_SCREENS = 4;
    public static int screen;
    boolean opening;
    Clock clock;
    CoinThrower coinThrower;
    Map map;
    Roullette roullette;
    ImageView buttonTop;
    ImageView buttonBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = 1;
        opening = true;
        selectScreen(screen);
        opening = false;
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    public void selectScreen(int screen){
        if (screen == 1){
            clock = new Clock(this);
            clock.clock();
        }
        if (screen == 2){
            coinThrower = new CoinThrower(this);
            coinThrower.coinThrower();
        }
        if (screen == 3){
            map = new Map(this);
            map.map();
        }
        if (screen == 4){
            roullette = new Roullette(this);
            roullette.roullette();
        }
        if (screen > MAX_SCREENS){
            MainActivity.screen = 1;
            selectScreen(MainActivity.screen);
        }
        if (screen < 1){
            MainActivity.screen = MAX_SCREENS;
            selectScreen(MainActivity.screen);
        }
    }
    public void initialize(int layoutResID){
        setContentView(layoutResID);
        buttonTop = findViewById(R.id.button_top);
        buttonBottom = findViewById(R.id.button_bottom);
        setButtons();
        Buttons.context = this;
        Buttons.setListeners(buttonTop, buttonBottom);
        new Screen(this).open(opening);
    }
    private void setButtons(){
        buttonTop.setBackgroundResource(R.drawable.watch_button_top_anim);
        buttonBottom.setBackgroundResource(R.drawable.watch_button_bottom_anim);
    }
}
