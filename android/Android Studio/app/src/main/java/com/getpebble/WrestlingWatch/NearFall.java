package com.getpebble.WrestlingWatch;

/**
 * Created by adamcooke on 6/28/15.
 */
public class NearFall  implements Point{

    private int value;
    private String name;
    private int period;

    public NearFall(int period) {
        this.value = 2;
        this.period = period;
        this.name = "Near Fall";
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
