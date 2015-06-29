package com.getpebble.WrestlingWatch;

/**
 * Created by adamcooke on 6/28/15.
 */
public class Reversal implements Point {

    private int value;
    private String name;
    private int period;

    public Reversal(int period) {
        this.value = 2;
        this.period = period;
        this.name = "Reversal";
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
