/*
 * Helper class
 * @description: this class contains methods for printing and formatting output
 * 
 * - Public methods:
 * |- millisecToTime: convert the number of miliseconds (int type) into String time (--d:--h:--m:--s). This is used to format the parking duration
 * |- limitStr: to format text so that long text can fit in limited space in a table
 * |- printLine: to print lines with specified type and length. This is used in displaying table
 * |- appPrint: to print menu and headings
 * |- printError: to print error message
 * |- printQuestion: to print question prompting user for input
 * |- printConfirm: to print confirmation messages
 * 
 * @author: Peter LUONG - 1038 11153
 * @version: JRE-17
 * @date: 11 Sep 2022
 * @unit: COS70006 - Object-Oriented Programming
 */

public class Helper {
    private final int errorLineLen = 84;

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
    public String limitStr(String input, String padding, int strLimit) {
        int inputLength = input.length();
        // result to be returned
        String result = "";
        // padding left and right
        String rightPaddingStr = "";
        String leftPaddingStr = "";
        // length of padding
        int rightPaddingLen;
        int leftPaddingLen;

        // if input is longer than limit
        if (inputLength > strLimit) {
            for (int i = 0; i < strLimit - 3; i ++) {
                result += input.charAt(i);
            }
            result += "...";
        } else {
            // if input is shorter than limit
            leftPaddingLen = (int)((strLimit - inputLength) / 2);
            rightPaddingLen = strLimit - leftPaddingLen - inputLength;
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
    public void appPrint(String menuType) {
        String headings = "";
        switch (menuType) {

            case "main menu":
                printLine("=", 41, true);
                headings = "|| Option |          Function          ||";
                System.out.println(headings);
                printLine("-", 41, true);
                System.out.println("||   1    | Add a parking slot.        ||");
                System.out.println("||   2    | Remove a parking slot      ||");
                System.out.println("||   3    | List all the parking slots ||");
                System.out.println("||   4    | Park a car                 ||");
                System.out.println("||   5    | Search for a car           ||");
                System.out.println("||   6    | Remove a car               ||");
                System.out.println("||   7    | Quit                       ||");
                printLine("_", 41, true);
                break;
            
            case "list slots":
                // print first line
                printLine("=", 115, true);
                // form & print headings
                headings = "||" + limitStr("Slot ID", " ", 9);
                headings += "|" + limitStr("Parking", " ", 9);
                headings += "|" + limitStr("First name", " ", 14);
                headings += "|" + limitStr("Last name", " ", 14);
                headings += "|" + limitStr("Type", " ", 10);
                headings += "|" + limitStr("Start", " ", 31);
                headings += "|" + limitStr("Duration", " ", 18) + "||";
                System.out.println(headings);
                // print line below heading
                printLine("-", 115, true);
                break;

            case "search car":
                printLine("=", 65, true);
                headings = "||" + limitStr("Rego number", " ", 13);
                headings += "|" + limitStr("Parking slot", " ", 14);
                headings += "|" + limitStr("Owner", " ", 32);
                headings += "||";
                System.out.println(headings);
                printLine("-", 65, true);
                break;
            default:
                break;
        }
    }

    public void printError(String errorMessage) {
        printLine("*", errorLineLen, true);
        System.out.println("ERROR: " + errorMessage);
        printLine("*", errorLineLen, true);
        System.out.println();
    }

    public void printConfirm(String message) {
        System.out.println("--> " + message);
        System.out.println();
    }

    public void printQuestion(String question) {
        System.out.print("// " + question + " ");
    }

}
