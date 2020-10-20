package ua.edu.sumdu.j2se.bekker.tasks;

import java.util.Iterator;

public class ArrayTaskList implements Iterable<Task>{

    // TODO: Create method to trim your array

    private Task[] taskList;
    private int capacity;
    private int size;
    private final int DEFAULT_CAPACITY = 16;

    public ArrayTaskList() {

        this.capacity = DEFAULT_CAPACITY;
        this.taskList = new Task[this.capacity];

    }

    public ArrayTaskList(int capacity) {

        if (capacity > 0) {
            this.taskList = new Task[capacity];
        }
        else throw new IllegalArgumentException("Given capacity cannot be equal or less than zero");

    }

    public void add(Task task) {

        if (capacity * 0.75 < size) {
            grow();
        }

        this.taskList[this.size++] = task;

    }

    private void grow() {

        this.capacity *= 1.5; // Here grow our capacity to make bigger array
        this.taskList = copy(); // Here we copy old values from initial array to the new one and assign it to the same refernce

    }

    // TODO: Implement the shrinking of the array in case that the 60% of capacity will not be used

    private void trim() {

    }

    private Task[] copy() {
        Task[] newTaskList = new Task[this.capacity];

        for (int i = 0; i < this.size(); ++i) {
            newTaskList[i] = taskList[i];
        }

        return newTaskList;

    }


    public boolean remove(Task task) {

        for (int i = 0; i < this.size(); ++i) {

            if (taskList[i].equals(task)) {
                moveToTheLeft(i);
                this.size--;
                return true;
            }

        }

        return false;

    }

    private void moveToTheLeft(int index) {

        for (int i = index; i < this.size() - 1; ++i) {
            this.taskList[i] = this.taskList[i+1];
        }

    }

    public int size() {
        return this.size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {

        if (this.size() <= index || index < 0) {
            throw new IndexOutOfBoundsException("Given index is out of array bound");
        }

        return this.taskList[index];

    }

    public ArrayTaskList incoming(int from, int to) {

        ArrayTaskList subTaskList = new ArrayTaskList();

        for (int i = 0; i < this.size(); ++i) {
            if (this.getTask(i).isRepeated() && this.getTask(i).isActive()) {
                if (this.getTask(i).getStartTime() >= from
                        && this.getTask(i).getEndTime() <= to) {
                    subTaskList.add(this.getTask(i));
                }
            }else {
                if (this.getTask(i).getTime() <= from
                        && this.getTask(i).getTime() >= to) {
                    subTaskList.add(this.getTask(i));
                }
            }
        }

        return subTaskList;

    }


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
