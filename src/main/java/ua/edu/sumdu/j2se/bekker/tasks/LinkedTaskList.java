package ua.edu.sumdu.j2se.bekker.tasks;

/**
 * This class is responsible for storing objects of Task class
 * It represented as an Linked list so in the base it has a sequence of nodes that contain:
 *  - taskList and reference to the next node
 * As well all the methods for adding, removing, getting tasks are provided
 *
 * @author Dmitry Bekker
 */
public class LinkedTaskList {
    private int size;
    private Node head; // Main node that contains the start of the linked list

    /**
     * This class is representation of the Node that contains
     * the object of class Task and the reference to the next node in sequence
     */
    class Node {
        Task task;
        Node next;

        /**
         * Custom constructor that assign task to the current node
         *
         * @param task to be assigned to the node
         */
        public Node(Task task) {
            this.task = task;
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

    /**
     * This method return subclass of the LinkedTaskList
     * that contains of the tasks that are scheduled in the range
     * given by the user
     *
     * @param from is the start point of gathering of tasks
     * @param to is the end point of gathering of tasks
     * @return subclass of type ArrayTaskList that contains of selected elements
     */
    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList subTaskList = new LinkedTaskList();
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
}
