package com.getpebble.WrestlingWatch;

import java.io.Serializable;

/**
 * Created by adamcooke on 7/19/15.
 */
public class Club implements Serializable{

    private String name;
    private String city;

    public Club(String name) {
        this(name, "unknown");
    }

    public Club(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
