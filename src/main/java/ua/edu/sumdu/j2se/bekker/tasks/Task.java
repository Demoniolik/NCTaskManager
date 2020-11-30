package ua.edu.sumdu.j2se.bekker.tasks;

import java.util.Objects;

/**
 * This is the class which hold all the information needed to handle the task in the program
 * This class coluld be described as one that hold all the needed information to schedule a task
 * The task colud be of two types -
 *      - one is that will not be repeated. It will be scheduled on one date and that's it
 *      - second one is that will be repeated from start point of time to the end point with some interval
 *
 * @author Bekker Dmitry
 * */
public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;

    /**
     * This is constructor which will creates non-repeated task
     *
     * @param title the name of the task, that we want to schedule
     * @param time specified time of the task to occur
     */
    public Task(String title, int time) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Title of the task cannot be null");
        }
        this.title = title;
        if(time < 0) {
            throw new IllegalArgumentException("Time cannot be less than zero");
        }
        this.time = time;
    }

    /**
     * This is the constructor which creates repeated task
     *
     * @param title is the name of the task, that we want to schedule
     * @param start start time of the task
     * @param end the deadline of the task
     * @param interval amount of times the task will be repeated during the time form start point to the end
     */
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Title of the task cannot be null");
        }
        this.title = title;
        if (start < 0) {
            throw new IllegalArgumentException("Start point cannot be less than zero");
        }
        this.start = start;
        if (end < 0) {
            throw new IllegalArgumentException("End point cannot be less than zero");
        }else if (start >= end) {
            throw new IllegalArgumentException("End point cannot be less or equal to start point");
        }
        this.end = end;
        if (interval < 0) {
            throw new IllegalArgumentException("Interval cannot be less than zero");
        }
        this.interval = interval;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Title of the task cannot be null");
        }
        this.title = title;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTime() {
        if (isRepeated()) {
            return this.start;
        }
        return this.time;
    }

    /**
     * This method sets time for the task
     * in case it was a repeatable task it becomes non-repeatable
     * and it sets time for non-repeatable task
     *
     * @param time given time to be set as a non-repeatable task
     */
    public void setTime(int time) throws IllegalArgumentException {
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be less than zero");
        }
        if (isRepeated()) {
            this.start = 0;
            this.end = 0;
            this.interval = 0;
        }
        this.time = time;
    }

    public int getStartTime() {
        if (isRepeated()) {
            return this.start;
        }
        return this.time;
    }

    public int getEndTime() {
        if (isRepeated()) {
            return this.end;
        }
        return this.time;
    }

    public int getRepeatInterval() {
        if (isRepeated()) {
            return this.interval;
        }
        return 0;
    }

    /**
     * This method sets time for the task
     * in case it was a non-repeatable task it becomes repeatable
     * and it sets time for repeatable task
     *
     * @param start point of the scheduled task
     * @param end point of the scheduled task
     * @param interval amount of repetitions in the range
     */
    public void setTime(int start, int end, int interval) throws IllegalArgumentException {
        if (start < 0) {
            throw new IllegalArgumentException("Start point cannot be less than zero");
        }
        if (end < 0) {
            throw new IllegalArgumentException("End point cannot be less than zero");
        }else if (start >= end) {
            throw new IllegalArgumentException("End point cannot be less or equal to start point");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Interval cannot be less than zero");
        }
        if (!isRepeated()) {
            this.time = 0;
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public boolean isRepeated() {
        return time == 0;
    }

    /**
     * This method looks for the task that will occur before the current (given) time
     * if task has already happened (given time is after the time of the the task to occur)
     *  it will return -1
     *  For non-repeatable tasks it checks if the given time is before the time of the task to take place
     *  For repeatable tasks it checks if the given time is before the start time plus it adds the interval in case
     *  if given time is in interval of the task. We also should return the start point including the interval it is taking place
     *
     * @param current - time that is given to check when the task is scheduled in case the given time is before the task time
     * @return the time of the task in case the given time is before the task time
     * */
    public int nextTimeAfter(int current) {
        if (isActive()) {
            if (!isRepeated()) {
                if (this.time > current) {
                    return this.time;
                }else return -1;
            }
            for (int startPoint = this.start; startPoint < this.end; startPoint += interval) {
                if (current < startPoint) {
                    return startPoint;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time &&
                start == task.start &&
                end == task.end &&
                interval == task.interval &&
                active == task.active &&
                title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }

    public Task clone() {
        Task new_task = new Task("", 0);
        new_task.title = new String(this.title);
        new_task.time = this.time;
        new_task.active = this.active;
        new_task.start = this.start;
        new_task.end = this.end;
        new_task.interval = this.interval;
        return new_task;
    }
}
