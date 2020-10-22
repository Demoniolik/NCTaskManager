package ua.edu.sumdu.j2se.bekker.tasks;

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
     * @param title - is the name of the task, that we want to schedule
     * @param time - specified time of the task to occur
     */

    public Task(String title, int time) {
        this.title = title;
        this.time = time;
    }

    /**
     * This is the constructor which creates repeated task
     *
     * @param title - is the name of the task, that we want to schedule
     * @param start - start time of the task
     * @param end - the deadline of the task
     * @param interval - amount of times the task will be repeated during the time form start point to the end
     */

    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
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
     */

    public void setTime(int time) {
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
     */

    public void setTime(int start, int end, int interval) {
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
}
