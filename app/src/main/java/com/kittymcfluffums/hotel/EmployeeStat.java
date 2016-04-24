package com.kittymcfluffums.hotel;


/**
 * Created by charlesarvey on 4/23/16.
 */
public class EmployeeStat {
    private String metric;
    private int metric_value;

    public EmployeeStat(String metric, int metric_value) {
        this.metric = metric;
        this.metric_value = metric_value;
    }

    public String getMetric() {
        return metric;
    }

    public int getMetric_value() {
        return metric_value;
    }
}
