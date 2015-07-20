package com.getpebble.WrestlingWatch;

/**
 * Created by adamcooke on 6/28/15.
 */
public class Escape implements Point {

    private int value;
    private int period;
    private String name;

    public Escape(int period) {
        this.value = 1;
        this.period  = period;
        this.name = "Escape";
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
