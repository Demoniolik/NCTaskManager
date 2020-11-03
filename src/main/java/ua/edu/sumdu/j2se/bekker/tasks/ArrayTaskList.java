package ua.edu.sumdu.j2se.bekker.tasks;

import java.util.Iterator;

/**
 * This class is responsible for storing objects of Task class
 * It represented as an Array list so in the base it has an array - taskList
 * As well all the methods for adding, removing, getting tasks are provided
 *
 * @author Dmitry Bekker
 */

public class ArrayTaskList extends AbstractTaskList implements Iterable<Task>{
    private Task[] taskList;
    private int capacity;
    private int size;

    /**
     * Default constructor that allocates the array with default value
     */
    public ArrayTaskList() {
        final int DEFAULT_CAPACITY = 16;
        this.capacity = DEFAULT_CAPACITY;
        this.taskList = new Task[this.capacity];
    }

    /**
     * Custom constructor that allocates space for the array given by user
     *
     * @param capacity amount of elements in the array to be it's capacity
     */
    public ArrayTaskList(int capacity) {
        if (capacity > 0) {
            this.taskList = new Task[capacity];
        }
        else throw new IllegalArgumentException("Given capacity cannot be equal or less than zero");
    }

    /**
     * This method adds elements of type Task to the array - taskList
     * In case when the capacity is not enough for storing future elements
     * Method calls private grow method to allocate additional space
     *
     * @param task given element by user to be added to the list
     */
    public void add(Task task) {
        if (capacity * 0.75 < size) {
            grow();
        }
        this.taskList[this.size++] = task;
    }

    /**
     * This method allocates more space for the array to store elements
     * It is called only if the capacity level is not enough for the future adding
     */
    private void grow() {
        final double GROWING_COEFFICIENT = 1.5;
        this.capacity *= GROWING_COEFFICIENT; // Here grow our capacity to make bigger array
        this.taskList = copy(); // Here we copy old values from initial array to the new one and assign it to the same reference
    }

    /**
     * This method copies elements from the initial array to the new one
     * due to creating a bigger or a smaller array (in cases of adding and deleting of elements)
     *
     * @return Task array new array with copied previous values from initial array
     */
    private Task[] copy() {
        Task[] newTaskList = new Task[this.capacity];
        if (this.size() >= 0) System.arraycopy(taskList, 0, newTaskList, 0, this.size());
        return newTaskList;
    }

    /**
     * This method removes the Task from the array
     * It has two additional methods called inside it
     * One is to move all the elements after deleted one to the left
     * so it won`t break the iteration and makes the right storing of the elements
     * Another one is to shrinking array in case it is more than 60% of unused space
     *
     * @param task given task to be deleted
     * @return true if the task is found and deleted, false if not found
     */
    public boolean remove(Task task) {
        for (int i = 0; i < this.size(); ++i) {
            if (taskList[i].equals(task)) {
                moveToTheLeft(i);
                this.size--;
                if (this.size() <= capacity * 0.4) {
                    shrink();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This method shrinks the capacity of the array in case it has
     * a lot of unused capacity
     * It shrinks capacity in 40%
     * Also it calls copy method to copy elements from initial array to the shrunk one
     */
    private void shrink() {
        final double SHRINKING_COEFFICIENT = 0.6;
        this.capacity *= SHRINKING_COEFFICIENT;
        this.taskList = copy();
    }
    /**
     * This method moves elements to the left, so the deleted one will be rewritten by next values
     * in the array
     *
     * @param index of the value that should be rewritten
     */
    private void moveToTheLeft(int index) {
        if (this.size() - 1 - index >= 0)
            System.arraycopy(this.taskList, index + 1, this.taskList, index, this.size() - 1 - index);
    }

    /**
     * This method returns the current size of the array
     *
     * @return array size
     */
    public int size() {
        return this.size;
    }

    /**
     * This method returns the task by its index
     * which represents elements position in the array
     *
     * @param index of the element that needed to be get
     * @return Task object that is found in the array by the given index
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (this.size() <= index || index < 0) {
            throw new IndexOutOfBoundsException("Given index is out of array bound");
        }
        return this.taskList[index];
    }

    /**
     * This method create object of Iterator that gives an opportunity to go through the
     * array list using foreach or streams
     *
     * @return iterator that could be used as a way to go through the elements of the array
     */
    @Override
    public Iterator<Task> iterator() {
        Iterator<Task> iterator = new Iterator<Task>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < size() && taskList[currentIndex] != null;
            }
            @Override
            public Task next() {
                return taskList[currentIndex++];
            }
        };
        return iterator;
    }
}
