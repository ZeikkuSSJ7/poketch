package com.zeikkussj.pokewatch;

public class CoinContainer {
    private int to;
    private int duration;
    public CoinContainer(int to, int duration){
        this.duration = duration;
        this.to = to;
    }

    public int getDuration() {
        return duration;
    }

    public int getTo() {
        return to;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
