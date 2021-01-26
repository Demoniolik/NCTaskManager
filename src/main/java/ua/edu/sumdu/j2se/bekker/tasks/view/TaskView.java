package ua.edu.sumdu.j2se.bekker.tasks.view;

import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;

public interface TaskView {
    /**
     * Displays task list.
     *
     * @param model task list.
     */
    void showTaskList(AbstractTaskList model);

    /**
     * Displays message to select task and return a number of
     * selected task to a controller.
     *
     * @param model task list.
     * @return index of element in task list.
     */
    int selectTask(AbstractTaskList model);

    /**
     * Displays message to remove task and requests a number of
     * task to delete. Returns number of element to a controller.
     *
     * @param model task list.
     * @return index of element in task list.
     */
    int removeTask(AbstractTaskList model);

    /**
     * Used to get title from representation.
     *
     * @return string title into a controller.
     */
    String getTitle();

    /**
     * Used to get activity status from representation.
     *
     * @return boolean. True if task is active.
     */
    boolean getActiveStatus();

    /**
     * Used to get task repetition status from representation.
     *
     * @return an integer value into a controller.
     */
    int getIsTaskRepeated();

    /**
     * Used to get time interval in minutes from representation.
     *
     * @return an integer value in minutes into a controller.
     */
    int getInterval();

    /**
     * Displays an information about task to representation.
     *
     * @param task task object.
     */
    void displayTaskInfo(Task task);

    /**
     * Checks user confirmation from representation.
     *
     * @return true if user confirmed request.
     */
    boolean checkUserAnswer();
}
