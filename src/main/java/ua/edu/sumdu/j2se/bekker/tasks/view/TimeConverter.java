package ua.edu.sumdu.j2se.bekker.tasks.view;

public class TimeConverter {
    private final static int AMOUNT_OF_SECONDS_IN_ONE_HOUR = 3600;
    private final static int AMOUNT_OF_SECONDS_IN_ONE_DAY = AMOUNT_OF_SECONDS_IN_ONE_HOUR * 24;
    private final static int AMOUNT_OF_SECOND_IN_ONE_MINUTE = 60;

    /**
     * Returns time interval (days, hours, minutes and seconds) as a string.
     * If one of the values 0 it is not displayed in the console.
     *
     * @param amountOfMinutes an integer value in seconds.
     * @return string value.
     */
    public static String convertSecondsToDayHourMinuteSecondFormat(int amountOfMinutes) {
        StringBuilder resultTime = new StringBuilder();

        int days = amountOfMinutes / AMOUNT_OF_SECONDS_IN_ONE_DAY;
        if (days > 1) resultTime.append(days).append(" ").append("days ");
        else if (days > 0) resultTime.append(days).append(" ").append("day ");

        amountOfMinutes = amountOfMinutes % AMOUNT_OF_SECONDS_IN_ONE_DAY;
        int hours = amountOfMinutes / AMOUNT_OF_SECONDS_IN_ONE_HOUR;
        if (hours > 1) resultTime.append(hours).append(" ").append("hours ");
        else if (hours > 0) resultTime.append(hours).append(" ").append("hour ");

        amountOfMinutes %= AMOUNT_OF_SECONDS_IN_ONE_HOUR;
        int minutes = amountOfMinutes / AMOUNT_OF_SECOND_IN_ONE_MINUTE;
        if (minutes > 1) resultTime.append(minutes).append(" ").append("minutes ");
        else if (minutes > 0) resultTime.append(minutes).append(" ").append("minute ");

        amountOfMinutes %= AMOUNT_OF_SECOND_IN_ONE_MINUTE;
        int seconds = amountOfMinutes;
        if (seconds > 1) resultTime.append(seconds).append(" ").append("seconds ");
        else if (seconds > 0) resultTime.append(seconds).append(" ").append("second ");

        return resultTime.toString();
    }
}
