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