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
        // // 4test helper.millisecToTime() => Done
        // Helper helper = new Helper();
        // System.out.println(helper.millisecToTime(864533555));

        // // test helper.checkRegoNumber() => Done
        // Car car = new Car("T2345", "John", "Doe", true);
        // ParkingSlot parkingSlot = new ParkingSlot();
        // parkingSlot.setCar(car);
        // CarPark carPark = new CarPark();
        // carPark.addParkingSlot(parkingSlot);
        // System.out.println(helper.checkRegoNumber("T2345", carPark));
        // System.out.println(helper.checkRegoNumber("T1345", carPark));
        // System.out.println(helper.checkRegoNumber("T23452", carPark));
        // System.out.println(helper.checkRegoNumber("t2345", carPark));
        // System.out.println(helper.checkRegoNumber("T2a345", carPark));




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