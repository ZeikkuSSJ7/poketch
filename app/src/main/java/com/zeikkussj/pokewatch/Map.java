package com.zeikkussj.pokewatch;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class Map {
    private MainActivity main;
    private RelativeLayout sinnoh;
    private TextView tvMapLocation;
    private TextView tvProgress;
    private ImageView ivLocator;
    private long start;
    private long currentTime;
    private static final MapLocator[] locators = {
            new MapLocator("Pueblo Hojaverde", 75, 425),
            new MapLocator("Ruta 201", 75, 406),
            new MapLocator("Orilla Veraz", 37, 388),
            new MapLocator("Ruta 201", 75, 406),
            new MapLocator("Pueblo Arena", 113, 406),
            new MapLocator("Ruta 202", 113, 388),
            new MapLocator("Ciudad Jubileo", 94, 350),
            new MapLocator("Ruta 203", 131, 350),
            new MapLocator("Ciudad Pirita", 169, 350),
            new MapLocator("Ruta 204", 113, 313),
            new MapLocator("Pueblo Aromaflor", 113, 275),
            new MapLocator("Ruta 205 Sur", 131, 256),
            new MapLocator("Valle Eólico", 150, 294),
            new MapLocator("Ruta 205 Sur", 131, 256),
            new MapLocator("Bosque Vetusto", 132, 219),
            new MapLocator("Ruta 205 Norte", 170, 219),
            new MapLocator("Ciudad Vetusta", 188, 219),
            new MapLocator("Ruta 206", 188, 256),
            new MapLocator("Ruta 207", 187, 331),
            new MapLocator("Monte Corona Sur", 187, 331),
            new MapLocator("Ruta 208", 244, 331),
            new MapLocator("Ciudad Corazón", 281, 313),
            new MapLocator("Ruta 209", 319, 313),
            new MapLocator("Pueblo Sosiego", 338, 294),
            new MapLocator("Ruta 210", 300, 219),
            new MapLocator("Ruta 215", 356, 256),
            new MapLocator("Ciudad Rocavelo", 412, 256),
            new MapLocator("Ruta 214", 431, 293),
            new MapLocator("Orilla Valor", 413, 350),
            new MapLocator("Ruta 213", 394, 388),
            new MapLocator("Ciudad Pradera", 356, 388),
            new MapLocator("Ruta 212", 281, 350),
            new MapLocator("Ciudad Corazon", 281, 313),
            new MapLocator("Ruta 209", 319, 313),
            new MapLocator("Pueblo Sosiego", 338, 294),
            new MapLocator("Ruta 210", 300, 219),
            new MapLocator("Pueblo Caelestis", 281, 219),
            new MapLocator("Ruta 211 Este", 263, 219),
            new MapLocator("Ruta 211 Oeste", 225, 219),
            new MapLocator("Ciudad Vetusta", 188, 219),
            new MapLocator("Ciudad Jubileo", 94, 350),
            new MapLocator("Ruta 218", 56, 350),
            new MapLocator("Ciudad Canal", 38, 331),
            new MapLocator("Isla Hierro", 75, 200),
            new MapLocator("Ciudad Canal", 38, 331),
            new MapLocator("Ciudad Pradera", 356, 388),
            new MapLocator("Ruta 213", 394, 388),
            new MapLocator("Orilla Valor", 413, 350),
            new MapLocator("Pueblo Hojaverde", 75, 425),
            new MapLocator("Ruta 201", 75, 406),
            new MapLocator("Orilla Veraz", 37, 388)
    };
    public Map(MainActivity main){
        this.main = main;
    }
    public void map(){
        main.initialize(R.layout.activity_map);
        sinnoh = main.findViewById(R.id.sinnoh);
        tvMapLocation = main.findViewById(R.id.tvMapLocator);
        ivLocator = main.findViewById(R.id.locator);
        tvProgress = main.findViewById(R.id.progress);
        initializeMapCurrentTime();
        uiThreadLocator();
        porcentajeCurso();
    }
    private void initializeMapCurrentTime(){
        Calendar c = Calendar.getInstance();
        currentTime = c.getTimeInMillis();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(getAccurateStartYear(c), 8, 1);
        start = startCalendar.getTimeInMillis();
    }
    private int getAccurateStartYear(Calendar c){
        if (c.get(Calendar.MONTH) >= 8)
            return c.get(Calendar.YEAR);
        else
            return c.get(Calendar.YEAR) - 1;
    }
    private void uiThreadLocator(){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(28, 24);
                        final MapLocator ml = locators[getPosition()];
                        lp.setMargins(ml.getX(), ml.getY(), 0, 0);
                        main.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMapLocation.setText(ml.getLocation());
                                ivLocator.setBackgroundResource(R.drawable.map_locator);
                                ivLocator.setLayoutParams(lp);
                            }
                        });
                    for(;;){
                        Thread.sleep(1000);
                        main.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivLocator.setVisibility(View.VISIBLE);
                            }
                        });
                        Thread.sleep(1000);
                        main.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivLocator.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }
    private void porcentajeCurso(){
        long current = currentTime - start;
        current = current/1000;
        current = current/60;
        current = current/60;
        long end = 31536000004L;
        end = end/1000;
        end = end/60;
        end = end/60;
        double porcentaje = current * 100;
        porcentaje = porcentaje/end;

        String fin = "Progreso del curso: " + String.valueOf(porcentaje).substring(0 , 5) + "%";
        tvProgress.setText(fin);
    }
    private int getPosition(){
        // 31536000004 max
        long limit = 31536000004L/100;
        long pos = currentTime - start;
        Log.d("aaaa", pos + "");
        for (int i = 0; i < 100; i++) {
            if (pos < limit * i && pos > limit * (i - 1))
                return i;
        }
        return 0;
    }
}