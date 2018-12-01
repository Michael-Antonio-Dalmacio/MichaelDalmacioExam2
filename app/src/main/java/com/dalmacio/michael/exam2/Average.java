package com.dalmacio.michael.exam2;

public class Average {
    String fname, lname;
    Long average;

    public Average(String fname, String lname, Long average) {
        this.fname = fname;
        this.lname = lname;
        this.average = average;
    }

    public Average(){

    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public Long getAverage() {
        return average;
    }
}
