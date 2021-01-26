package ua.edu.sumdu.j2se.bekker.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.bekker.tasks.TaskManager;
import ua.edu.sumdu.j2se.bekker.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.bekker.tasks.model.Task;
import ua.edu.sumdu.j2se.bekker.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.bekker.tasks.view.ConsolePrinting;
import ua.edu.sumdu.j2se.bekker.tasks.view.MainViewDecorator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class TaskController {
    private static final Logger logger = Logger.getLogger(TaskController.class);
    private final MainViewDecorator view;
    private AbstractTaskList model;
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
        int repeated = view.getIsTaskRepeated();
        if (repeated == 2) {
            while (true) {
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
                    ConsolePrinting.print("The task was successfully added to the list.");
                    break;
                } else {
                    ConsolePrinting.print("Wrong time period. Try again!");
                }
            }
        } else {
            ConsolePrinting.print("\nTask completion time: ");
            LocalDateTime time = view.parseDateTime();
            task = new Task(title, time);
            task.setActive(true);
            model.add(task);
            view.displayTaskInfo(task);
            ConsolePrinting.print("The task was successfully added to the list.");
            logger.info("The task: " + task.getTitle() + " was successfully added to the list.");
        }
        TaskIO.saveToFileStorage(model, TaskManager.DATA_JSON_PATH);
    }

    /**
     * Edits selected task from task lit.
     */
    public void editTask() {
        int index = view.selectTask(model) - 1;
        Task task = model.getTask(index);
        LocalDateTime start, end;
        while (true) {
            view.showEditMenu();
            String line = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (Exception e) {
                ConsolePrinting.print("Please enter a number: \n");
                continue;
            }
            if (choice == 0) {
                ConsolePrinting.print("Do you want to return to previous menu? (Yes/No): ");
                if (view.checkUserAnswer()) {
                    break;
                }
            } else {
                switch (choice) {
                    case 1:
                        task.setTitle(task.getTitle());
                        ConsolePrinting.print("Title was changed on: " + task.getTitle());
                        break;
                    case 2:
                        boolean aBoolean = view.getActiveStatus();
                        task.setActive(aBoolean);
                        ConsolePrinting.print("Activity status was changed on: " + task.isActive());
                        break;
                    case 3:
                        task.setTime(view.parseDateTime());
                        ConsolePrinting.print("Time was changed on: " + task.getTime());
                        break;
                    case 4:
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
                        break;
                    default:
                }
                TaskIO.saveToFileStorage(model, TaskManager.DATA_JSON_PATH);
            }
        }
        ConsolePrinting.println("The task has been successfully changed!");
        logger.info("The task: " + task.getTitle() + "has been successfully changed!");
    }

    /**
     * Removes selected task from task list.
     */
    public void removeTask() {
        int taskID = view.removeTask(model);
        Task task;
        if (taskID == 0) {
            ConsolePrinting.print("The task list is empty! Add at least one task.");
        } else if (taskID == -1) {
            ConsolePrinting.print("You did not confirm the deletion of the task.");
        } else if (taskID > 0) {
            task = model.getTask(taskID - 1);
            model.remove(task);
            TaskIO.saveToFileStorage(model, TaskManager.DATA_JSON_PATH);
            ConsolePrinting.print("Task №: " + taskID + " was deleted from controller!");
            logger.info("Task №: " + taskID + " was deleted from controller!");
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
            e.printStackTrace();
        }
    }
}
