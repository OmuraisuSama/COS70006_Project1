import java.util.Scanner;

public class Application {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
    //     boolean continueProgram = true;
    //     int userChoice;
    //     while (continueProgram) {
    //         userChoice = displayMenu();
    //         switch (userChoice) {
    //             case 1:
    //                 System.out.println("You want to add a new parking slot");
    //                 break;
    //             case 2:
    //                 System.out.println("You want to delete a parking slot");
    //                 break;
    //             case 3:
    //                 System.out.println("You want to list all parking slots.");
    //                 break;
    //             case 4:
    //                 System.out.println("You want to park a car.");
    //                 break;
    //             case 5:
    //                 System.out.println("You want to search for a car.");
    //                 break;
    //             case 6:
    //                 System.out.println("You want to remove a car");
    //                 break;
    //             case 7:
    //                 continueProgram = false;
    //                 System.out.println("See ya!");
    //                 break;
    //             default:
    //                 System.out.println("Please enter a number 1 - 7");
    //                 break;
    //         } // close switch()
    //     } // close while()


    // ==================== TESTERS ============================
        // variables needed for testing
        String slotNumber = "A10";
        String regoNumber = "T2345";
        Helper helper = new Helper();
        Car car = new Car(regoNumber, "John", "Doe", true);
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotNumber(slotNumber);
        parkingSlot.setCar(car);
        ParkingSlot parkingSlot2 = new ParkingSlot("B52", true);
        CarPark carPark = new CarPark();
        carPark.addParkingSlot(parkingSlot);
        carPark.addParkingSlot(parkingSlot2);

        
        // // test helper.millisecToTime() => Success
        // System.out.println(helper.millisecToTime(864533555));

        // // test helper.checkRegoNumber() => Success
        // System.out.println("T2345: " + helper.checkRegoNumber("T2345", carPark));
        // System.out.println("T1345: " + helper.checkRegoNumber("T1345", carPark));
        // System.out.println("T23452: " + helper.checkRegoNumber("T23452", carPark));
        // System.out.println("t2345: " + helper.checkRegoNumber("t2345", carPark));
        // System.out.println("T2a345: " + helper.checkRegoNumber("T2a345", carPark));

        // // test helper.checkSlotNumber() => Success
        // System.out.println("A10: " + helper.checkSlotNumber("A10", carPark));
        // System.out.println("B11: " + helper.checkSlotNumber("B11", carPark));
        // System.out.println("A11: " + helper.checkSlotNumber("A11", carPark));
        // System.out.println("a10: " + helper.checkSlotNumber("a10", carPark));
        // System.out.println("10A: " + helper.checkSlotNumber("10A", carPark));
        // System.out.println("1A0: " + helper.checkSlotNumber("1A0", carPark));
        // System.out.println("AA1: " + helper.checkSlotNumber("AA1", carPark));
        // System.out.println("A110: " + helper.checkSlotNumber("A110", carPark));

        // // test carPark.deleteSlot() => Sucess
        // System.out.println("Before delete");
        // int slotIndex = carPark.getIndexOf(slotNumber);
        // System.out.println(carPark.getParkingSlot(slotIndex));
        // System.out.println("After delete");
        // // delete the slot number
        // carPark.deleteSlot(slotNumber);
        // slotIndex = carPark.getIndexOf(slotNumber);
        // // check if slotNumber can be found
        // if (slotIndex != -1) {
        //     System.out.println(carPark.getParkingSlot(slotIndex));
        // } else {
        //     System.out.println(slotNumber + " has been deleted.");
        // }
        // // check if method will print (slotNumber + " does not exist")
        // carPark.deleteSlot(slotNumber);


        // test CarPark.getSlotInfo();
        for (int i = 0; i < carPark.getSize(); i ++) {
            for (int j = 0; j < 4; j ++) {
                String cr = carPark.getSlotInfo(i)[j];
                if (!cr.equals("")) {
                    System.out.println(cr);
                }
            }
            System.out.println();
        }
            

    // ==========================================================
    } // close main()
    
    public static int displayMenu() {
        int userChoice;

        System.out.println("1. Add a parking slot.");
        System.out.println("2. Remove a parking slot");
        System.out.println("3. List all the parking slots");
        System.out.println("4. Park a car");
        System.out.println("5. Search for a car");
        System.out.println("6. Remove a car");
        System.out.println("7. Quit");
        System.out.println("Your choice: ");
        
        userChoice = sc.nextInt();
        return userChoice;
    }
    // sc.close();
}