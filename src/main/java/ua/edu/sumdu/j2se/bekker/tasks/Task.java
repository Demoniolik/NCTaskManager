package ua.edu.sumdu.j2se.bekker.tasks;


public class Task {

    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;

    public Task(String title, int time) {
        this.title = title;
        this.time = time;
    }

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
