package ua.edu.sumdu.j2se.bekker.tasks.view.impl;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;
import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;
import ua.edu.sumdu.j2se.bekker.tasks.view.ConsoleTable;
import ua.edu.sumdu.j2se.bekker.tasks.view.TimeConverter;
import ua.edu.sumdu.j2se.bekker.tasks.view.TaskView;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TaskViewImpl implements TaskView {
    private static final Logger logger = Logger.getLogger(TaskViewImpl.class);
    private final Scanner scanner;

    public TaskViewImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints task list into CLI.
     *
     * @param tasks task list.
     */
    @Override
    public void showTaskList(AbstractTaskList tasks) {
        if (tasks.size() == 0) {
            emptyList();
        } else {
            notEmptyList(tasks);
        }
    }

    /**
     * Prints message to select task and return a number of
     * selected task to a controller.
     *
     * @param list task list.
     * @return index of element in task list.
     */
    @Override
    public int selectTask(AbstractTaskList list) {
        int id;
        if (list.size() == 0) {
            emptyList();
            return 0;
        } else {
            notEmptyList(list);
            ConsolePrinting.print("Enter the task number that you want to edit: ");
            id = readMenuOption(list.size());
        }
        return id;
    }

    /**
     * Prints message to remove task and requests a number of
     * task to delete. Returns number of element to a controller.
     *
     * @param list task list.
     * @return index of element in task list.
     */
    @Override
    public int removeTask(AbstractTaskList list) {
        if (list.size() == 0) {
            emptyList();
            return 0;
        } else {
            notEmptyList(list);
            ConsolePrinting.print("Enter the task number that you want to remove: ");
            int id = readMenuOption(list.size());
            ConsolePrinting.print("To remove you should enter \"Yes\"" + "\nDo you want to exit(Yes/No)? ");
            if (checkUserAnswer()) {
                return id;
            }
        }
        return -1;
    }

    /**
     * Used to request new title from CLI.
     *
     * @return string title into a controller.
     */
    @Override
    public String getTitle() {
        String title = "";
        ConsolePrinting.print("Enter task name: ");
        while (title.isEmpty()) {
            title = scanner.nextLine();
            if (title.isEmpty()) {
                ConsolePrinting.print("Title field is empty. Please enter correct value: ");
            }
        }
        return title;
    }

    /**
     * Used to request activity status from CLI.
     *
     * @return boolean. True if task is active.
     */
    @Override
    public boolean getActiveStatus() {
        String s = "";
        ConsolePrinting.print("Enter activity status (true/false): ");
        while (!(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))) {
            s = scanner.nextLine();
            if (s.isEmpty()) {
                ConsolePrinting.print("Activity status field is empty. Please enter correct value: ");
            } else if (!(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))) {
                ConsolePrinting.print("Incorrect status. Please enter correct value: ");
            }
        }
        return Boolean.parseBoolean(s);
    }

    /**
     * Used to request task repetition status from CLI.
     *
     * @return an integer value into a controller.
     */
    @Override
    public int getIsTaskRepeated() {
        int choice;
        do {
            ConsolePrinting.print("Enter [1] to create non-repeating task or [2] to repeating task: ");
            choice = readMenuOption(2);
            if (choice == 1) {
                ConsolePrinting.print("You selected non-repeating task!");
                return choice;
            } else if (choice == 2) {
                ConsolePrinting.print("You selected repeating task!!");
                return choice;
            } else if (choice == -1) {
                break;
            }
        } while (true);
        return choice;
    }

    /**
     * Used to request time interval in minutes from CLI.
     *
     * @return an integer value in minutes into a controller.
     */
    @Override
    public int getInterval() {
        ConsolePrinting.print("Enter the interval in minutes: ");
        return readMenuOption(Integer.MAX_VALUE) * 60;
    }

    /**
     * Print an information about task to CLI.
     *
     * @param task task object.
     */
    @Override
    public void displayTaskInfo(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        StringBuilder builder = new StringBuilder();
        builder.append("Task title: ")
                .append(task.getTitle());
        if (!task.isRepeated()) {
            builder.append(", time: ")
                    .append(task.getTime().format(formatter));
        } else {
            builder.append(", start time: ")
                    .append(task.getStartTime().format(formatter))
                    .append(", end time: ")
                    .append(task.getEndTime().format(formatter))
                    .append(", interval: ")
                    .append(TimeConverter.convertSecondsToDayHourMinuteSecondFormat(task.getRepeatInterval()));
        }
        builder.append(", active: ")
                .append(task.isActive())
                .append(".");
        ConsolePrinting.println(builder.toString());
    }


    private void emptyList() {
        ConsolePrinting.print("The task list is empty! Please create at least one task.\n");
    }

    private void notEmptyList(AbstractTaskList tasks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        ConsoleTable st = new ConsoleTable();
        st.setShowVerticalLines(true);
        st.setHeaders("№", "Title", "Start time", "End time", "Interval", "Status");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            String title = task.getTitle();
            String time = task.getTime().format(formatter);
            String start = task.getStartTime().format(formatter);
            String end = task.getEndTime().format(formatter);
            String interval = TimeConverter.convertSecondsToDayHourMinuteSecondFormat(task.getRepeatInterval());
            String active = task.isActive() ? "active" : "inactive";

            if (task.isRepeated()) {
                st.addRow(("" + (i + 1)), title, start, end, interval, active);
            } else if (!task.isRepeated()) {
                st.addRow(("" + (i + 1)), title, time, "   -", "non-repeating", active);
            }
        }
        st.print();
    }

    /**
     * Checks user confirmation from command line input.
     *
     * @return true if user answer is "yes".
     */
    @Override
    public boolean checkUserAnswer() {
        return userAnswer().equalsIgnoreCase("yes");
    }

    private String userAnswer() {
        String userAnswer = scanner.nextLine();
        while (!userAnswer.equalsIgnoreCase("yes") & !userAnswer.equalsIgnoreCase("no")) {
            ConsolePrinting.println("You should select [yes] or [no]: ");
            userAnswer = scanner.nextLine();
        }
        return userAnswer;
    }

    private int readMenuOption(int menuOption) {
        while (true) {
            String line = scanner.nextLine();
            try {
                if (line.equalsIgnoreCase("quit")) {
                    return -1;
                }
                int n = Integer.parseInt(line);
                if (n >= 1 && n <= menuOption) {
                    return n;
                }
            } catch (NumberFormatException e) {
                logger.error("Incorrect input value.");
            }
            ConsolePrinting.print("Please, enter correct value or [quit] to cancel: ");
        }
    }
}
