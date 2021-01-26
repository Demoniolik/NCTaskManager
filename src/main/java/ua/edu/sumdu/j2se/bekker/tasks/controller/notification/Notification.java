package ua.edu.sumdu.j2se.bekker.tasks.controller.notification;

import ua.edu.sumdu.j2se.bekker.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public interface Notification {
    /**
     * Represents notification sending to the task manager user.
     *
     * @param calendar map contains tasks in selected time period.
     */
    void notify(SortedMap<LocalDateTime, Set<Task>> calendar);
}
