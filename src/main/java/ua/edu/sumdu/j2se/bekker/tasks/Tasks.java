package ua.edu.sumdu.j2se.bekker.tasks;

import java.time.LocalDateTime;
import java.util.*;


public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) throws ClassNotFoundException {
        AbstractTaskList result = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        for (Task task : tasks) {
            if (task.nextTimeAfter(start) != null
                    && task.nextTimeAfter(start).compareTo(end) <= 0) {
                result.add(task);
            }
        }
        return (Iterable<Task>) result;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) throws ClassNotFoundException {
        SortedMap<LocalDateTime, Set<Task>> calendarMap = new TreeMap<>();
        Iterable<Task> iterableTasks = incoming(tasks, start, end);
        for (Task task : iterableTasks) {
            LocalDateTime nextTime = task.nextTimeAfter(start);
            while (nextTime != null
                    && (nextTime.isBefore(end) || nextTime.isEqual(end))) {
                if (!calendarMap.containsKey(nextTime)) {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    calendarMap.put(nextTime, taskSet);
                } else {
                    calendarMap.get(nextTime).add(task);
                }
                nextTime = task.nextTimeAfter(nextTime);
            }
        }
        return calendarMap;
    }
}
