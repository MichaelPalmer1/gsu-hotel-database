package com.kittymcfluffums.hotel;

/**
 * Guest object
 */
public class Guest {
    private String firstName, middleName, lastName, phoneNumber, email;

    /**
     * Create new instance
     * @param firstName First name
     * @param middleName Middle name
     * @param lastName Last name
     * @param phoneNumber Phone number
     * @param email Email
     */
    public Guest(String firstName, String middleName, String lastName,
                 String phoneNumber, String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Get first name
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get middle name
     * @return Middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Get last name
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get phone number
     * @return Phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get email
     * @return Email
     */
    public String getEmail() {
        return email;
    }
}
