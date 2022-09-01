import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Logics {
    private Scanner sc = new Scanner(System.in);
    
    public void addParkingSlot(CarPark carPark) {
        String userInput;
        boolean invalidYesNo = true;
        boolean invalidSlotNo = true;
        String checkResult;
        String message = "";
        boolean forStaff = true;
        
        // ask user if the parking slot is for staff
        do {
            System.out.println("Is this parking slot for staff (y/n)");
            userInput = sc.next();
            // check for valid y/n answer
            if (userInput.equalsIgnoreCase("n")) {
                message = "This parking slot will be for visitor";
                forStaff = false;
                invalidYesNo = false;
            } else if (userInput.equalsIgnoreCase("y")) {
                message = "This parking slot will be for staff";
                invalidYesNo = false;
            } else {
                message = "Please enter \"y\" or \"n\".";
            }
            System.out.println(message);
        } while (invalidYesNo);

        // check if the slot number entered is valid
        // right format + is not yet created
        do {
            System.out.println("Enter the new parking slot number:");
            userInput = sc.next();
            checkResult = checkSlotNumber(userInput, carPark);

            if (checkResult.equals("incorrect")) {
                message = "The parking slot number should contain an uppcase letter\nfollowed by two digits.";
            } else if (checkResult.equals("unavailable")) {
                message = "This number has already been assigned to a parking slot";
            } else if (checkResult.equals("available")) {
                invalidSlotNo = false;
                ParkingSlot parkingSlot = new ParkingSlot(userInput, forStaff);
                carPark.addParkingSlot(parkingSlot);
                message = "Parking slot " + userInput + " has been created";
            }
            System.out.println(message);
        } while (invalidSlotNo);
    }


    // ------------------- checkSlotNumber() ------------------------
    // method to check the validity of parking slot number
    // whether it is of the right format, eg. A10, B02
    // AND whether it has already been added to the car park
    // return "available" if the slot number is of correct format and hasn't been added
    // return "unavailable" if the slot number is of correct format but has already been added
    // return "incorrect" if the slot number provided is of the wrong format
    public String checkSlotNumber(String slotNumber, CarPark carPark) {
        // regex of slot number format
        final String slotNumberRegex = "^[A-Z]{1}[0-9]{2}$";
        // compile the regex string
        Pattern pattern = Pattern.compile(slotNumberRegex);
        Matcher matcher = pattern.matcher(slotNumber);

        // if the slotNumber param has the correct format
        if (matcher.find()) {
            // loop through carPark to check if the slot number is already taken
            for (int i = 0; i < carPark.getSize(); i ++) {
                // the parking slot number in the current iteration
                String currentSlotNumber = carPark.getParkingSlot(i).getSlotNumber();
                // if the same slot number is already in carPark, return "unavailable"
                if (currentSlotNumber.equals(slotNumber)) {
                    return "unavailable";
                } // close if
            } // close for
            // if slot number hasn't already been added to carPark, return "available"
            return "available";
        } // close if(matcher.find())
        // return "incorrect" because the slotNumber param is not in the correct format
        return"incorrect";
    } // close checkSlotNumber() method


    // ------------------- checkRegoNumber() ------------------------
    // method to check the validity of rego number
    // whether it is of the right format, eg. T2345
    // AND whether it has already been added to the car park
    // return "available" if the rego is of correct format and hasn't been added
    // return "unavailable" if the rego is of correct format but has already been added
    // return "incorrect" if the rego provided is of the wrong format
    public String checkRegoNumber(String regoNumber, CarPark carPark) {
        // declare the regex format for rego number
        final String regoNumberRegex = "^[A-Z]{1}[1-9]{4}$";
        // compile and match the patter to regoNumber
        Pattern pattern = Pattern.compile(regoNumberRegex);
        Matcher matcher = pattern.matcher(regoNumber);
        // if the regoNumber is of correct format
        if (matcher.find()) {
            // loop through carPark to find if the same regoNumber has been parked
            for (int i = 0; i < carPark.getSize(); i ++) {
                // declare the current rego number in the loop
                String currentRegoNumber = carPark.getParkingSlot(i).getRegoNumber();
                // if the current rego number is the same as the param
                // the rego number has already been parked
                if (currentRegoNumber.equals(regoNumber)) {
                    return "unavailable";
                }
            } // close for()
            return "available";
        } // close if (matcher.find())
        return "incorrect";
    } // close checkRegoNumber() method
}
