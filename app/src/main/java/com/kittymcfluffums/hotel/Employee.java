package com.kittymcfluffums.hotel;

/**
 * Employee object
 */
public class Employee {
    private String firstName, lastName, demographic, gender, position, date_employed;
    private double salary;
    private int age;

    /**
     * Create new employee
     * @param firstName First name
     * @param lastName Last name
     * @param demographic Demographic
     * @param gender Gender
     * @param position Position
     * @param date_employed Date employed
     * @param salary Salary
     * @param age Age
     */
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

    /**
     * Get first name
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get last name
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get demographic
     * @return Demographic
     */
    public String getDemographic() {
        return demographic;
    }

    /**
     * Get gender
     * @return Gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Get position
     * @return Position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Get date employed
     * @return Date employed
     */
    public String getDateEmployed() {
        return date_employed;
    }

    /**
     * Get salary
     * @return Salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Get age
     * @return Age
     */
    public int getAge() {
        return age;
    }
}
