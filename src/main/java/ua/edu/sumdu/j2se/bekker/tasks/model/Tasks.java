package ua.edu.sumdu.j2se.bekker.tasks.model;

import java.time.LocalDateTime;
import java.util.*;


public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end)
            throws ClassNotFoundException {
        AbstractTaskList result = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        for (Task task : tasks) {
            if (task.nextTimeAfter(start) != null
                    && task.nextTimeAfter(start).compareTo(end) <= 0) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     *
     * @param tasks - list of tasks, after the method is finished
     *              there will be formed a calendar of dates and tasks in given timespan
     * @param start - start time of repetitive the task in interval
     * @param end - end time of the repetitive task in interval
     * @return a collection that contains dates of occurring tasks and tasks,
     * also it is sorted by time in ascending order
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end)
            throws ClassNotFoundException {
        SortedMap<LocalDateTime, Set<Task>> calendarMap = new TreeMap<>(LocalDateTime::compareTo);
        Iterable<Task> iterableTasks = incoming(tasks, start, end);
        for (Task task : iterableTasks) {
            LocalDateTime nextTime = task.nextTimeAfter(start);
            while (nextTime != null
                    && (nextTime.isBefore(end) || nextTime.isEqual(end))) {
                if (!calendarMap.containsKey(nextTime)) { // if calendar does not contain this time
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    calendarMap.put(nextTime, taskSet); // putting the value to the calendar by new date time key
                } else {
                    calendarMap.get(nextTime).add(task); // putting the value task to old time key
                }
                nextTime = task.nextTimeAfter(nextTime);
            }
        }
        return calendarMap;
    }
}
