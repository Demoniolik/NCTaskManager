package ua.edu.sumdu.j2se.bekker.tasks.view;

import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;

import java.time.LocalDateTime;

public interface CalendarView {

    /**
     * Displays date request for a calendar. Then shows the calendar
     * to selected period.
     *
     * @param list task list.
     */
    void getCalendar(AbstractTaskList list) throws ClassNotFoundException;

    /**
     * Used to get date time from representation.
     *
     * @return date time.
     */
    LocalDateTime parseDateTime();
}
