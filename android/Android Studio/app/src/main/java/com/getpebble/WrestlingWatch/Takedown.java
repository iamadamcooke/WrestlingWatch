package com.getpebble.WrestlingWatch;

/**
 * Created by adamcooke on 6/28/15.
 */
public class Takedown implements Point{

    private int value;
    private String name;
    private int period;

    public Takedown(int period) {
        this.value = 2;
        this.period = period;
        this.name = "Takedown";
    }

    public int getPeriod() {
        return this.period;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

}
