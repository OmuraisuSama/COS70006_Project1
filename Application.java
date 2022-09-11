import java.util.Scanner;

/*
 * Application class
 * @description: contain the main() method
 * 
 * @author: Peter LUONG - 1038 11153
 * @version: JRE-17
 * @date: 11 Sep 2022
 * @unit: COS70006 - Object-Oriented Programming
 */

public class Application {
    // private static Scanner sc = new Scanner(System.in);
    private Helper helper = new Helper();
    private Logics logics = new Logics();
    private CarPark carPark = new CarPark();
    // private int[] menuChoices = {1, 2, 3, 4, 5, 6, 7};
    private Scanner sc = new Scanner(System.in);

    // method to start the application
    private void applicationStarts() {
        boolean continueProgram = true;
        String strUserChoice;
        int intUserChoice = 0;
        String errorMessage = "Please enter a number from 1 to 7.";
        while (continueProgram) {
            // print menu options
            helper.appPrint("main menu");
            // take and check user input
            System.out.print("|| Your choice: ");
            strUserChoice = sc.next();
            try {
                intUserChoice = Integer.parseInt(strUserChoice);
            } catch (Exception e) {
                helper.printError(errorMessage);
                continue;
            }

            switch (intUserChoice) {
                case 1:
                    System.out.println("|| You want to add a new parking slot");
                    logics.addParkingSlot(carPark);
                    break;
                case 2:
                    System.out.println("|| You want to delete a parking slot");
                    logics.deleteParkingSlot(carPark);
                    break;
                case 3:
                    System.out.println("|| You want to list all parking slots.");
                    System.out.println();
                    logics.listSlots(carPark);
                    break;
                case 4:
                    System.out.println("|| You want to park a car.");
                    logics.parkCar(carPark);
                    break;
                case 5:
                    System.out.println("|| You want to search for a car.");
                    logics.searchCar(carPark);
                    break;
                case 6:
                    System.out.println("|| You want to remove a car");
                    logics.removeCar(carPark);
                    break;
                case 7:
                    continueProgram = false;
                    System.out.println("|| Thank you for using the application!");
                    break;
                default:
                    break;
            } // close switch()
        } // close while()
    }


    // main()
    public static void main(String[] args) {
        Application parkingApp = new Application();
        parkingApp.applicationStarts();
    }
}