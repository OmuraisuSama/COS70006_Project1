import java.util.regex.Pattern;
import java.util.regex.Matcher;
// import java.util.ArrayList;

public class Helper {

    // ------------------- millisecToTime() ------------------------
    // method to convert the number of milliseconds into
    // xxD:xxH:xxM:xxS
    public String millisecToTime(long duration) {
        // result to be returned
        String result = "";
        // array to store result {days, hours, minutes, seconds}
        int [] intArrResult = {0, 0, 0, 0};
        // array to store name of units
        final String [] unitArr = {"d : ", "h : ", "m : ", "s"};
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

        // convert datetimeResult to string
        // start from the first non zero unit
        // 2H:23M:00S instead of 00D:2H:23M:00S
        int nonZeroIndex = 0;
        // find the first non zero time unit
        for (int i = 0; i < intArrResult.length; i ++) {
            if (intArrResult[i] != 0) {
                nonZeroIndex = i;
                break;
            }
        } // close for()
        // format the final result
        for (int i = nonZeroIndex; i < intArrResult.length; i ++) {
            // add 0 to number less than 10
            if (intArrResult[i] < 10) {
                result += "0";
            }
            result += intArrResult[i] + unitArr[i];
        } // close for()

        // add final period
        result += ".";
        return result;
    } // close millisecToTime() method


    // ------------------- checkRegoNumber() ------------------------
    // method to check the validity of rego number
    // whether it is of the right format, eg. T2345
    // AND whether it has already been added to the car park
    // return "available" if the rego is of correct format and hasn't been added
    // return "unavailable" if the rego is of correct format but has already been added
    // return "incorrect" if the rego provided is of the wrong format
    public String checkRegoNumber(String regoNumber, CarPark carPark) {
        // declare the regex format for rego number
        final String regoNumberRegex = "^[A-Z]{1}[1-9]{4}";
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
