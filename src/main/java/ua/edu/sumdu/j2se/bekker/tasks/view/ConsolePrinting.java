package ua.edu.sumdu.j2se.bekker.tasks.view;

public class ConsolePrinting {
    /**
     * Prints message into command line.
     *
     * @param prompt a message to be displayed to the user.
     */
    public static void print(String prompt) {
        System.out.print(prompt);
    }

    /**
     * Prints message into command line and then terminate the line.
     *
     * @param prompt a message to be displayed to the user.
     */
    public static void println(String prompt) {
        System.out.println(prompt);
    }
}
