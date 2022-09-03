
import java.util.Scanner;
// import java.util.ArrayList;

public class Helper {
    private Scanner sc = new Scanner(System.in);

    // ------------------- millisecToTime() ------------------------
    // method to convert the number of milliseconds into
    // xxD:xxH:xxM:xxS
    public String millisecToTime(long duration) {
        // result to be returned
        String result = "";
        // array to store result {days, hours, minutes, seconds}
        int [] intArrResult = {0, 0, 0, 0};
        // array to store name of units
        final String [] unitArr = {"d:", "h:", "m:", "s"};
        // declare converstion rates from millisecond
        final long msToDays = 86400000;
        final long msToHours = 3600000;
        final long msToMinutes = 60000;
        final long msToSeconds = 1000;

        // convert to datetime
        // find the number of days
        intArrResult[0] = (int)(duration / msToDays);
        // remainder of milliseconds after calculating days
        long remainder = duration % msToDays;
        // find the number of hours
        intArrResult[1] = (int)(remainder / msToHours);
        // remainder after calculating hours
        remainder %= msToHours;
        // find the number of minutes
        intArrResult[2] = (int)(remainder / msToMinutes);
        // remainder after calculating minutes
        remainder %= msToMinutes;
        // find the number of minutes
        intArrResult[3] = (int)(remainder / msToSeconds);

        // convert number of milliseconds to string (eg, 60,000 ms to "01m.00s")
        // by combining the value of intArrResult with unitArr
        // nonZeroIndex is to find the first non zero time unit
        // for example: 2H:23M:00S instead of 00D:2H:23M:00S (we don't want to have 0 days)
        // however we want to show the minute event if it's zero (eg, 00m.23s)
        // the minute value is the third value in intArrResult -> index = 2
        int nonZeroIndex = 2; // default to minute value in intArrResult
        // find the first non zero time unit
        for (int i = 0; i < intArrResult.length; i ++) {
            if (intArrResult[i] > 0) {
                nonZeroIndex = i;
                break;
            }
            
        } // close for()
        // format the final result
        for (int i = nonZeroIndex; i < intArrResult.length; i ++) {
            // add 0 to number less than 10
            if (intArrResult[i] < 10 && i != 0) {
                result += "0";
            }
            result += intArrResult[i] + unitArr[i];
        } // close for()

        return result;
    } // close millisecToTime() method

    // method to cut down string to a fixed number of characters
    // e.g. String "This is the string"
    // if too long, cut down to limit + add "...": "This is a ..."
    // if too short, add padding: "     This is a String     "
    public String limitStr(String input, String padding, int strLength) {
        int len = input.length();
        // result to be returned
        String result = "";
        // padding left and right
        String rightPaddingStr = "";
        String leftPaddingStr = "";
        // length of padding
        int rightPaddingLen;
        int leftPaddingLen;

        // if input is longer than limit
        if (len > strLength) {
            for (int i = 0; i < strLength - 3; i ++) {
                result += input.charAt(i);
            }
            result += "...";
        } else {
            // if input is shorter than limit
            leftPaddingLen = (int)((strLength - len) / 2);
            rightPaddingLen = strLength - leftPaddingLen - len;
            // add left padding
            for (int i = 0; i < leftPaddingLen; i ++) leftPaddingStr += padding;
            // add right padding
            for (int i = 0; i < rightPaddingLen; i ++) rightPaddingStr += padding;
            result = leftPaddingStr + input + rightPaddingStr;
        }
        return result;
    }

    // use to print lines
    public void printLine(String line, int times, boolean newline) {
        String result = "";
        for (int i = 0; i < times; i ++) {
            result += line;
        }
        if (newline) {
            System.out.println(result);
        } else {
            System.out.print(result);
        }
    }

    // use to print different header
    public void printHeader(String type) {
        String headings = "";
        switch (type) {
            case "main menu":
                // to be completed;
                break;
            case "list slots":
                // print first line
                printLine("=", 111, true);
                // form & print headings
                headings = "||" + limitStr("Slot ID", " ", 9);
                headings += "|" + limitStr("Parking", " ", 9);
                headings += "|" + limitStr("First name", " ", 12);
                headings += "|" + limitStr("Last name", " ", 12);
                headings += "|" + limitStr("Type", " ", 10);
                headings += "|" + limitStr("Start", " ", 31);
                headings += "|" + limitStr("Duration", " ", 18) + "||";
                System.out.println(headings);
                // print line below heading
                printLine("-", 111, true);
                break;
            default:
                break;
        }
    }

    
    // display main menu
    public int displayMenu() {
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
}
