package ua.edu.sumdu.j2se.bekker.tasks;

import java.util.Iterator;
import java.util.Objects;

/**
 * This class is responsible for storing objects of Task class
 * It represented as an Linked list so in the base it has a sequence of nodes that contain:
 *  taskList and reference to the next node
 * As well all the methods for adding, removing, getting tasks are provided
 *
 * @author Dmitry Bekker
 */
public class LinkedTaskList extends AbstractTaskList implements Iterable<Task> {
    private int size;
    private Node head; // Main node that contains the start of the linked list

    /**
     * This class is representation of the Node that contains
     * the object of class Task and the reference to the next node in sequence
     */
    class Node {
        private Task task;
        private Node next;

        /**
         * Custom constructor that assign task to the current node
         *
         * @param task to be assigned to the node
         */
        public Node(Task task) {
            this.task = task;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(task, node.task) &&
                    Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, next);
        }
    }

    /**
     * This method adds elements of type Task to the linked list - taskList
     *
     * In case if head node is not preseent at the time of adding the element it will be created
     * and given task will be stored in the head
     * the reference to the next node will be null
     *
     * In case if head is present we go through the linked list to find the end of it and put
     * the task to the end of the linked list
     *
     * @param task given element by user to be added to the list
     */
    public void add(Task task) {
        if (head == null) {
            head = new Node(task);
            size++;
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(task);
        size++;
    }

    /**
     * This method returns the current size of the linked list
     *
     * @return linked list size
     */
    public int size() {
        return this.size;
    }

    /**
     * This method removes the Task from the linked list
     *
     * In case if we need to delete task from the head element
     * the head is moved to the new element by assigning new next Node reference
     *
     * In case if the element that should be deleted is after the head element
     * algorithm goes through the linked list (using next Node references) and when element
     * is found the previous reference is assigned to the next element of the deleted one
     *
     * @param task given task to be deleted
     * @return true if the task is found and deleted, false if not found
     */
    public boolean remove(Task task) {
        if (head.task.equals(task)) {
            head = head.next;
            size--;
            return true;
        }
        Node new_node = head;
        Node prev_node = new_node;
        while (new_node != null) {
            if (new_node.task.equals(task)) {
                prev_node.next = new_node.next;
                size--;
                return true;
            }
            prev_node = new_node;
            new_node = new_node.next;
        }
        return false;
    }

    /**
     * This method returns the task by its index
     * which represents elements position in the linked list
     *
     * @param index of the element that needed to be get
     * @return Task object that is found in the array by the given index
     */
    public Task getTask(int index) {
        Node temp = head;
        for (int i = 0; temp != null; ++i) {
            if (i == index) {
                return temp.task;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    public Iterator<Task> iterator() {
        Iterator<Task> iterator = new Iterator<Task>() {
            private int currentIndex = 0;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && getTask(currentIndex) != null;
            }

            @Override
            public Task next() {
                lastRet = currentIndex;
                return getTask(currentIndex++);
            }

            @Override
            public void remove() {
                if (currentIndex > 0) {
                    LinkedTaskList.this.remove(getTask(lastRet));
                    currentIndex--;
                }else {
                    throw new IllegalStateException();
                }
            }
        };
        return iterator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        Node runner = head;
        Node givenObject = that.head;
        while (runner != null) {
            if (!givenObject.equals(runner)) {
                return false;
            }
            runner = runner.next;
            givenObject = givenObject.next;
        }
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, head);
    }

    public LinkedTaskList clone() {
        LinkedTaskList clone = new LinkedTaskList();
        Node runner = head;
        while (runner != null) {
            clone.add(runner.task);
            runner = runner.next;
        }
        return clone;
    }
}
