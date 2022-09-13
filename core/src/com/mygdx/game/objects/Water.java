package com.mygdx.game.objects;

public class Water {

    private int Water_X;
    private int Water_Y;

    public Water(int water_X, int water_Y) {
        Water_X = water_X;
        Water_Y = water_Y;
    }

    @Override
    public String toString() {
        return "Water{" +
                "Water_X=" + Water_X +
                ", Water_Y=" + Water_Y +
                '}';
    }

    public int getWater_X() {
        return Water_X;
    }

    public void setWater_X(int water_X) {
        Water_X = water_X;
    }

    public int getWater_Y() {
        return Water_Y;
    }

    public void setWater_Y(int water_Y) {
        Water_Y = water_Y;
    }
}
