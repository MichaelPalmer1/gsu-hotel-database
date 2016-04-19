package com.kittymcfluffums.hotel;

public class Employee {
    private String firstName, lastName, demographic, gender, position, date_employed;
    private double salary;
    private int age;

    public Employee(String firstName, String lastName, String demographic, String gender,
                    String position, String date_employed, double salary, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.demographic = demographic;
        this.gender = gender;
        this.position = position;
        this.date_employed = date_employed;
        this.salary = salary;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDemographic() {
        return demographic;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public String getDateEmployed() {
        return date_employed;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }
}
