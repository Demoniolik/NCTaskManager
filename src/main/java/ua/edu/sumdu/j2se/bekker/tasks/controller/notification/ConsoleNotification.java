package ua.edu.sumdu.j2se.bekker.tasks.controller.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;
import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

class ConsoleNotification implements Notification {
    private static final Logger logger = Logger.getLogger(ConsoleNotification.class);
    private static final ConsoleNotification CONSOLE_NOTIFICATION = new ConsoleNotification();

    private ConsoleNotification() {}

    /**
     * Singleton.
     *
     * @return MailNotification instance.
     */
    public static ConsoleNotification getInstance() {
        return CONSOLE_NOTIFICATION;
    }

    /**
     * Sends email message to the user.
     *
     * @param calendar map contains an incoming tasks in selected period.
     */
    @Override
    public void notify(SortedMap<LocalDateTime, Set<Task>> calendar) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ConsolePrinting.println(String.format("%3s", "\n"));
        ConsolePrinting.println("Task Manager. You have upcoming tasks!");
        ConsolePrinting.println("Tasks for the next hour:");
        ConsolePrinting.println(String.format("Date:%14s Task:", " "));
        for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
            ConsolePrinting.print(entry.getKey().format(formatter) + "\t");
            for (Task task : entry.getValue()) {
                ConsolePrinting.println(task.toString());
            }
        }
        logger.info("Tasks for the next hour is shown in console");
    }
}