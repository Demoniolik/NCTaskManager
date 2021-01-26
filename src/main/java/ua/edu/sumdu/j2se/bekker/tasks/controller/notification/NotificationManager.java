package ua.edu.sumdu.j2se.bekker.tasks.controller.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;
import ua.edu.sumdu.j2se.bekker.tasks.model.Tasks;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Provides a notification manager that after a certain time period
 * notifies the user about upcoming active tasks.
 */
public class NotificationManager extends Thread {
    private static final Logger logger = Logger.getLogger(NotificationManager.class);
    private final static long TIMER = 300000; // 5 MIN
    private final AbstractTaskList list;
    private final Notification notification;

    /**
     * Constructor.
     *
     * @param list task list.
     */
    public NotificationManager(AbstractTaskList list) {
        this.list = list;
        notification = ConsoleNotification.getInstance();
    }

    /**
     * Sends a notification to the client.
     */
    @Override
    public void run() {
        SortedMap<LocalDateTime, Set<Task>> map = null;
        while (true) {
            try {
                map = Tasks.calendar(list, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (map.isEmpty()) {
                logger.info("Map is empty! Calendar has no tasks for the next period.");
            } else {
                notification.notify(map);
            }
            try {
                Thread.sleep(TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("Interrupted exception.", e);
            }
        }
    }
}