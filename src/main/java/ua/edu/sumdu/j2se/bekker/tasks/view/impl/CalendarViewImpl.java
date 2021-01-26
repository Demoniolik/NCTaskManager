package ua.edu.sumdu.j2se.bekker.tasks.view.impl;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;
import ua.edu.sumdu.j2se.bekker.tasks.model.Tasks;
import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;
import ua.edu.sumdu.j2se.bekker.tasks.view.CalendarView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;

public class CalendarViewImpl implements CalendarView {
    private static final Logger logger = Logger.getLogger(CalendarViewImpl.class);
    private final Scanner scanner;

    public CalendarViewImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints date request for a calendar. Then shows the calendar
     * into CLI to selected period.
     *
     * @param list task list.
     */
    @Override
    public void getCalendar(AbstractTaskList list) throws ClassNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        while (true) {
            ConsolePrinting.print("Enter start date of the period: ");
            LocalDateTime start = parseDateTime();
            ConsolePrinting.print("Enter end date of the period: ");
            LocalDateTime end = parseDateTime();

            if (!start.isAfter(end)) {
                SortedMap<LocalDateTime, Set<Task>> calendar;
                calendar = Tasks.calendar(list, start, end);
                ConsolePrinting.println("*** UPCOMING CALENDAR ***");
                if (calendar.isEmpty()) {
                    ConsolePrinting.println("There are no upcoming tasks");
                } else {
                    ConsolePrinting.println("Tasks for the selected period: ");
                    for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
                        String date = entry.getKey().format(formatter);
                        ConsolePrinting.println("Date: " + date);
                        for (Task task : entry.getValue()) {
                            ConsolePrinting.println("\tTask: " + task.getTitle());
                        }
                    }
                }
                break;
            } else {
                ConsolePrinting.print("Wrong parameters! Enter the correct time period for the calendar.");
            }
        }
    }

    /**
     * Used to parse date time from CLI.
     *
     * @return date time.
     */
    public LocalDateTime parseDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String date = "";
        LocalDateTime time;
        ConsolePrinting.print("Enter the date of the following format: dd-MM-yyyy HH:mm: ");
        while (true) {
            try {
                date = scanner.nextLine();
                time = LocalDateTime.parse(date, formatter);
                break;
            } catch (DateTimeParseException e) {
                logger.error("Incorrect date format");
            }
            ConsolePrinting.print("Incorrect! Please enter the date of the following time: dd-MM-yyyy HH:mm: ");
        }
        return time;
    }
}
