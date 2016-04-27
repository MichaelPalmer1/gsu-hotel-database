package com.kittymcfluffums.hotel;


/**
 * Created by charlesarvey on 4/23/16.
 */
public class EmployeeStat {
    private String metric;
    private String metric_value;

    // constructor that should take in a metric name (employee count) and a string-resolved
    // method value as metric_value
    public EmployeeStat(String metric, String metric_value) {
        this.metric = metric;
        this.metric_value = metric_value;
    }

    public String getMetric() {
        return metric;
    }

    public String getMetric_value() {
        return metric_value;
    }
}
