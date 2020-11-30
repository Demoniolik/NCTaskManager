package ua.edu.sumdu.j2se.bekker.tasks;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This class unites all the needed functionality
 * that are needed to store data of List types
 * like ArrayTaskList ot LinkedTaskList
 * it has four abstract methods that do their work in derived classes
 * here is only their declaration
 *
 * @author Dmitry Bekker
 */
abstract public class AbstractTaskList {

    abstract public void add(Task task);
    abstract public Task getTask(int index);
    abstract public int size();
    abstract public boolean remove(Task task);

    /**
     * This method return subclass of the ArrayTaskList
     * that contains of the tasks that are scheduled in the range
     * given by the user
     *
     * @param from is the start point of gathering of tasks
     * @param to is the end point of gathering of tasks
     * @return subclass of type ArrayTaskList that contains of selected elements
     */
    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList subTaskList;
        if (this.getClass().getSimpleName().equals("ArrayTaskList")) {
            subTaskList = new ArrayTaskList();
        }else {
            subTaskList = new LinkedTaskList();
        }
        getStream().filter(task -> task.nextTimeAfter(from) != -1
                && task.nextTimeAfter(to) == -1).forEach(subTaskList::add);
        return subTaskList;
    }

    /**
     *  This method writes all the elements to the stream
     * @return stream that contains all the elements of taskList
     */
    public Stream<Task> getStream() {
        Task[] tasks = new Task[this.size()];

        for(int i = 0; i < size(); ++i) {
            tasks[i] = getTask(i);
        }
        return Arrays.stream(tasks);
    }
}
