package ua.edu.sumdu.j2se.bekker.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bekker.tasks.TaskManager;
import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;
import ua.edu.sumdu.j2se.bekker.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;
import ua.edu.sumdu.j2se.bekker.tasks.view.MainViewDecorator;
import ua.edu.sumdu.j2se.bekker.tasks.view.TaskStatus;

import java.time.LocalDateTime;
import java.util.Scanner;

public class TaskController {
    private static final Logger logger = Logger.getLogger(TaskController.class);
    private final MainViewDecorator view;
    private AbstractTaskList model;
    private static final String tuskAddingSuccessMessage =
            "The task was successfully added to the list of tasks";
    private final Scanner scanner = new Scanner(System.in);

    public TaskController(MainViewDecorator view, AbstractTaskList model) {
        this.view = view;
        this.model = model;
    }
    /**
     * Adds a task to the task list.
     */
    public void addTask() {
        Task task;
        String title = view.getTitle();
        if (view.getTaskStatus() == TaskStatus.REPEATED) {
            while (true) {
                if (creatingRepetitiveTask(title)) break;
            }
        } else {
            creatingNonRepetitiveTask(title);
        }
        TaskIO.saveToFileStorage(model, TaskManager.DATA_JSON_PATH);
    }

    private void creatingNonRepetitiveTask(String title) {
        Task task;
        LocalDateTime time;
        boolean incorrectTime = false;
        do {
            ConsolePrinting.print("\nTask completion time: ");
            time = view.parseDateTime();
            if (time.isBefore(LocalDateTime.now())) {
                ConsolePrinting.println("Task with given time cannot be created." +
                        "\nTime is before now is invalid");
                incorrectTime = true;
            }
        }while(incorrectTime);
        task = new Task(title, time);
        task.setActive(true);
        model.add(task);
        view.displayTaskInfo(task);

        ConsolePrinting.print(tuskAddingSuccessMessage);
        logger.info(tuskAddingSuccessMessage);
    }

    private boolean creatingRepetitiveTask(String title) {
        Task task;
        ConsolePrinting.print("\nStart date of the period: ");
        LocalDateTime start = view.parseDateTime();
        ConsolePrinting.print("\nEnd date of the period: ");
        LocalDateTime end = view.parseDateTime();
        int interval = view.getInterval();
        if (!((start.isAfter(end) || start.isEqual(end)) &&
                start.plusSeconds(interval).isAfter(end))) {
            task = new Task(title, start, end, interval);
            task.setActive(true);
            model.add(task);
            view.displayTaskInfo(task);
            ConsolePrinting.print(tuskAddingSuccessMessage);
            return true;
        } else {
            ConsolePrinting.print("Wrong time period");
        }
        return false;
    }

    /**
     * Edits selected task from task lit.
     */
    public void editTask() {
        int index = view.selectTask(model) - 1;
        Task task = model.getTask(index);
        while (true) {
            view.showEditMenu();
            String line = scanner.nextLine();
            UserEditMenuChoice choice = getUserChoice(line);
            if (choice == null) continue;
            if (choice == UserEditMenuChoice.RETURN_TO_MAIN_MENU) {
                if (returningToMainMenuOption()) break;
            } else {
                switch (choice) {
                    case CHANGE_TITLE:
                        changingTitleOption(task);
                        break;
                    case CHANGE_ACTIVITY_STATUS:
                        changingActivityStatusOption(task);
                        break;
                    case CHANGE_TIME:
                        changingNonRepetitiveTaskTimeOption(task);
                        break;
                    case CHANGE_REPETITIVE_TIME:
                        changingRepetitiveTaskTimeOption(task);
                        break;
                    default:
                }
                TaskIO.saveToFileStorage(model, TaskManager.DATA_JSON_PATH);
            }
        }
        String taskEditSuccessMessage = "The task has been successfully changed!";
        ConsolePrinting.println(taskEditSuccessMessage);
        logger.info(taskEditSuccessMessage);
    }

    private void changingRepetitiveTaskTimeOption(Task task) {
        LocalDateTime end;
        LocalDateTime start;
        while (true) {
            ConsolePrinting.print("Enter start time: ");
            start = view.parseDateTime();
            ConsolePrinting.print("Enter end time: ");
            end = view.parseDateTime();
            ConsolePrinting.print("Enter interval time: ");
            int interval = view.getInterval();

            if (!((start.isAfter(end) || start.isEqual(end)) &&
                    start.plusSeconds(interval).isAfter(end))) {
                task.setTime(start, end, interval);
                ConsolePrinting.print("Start time was changed on: " + task.getStartTime() +
                        "\nEnd time was changed on: " + task.getEndTime() +
                        "\nInterval was changed on: " + task.getRepeatInterval());
                break;
            } else {
                ConsolePrinting.println("Wrong time period. Please try again.");
            }
        }
    }

    private void changingNonRepetitiveTaskTimeOption(Task task) {
        task.setTime(view.parseDateTime());
        ConsolePrinting.print("Time was changed on: " + task.getTime());
    }

    private void changingActivityStatusOption(Task task) {
        boolean aBoolean = view.getActiveStatus();
        task.setActive(aBoolean);
        ConsolePrinting.print("Activity status was changed on: " + task.isActive());
    }

    private void changingTitleOption(Task task) {
        ConsolePrinting.print("Enter new title to the task: ");
        String newTitle = scanner.nextLine();
        task.setTitle(newTitle);
        ConsolePrinting.print("Title was changed on: " + task.getTitle());
    }

    private boolean returningToMainMenuOption() {
        ConsolePrinting.print("Do you want to return to previous menu? (Yes/No): ");
        return view.checkUserAnswer();
    }

    private UserEditMenuChoice getUserChoice(String line) {
        UserEditMenuChoice userEditMenuChoice;
        int choice;
        try {
            choice = Integer.parseInt(line);
            userEditMenuChoice = UserEditMenuChoice.values()[choice];
        } catch (Exception e) {
            ConsolePrinting.print("Please enter a number: \n");
            return null;
        }

        return userEditMenuChoice;
    }

    /**
     * Removes selected task from task list.
     */
    public void removeTask() {
        int taskID = view.removeTask(model);
        Task task;
        if (taskID == 0) {
            ConsolePrinting.print("The task list is empty for now! Add at least one task");
        } else if (taskID == -1) {
            ConsolePrinting.print("You did not confirm the deletion of the task.");
        } else if (taskID > 0) {
            task = model.getTask(taskID - 1);
            model.remove(task);
            TaskIO.saveToFileStorage(model, TaskManager.DATA_JSON_PATH);
            ConsolePrinting.print("Task №: " + taskID + " was deleted from controller!");
            logger.info("Task was deleted from controller!");
        }
    }
    /**
     * Gets task list from model and represents into view.
     */
    public void getList() {
        view.showTaskList(model);
    }

    /**
     * Gets task list from model by selected period and represents into view.
     */
    public void getCalendar() {
        try {
            view.getCalendar(model);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    enum UserEditMenuChoice{
        RETURN_TO_MAIN_MENU,
        CHANGE_TITLE,
        CHANGE_ACTIVITY_STATUS,
        CHANGE_TIME,
        CHANGE_REPETITIVE_TIME
    }
}
