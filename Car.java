/*
 * Car class
 * @description: constructor, getter and setter method for the class Car
 * - Instance variable:
 * |- fName: first name of car owner
 * |- lName: last name of car owner
 * |- isStaff: true if the owner is a staff, false if a visitor
 * |- regoNumber: the car's registration number
 * 
 * @author: Peter LUONG - 1038 11153
 * @version: JRE-17
 * @date: 11 Sep 2022
 * @unit: COS70006 - Object-Oriented Programming
 */

public class Car {
    private String fName;
    private String lName;
    private boolean isStaff;
    private String regoNumber;

    // ------------ CONSTRUCTORS ---------------

    public Car(String regoNumber, String fName, String lName, boolean isStaff) {
        this.regoNumber = regoNumber;
        this.fName = fName;
        this.lName = lName;
        this.isStaff = isStaff;
    }

    public Car() {};

    // =========== END CONSTRUCTORS ============

    // --------------- GETTERS -----------------
    // method to get owner names
    public String getNames() {
        return fName + lName;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    // method to find if the car is a staff car
    public boolean isStaff() {
        return isStaff;
    }

    // method to get rego number
    public String getRegoNumber() {
        return regoNumber;
    }
    // ============= END GETTERS ===============

    // --------------- SETTERS -----------------
    // method to set rego number
    public void setRegoNumber(String regoNumber) {
        this.regoNumber = regoNumber;
    }

    // method to set owner names
    public void setNames(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    // method to set whether the car is a staff car
    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }
    // ============= END SETTERS ================
}