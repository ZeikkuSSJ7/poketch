package com.zeikkussj.pokewatch;

public class MapLocator {
    private String location;
    private int x;
    private int y;
    public MapLocator(String location, int marginleft, int marginTop){
        this.location = location;
        this.x = marginleft;
        this.y = marginTop;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLocation() {
        return location;
    }
}
