import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Logics {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<String> yes = new ArrayList<String>() {{add("yes"); add("y");}};
    private ArrayList<String> no = new ArrayList<String>() {{add("no"); add("n");}};
    private String errorMessageYN = "Enter \"y\" or \"n\" only.";
    private Helper helper = new Helper();

    // -------------------------- Option 1 --------------------------
    // Add a parking slot
    // Ask user for the following info:
    // - New parking slot number
    // - Whether the parking slot is for staff
    // Logics: Check whether number entered is correct
    // Use: CarPark.addParkingSLot(String slotNumber, boolean forStaff)
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
            if (evaluateYN(yes, no, userInput) == "n") {
                message = "This parking slot will be for visitor";
                forStaff = false;
                invalidYesNo = false;
            } else if (evaluateYN(yes, no, userInput) == "y") {
                message = "This parking slot will be for staff";
                invalidYesNo = false;
            } else {
                System.out.print(errorMessageYN);
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

    // -------------------------- Option 2 --------------------------
    // Delete parking slot
    // Ask user for the following info:
    // - Parking slot number to delete
    // Logics:
    // - Check whether the parking slot to be deleted is valid
    // - Check whether the parking slot is occupied, break if it is
    public void deleteParkingSlot(CarPark carPark) {
        String inputSlot;
        boolean invalidSlot = true;
        boolean invalidYN = true;
        String inputYN;
        boolean isOccupied;
        String checkResult;
        ParkingSlot parkingSlot;
        // prompt user for parking slot number
        System.out.println("Enter the parking slot number to be deleted");
        // evaluate the format of input & whether the parking slot exists
        do {
            inputSlot = sc.next();
            checkResult = checkSlotNumber(inputSlot, carPark);
            if (checkResult.equals("available")) {
                System.out.println("Slot " + inputSlot + " does not exist");
            } else if (checkResult.equals("unavailable")) { 
                // "unavailable" means that the same slot number exists
                invalidSlot = false;
            } else if (checkResult.equals("incorrect")) {
                System.out.println("Parking slot number should contain an uppercase letter\nfollowed by two digits");
            }
        } while (invalidSlot);

        // continue if parking slot number enter by user is valid
        if (!invalidSlot) {
            parkingSlot = carPark.getParkingSlot(inputSlot);
            isOccupied = parkingSlot.isOccupied();
            // check if the car park is occupied
            // if yes, remove the car
            if (isOccupied) {
                String regoNumber = parkingSlot.getRegoNumber();
                System.out.println(regoNumber + " is currently parked in " + inputSlot);
                System.out.println("Do you want to remove " + regoNumber + " before deleting " + inputSlot + "?");
                do {
                    inputYN = sc.next();
                    // if user wants to remove the car
                    if (evaluateYN(yes, no, inputYN) == "y") {
                        String [] output = parkingSlot.removeCar();
                        System.out.println(regoNumber + "parked for " + output[3] + " has been removed");
                        carPark.deleteSlot(inputSlot);
                        invalidYN = false;
                    } else if (evaluateYN(yes, no, inputYN) == "n") {
                        // if user does not want to remove the car
                        System.out.println(inputSlot + " will not be deleted.");
                        invalidYN = false;
                        break;
                    } else {
                        System.out.println(errorMessageYN);
                    }
                } while (invalidYN);
            } // close if (isOccupied)
            
            // delete the parking slot
            carPark.deleteSlot(inputSlot);
            System.out.println(inputSlot + " has been deleted.");
        } // close if (!invalidSlot)
    }

    // -------------------------- Option 3 -------------------------- 
    // method to list info of all slot numbers
    public void listSlots(CarPark carPark) {
        int size = carPark.getSize();
        // print header
        helper.printHeader("list slots");
        // loop through every parking slot in carPark
        for (int i = 0; i < size; i ++) {
            boolean isOccupied = carPark.getParkingSlot(i).isOccupied();
            String [] info = carPark.getSlotInfo(i);
            String slotNumber = helper.limitStr(info[0], " ", 9);
            String regoNumber;
            if (!isOccupied) regoNumber = info[1]; else regoNumber = "Vacant";
            regoNumber = helper.limitStr(regoNumber, " ", 9);
            String fName = helper.limitStr(info[2], " ", 12);
            String lName = helper.limitStr(info[3], " ", 12);
            String type = helper.limitStr(info[4], " ", 10);
            String start = helper.limitStr(info[5], " ", 31); 
            String duration = helper.limitStr(info[6], " ", 18);
            // print result
            System.out.println("||" + slotNumber + "|" + regoNumber + "|" + fName + "|" + lName + "|" + type + "|" + start + "|" + duration + "||");
        }
        // print final line
        helper.printLine("=", 111, true);
    }

    // -------------------------- Option 4 --------------------------
    // Park a car
    // Ask user for the following info:
    // - Registration number
    // - Owner's first and last name
    // - Whether owner is staff
    // - Which slot to park
    // --- If not specified, park in any available slot
    // ------- if no available slot, ask user to create one -> us addParkingSlot()
    // --- If specified, park in the specified slot
    public void parkCar(CarPark carPark) {
        String regoNumber;
        String fName;
        String lName;
        boolean isStaff;
        // prompt user for information & evaluate information
        // registration number:
        System.out.print("Enter the registration number of the car: ");
        regoNumber = takeRegoNumber(carPark);
        // owner name
        System.out.print("Enter the owner's first name: ");
        fName = sc.next();
        System.out.print("Enter the owner's last name: ");
        lName = sc.next();
        // is owner a staff
        isStaff = takeYNResponse(yes, no, "Is the owner a staff (y/n)? ");
        // which car park?

    }


    // method to evaluate y/n result
    public boolean takeYNResponse(ArrayList<String> yes, ArrayList<String> no, String question) {
        // evaluate if input is yes
        String response;
        boolean invalidResponse = true;
        
        do {
            System.out.println(question);
            response = sc.next();
            for (int i = 0; i < yes.size(); i ++) {
                if (yes.get(i).equalsIgnoreCase(response)) {
                    invalidResponse = false;
                    return true;
                }
            }
            // evaluate if input is no
            for (int i = 0; i < no.size(); i ++) {
                if (no.get(i).equalsIgnoreCase(response)) {
                    invalidResponse = false;
                    return false;
                }
            }
            // if input is invalid
            System.out.println ("Enter \"y\" or \"n\" only.");
        } while (invalidResponse);
        System.out.print("ERROR: out of takeYNResponse loop");
        return false;
    }

    

    // ------------------- checkSlotNumber() ------------------------
    // method to check the validity of parking slot number
    // whether it is of the right format, eg. A10, B02
    // AND whether it has already been added to the car park
    // return "available" if the slot number is of correct format and hasn't been added
    // return "unavailable" if the slot number is of correct format but has already been added
    // return "incorrect" if the slot number provided is of the wrong format
    public String takeSlotNumber(CarPark carPark) {
        String slotNumber;
        // regex of slot number format
        final String slotNumberRegex = "^[A-Z]{1}[0-9]{2}$";
        // compile the regex string
        Pattern pattern = Pattern.compile(slotNumberRegex);

        do {
            System.out.print("Enter the parking slot number: ");
            slotNumber = sc.next();
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
        }
        
    } // close checkSlotNumber() method


    // ------------------- checkRegoNumber() ------------------------
    // method to check the validity of rego number
    // whether it is of the right format, eg. T2345
    // AND whether it has already been added to the car park
    // return "available" if the rego is of correct format and hasn't been added
    // return "unavailable" if the rego is of correct format but has already been added
    // return "incorrect" if the rego provided is of the wrong format
    public String takeRegoNumber(CarPark carPark) {
        String regoNumber;
        boolean invalidInput = true;

        // declare the regex format for rego number
        final String regoNumberRegex = "^[A-Z]{1}[1-9]{4}$";
        // compile and match the patter to regoNumber
        Pattern pattern = Pattern.compile(regoNumberRegex);
        
        do {
            System.out.print("Enter the car's registration number: ");
            regoNumber = sc.next();
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
                        String currentSlotNumber = carPark.getParkingSlot(i).getSlotNumber();
                        System.out.println(regoNumber + " is currently parked in " + currentSlotNumber);
                        continue;
                    }
                } // close for()
                invalidInput = false;
                return regoNumber;
            } else {
                System.out.println("Registration number should contain an uppercase letter followed by two digits.");
            } // close if (matcher.find())
        } while (invalidInput);
        return regoNumber;
    } // close checkRegoNumber() method
}
