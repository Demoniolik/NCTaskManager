package ua.edu.sumdu.j2se.bekker.tasks;

public class LinkedTaskList {
    private int size;
    private Node head;

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

    public int size() {
        return this.size;
    }

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
                //prev_node.task = null;
                prev_node.next = new_node.next;
                size--;
                return true;
            }
            prev_node = new_node;
            new_node = new_node.next;

        }

        return false;
    }

    class Node {

        Task task;
        Node next;

        public Node(Task task) {
            this.task = task;
        }

    }

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
